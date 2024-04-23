package com.example.myapplication;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;

public class firstActivity extends AppCompatActivity {

    private ImageView image2;
    private Button startButton;
    private RelativeLayout relativeLayout;
    private int startX, startY, endX, endY;
    private float radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first);

        startButton = findViewById(R.id.start_button);
        image2 = findViewById(R.id.image_2);
        relativeLayout = findViewById(R.id.first);

        startButton.post(new Runnable() {
            @Override
            public void run() {
                startX = (relativeLayout.getWidth() - image2.getWidth()) / 2;
                startY = startButton.getTop() - image2.getHeight()*2;
                endX = (relativeLayout.getWidth() - image2.getWidth()) / 2;
                endY = (int) (image2.getY() + image2.getHeight());

                radius = (float) (Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2)) /2); // Уменьшаем радиус
                animateImage();
            }
        });

        Button exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(firstActivity.this);
                builder.setMessage("Вы действительно хотите выйти из приложения?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(firstActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void animateImage() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(image2, "rotation", 0f, 360f);
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            float x = startX + radius * (float) Math.cos(Math.toRadians(animatedValue));
            float y = startY + radius * (float) Math.sin(Math.toRadians(animatedValue));
            image2.setX(x);
            image2.setY(y);
        });
        animator.start();
    }
}