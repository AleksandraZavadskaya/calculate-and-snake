package com.example.myapplication;

import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class calculatorActivity extends AppCompatActivity {

    private EditText editText1;
    private EditText editText2;
    private RadioGroup radioGroup;
    private Button calculateButton;
    private TextView resultTextView;
    private Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        // Находим изображение по его ID
        ImageView imageView = findViewById(R.id.imageView);

        // Находим элементы интерфейса
        View[] interfaceViews = {findViewById(R.id.textView), findViewById(R.id.cacledit), findViewById(R.id.cacledit2), findViewById(R.id.radioGroup), findViewById(R.id.calculateButton), findViewById(R.id.menuButton)};

        // Устанавливаем прозрачность элементам интерфейса до начала анимации
        setViewsAlpha(interfaceViews, 0.0f);

        // Сделаем изображение видимым перед запуском анимации
        imageView.setVisibility(View.VISIBLE);

        // Получаем высоту экрана
        int screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Запускаем анимацию
        imageView.animate()
                .translationY(-screenHeight) // Сдвигаем изображение вверх до выхода за пределы экрана
                .setDuration(2500) // Продолжительность анимации в миллисекундах
                .withEndAction(() -> {
                    // После окончания анимации скрываем изображение
                    imageView.setVisibility(View.GONE);
                    // Восстанавливаем прозрачность элементам интерфейса
                    setViewsAlpha(interfaceViews, 1.0f);
                    // Затем можно отобразить интерфейс активности
                });
    }

    // Метод для установки прозрачности элементам интерфейса
    private void setViewsAlpha(View[] views, float alpha) {
        for (View view : views) {
            view.setAlpha(alpha);
        }
        // Находим все необходимые элементы интерфейса по их ID
        editText1 = findViewById(R.id.cacledit);
        editText2 = findViewById(R.id.cacledit2);
        radioGroup = findViewById(R.id.radioGroup);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.textView);
        menuButton = findViewById(R.id.menuButton);

        // Добавляем обработчик клика для кнопки "Calculate"
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Получаем введенные пользователем числа
                double number1 = Double.parseDouble(editText1.getText().toString());
                double number2 = Double.parseDouble(editText2.getText().toString());

                // Получаем выбранную математическую операцию
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String operation = selectedRadioButton.getText().toString();

                // Выполняем выбранную операцию над числами
                double result = 0;
                switch (operation) {
                    case "+":
                        result = number1 + number2;
                        break;
                    case "-":
                        result = number1 - number2;
                        break;
                    case "*":
                        result = number1 * number2;
                        break;
                    case "/":
                        if (number2 != 0) {
                            result = number1 / number2;
                        } else {
                            Toast.makeText(calculatorActivity.this, "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        break;
                }

                // Отображаем результат в textView
                resultTextView.setText(String.valueOf(result));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        // Добавляем обработчик клика для кнопки "Menu"
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Возвращаемся к MainActivity
                finish();
            }
        });
    }
}
