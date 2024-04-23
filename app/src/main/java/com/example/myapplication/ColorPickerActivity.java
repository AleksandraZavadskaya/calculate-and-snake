package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

public class ColorPickerActivity extends Activity {

    private Button colorButton;
    private Button colorButton2;
    private Button returnButton;
    private int currentColor;
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String COLOR_KEY = "color";
    private static final String BACKGROUND_COLOR_KEY = "background_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        colorButton = findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открываем диалог выбора цвета для первой кнопки
                showColorPickerDialog();
            }
        });

        colorButton2 = findViewById(R.id.colorButton2);
        colorButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Открываем диалог выбора цвета заднего фона
                showBackgroundColorPickerDialog();
            }
        });

        returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Возвращаемся к MainActivity
                finish();
            }
        });

        // Получаем сохраненные цвета и устанавливаем их
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        currentColor = settings.getInt(COLOR_KEY, ContextCompat.getColor(this, R.color.red)); // По умолчанию красный цвет
        colorButton.setBackgroundColor(currentColor);
        int backgroundColor = settings.getInt(BACKGROUND_COLOR_KEY, Color.WHITE); // По умолчанию белый цвет заднего фона
        getWindow().getDecorView().setBackgroundColor(backgroundColor);
    }

    private void showColorPickerDialog() {
        // Получаем массив цветов из ресурсов
        final int[] colors = {
                ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.pink),
                ContextCompat.getColor(this, R.color.pink_text),
                ContextCompat.getColor(this, R.color.purple),
                ContextCompat.getColor(this, R.color.lightgreen),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.darkgreen),
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.yellow2),
                ContextCompat.getColor(this, R.color.lime),
                ContextCompat.getColor(this, R.color.aqua),
                ContextCompat.getColor(this, R.color.blue)
        };

        // Используем встроенный диалог выбора цвета
        new android.app.AlertDialog.Builder(this)
                .setTitle("Выберите цвет")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ничего не делаем, закрываем диалог
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ничего не делаем, закрываем диалог
                    }
                })
                .setSingleChoiceItems(new CharSequence[]{
                        "Черный", "Белый", "Красный", "Розовый", "Розовый текст",
                        "Фиолетовый", "Светло-зеленый", "Зеленый", "Темно-зеленый",
                        "Цвет основной", "Темный цвет основной", "Акцентный цвет",
                        "Желтый", "Оранжевый", "Желтый 2", "Лайм", "Аква", "Синий"
                }, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Устанавливаем цвет кнопки в зависимости от выбранного цвета
                        currentColor = colors[which];
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        // Устанавливаем выбранный цвет кнопки после закрытия диалога
                        colorButton.setBackgroundColor(currentColor);
                        // Сохраняем выбранный цвет
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt(COLOR_KEY, currentColor);
                        editor.apply();
                    }
                })
                .show();
    }

    private void showBackgroundColorPickerDialog() {
        // Получаем массив цветов из ресурсов
        final int[] colors = {
                ContextCompat.getColor(this, R.color.black),
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.red),
                ContextCompat.getColor(this, R.color.pink),
                ContextCompat.getColor(this, R.color.pink_text),
                ContextCompat.getColor(this, R.color.purple),
                ContextCompat.getColor(this, R.color.lightgreen),
                ContextCompat.getColor(this, R.color.green),
                ContextCompat.getColor(this, R.color.darkgreen),
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.orange),
                ContextCompat.getColor(this, R.color.yellow2),
                ContextCompat.getColor(this, R.color.lime),
                ContextCompat.getColor(this, R.color.aqua),
                ContextCompat.getColor(this, R.color.blue)
        };

        // Используем встроенный диалог выбора цвета
        new android.app.AlertDialog.Builder(this)
                .setTitle("Выберите цвет заднего фона")
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ничего не делаем, закрываем диалог
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Ничего не делаем, закрываем диалог
                    }
                })
                .setSingleChoiceItems(new CharSequence[]{
                        "Черный", "Белый", "Красный", "Розовый", "Розовый текст",
                        "Фиолетовый", "Светло-зеленый", "Зеленый", "Темно-зеленый",
                        "Цвет основной", "Темный цвет основной", "Акцентный цвет",
                        "Желтый", "Оранжевый", "Желтый 2", "Лайм", "Аква", "Синий"
                }, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Устанавливаем цвет фона в зависимости от выбранного цвета
                        int backgroundColor = colors[which];
                        getWindow().getDecorView().setBackgroundColor(backgroundColor);

                        // Сохраняем выбранный цвет фона
                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt(BACKGROUND_COLOR_KEY, backgroundColor);
                        editor.apply();
                    }
                })
                .show();
    }
}
