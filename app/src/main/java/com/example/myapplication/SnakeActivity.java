package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeActivity extends AppCompatActivity {

    private ImageView snakeHeadImageView;
    private ImageView foodImageView;
    private ImageView food2ImageView;
    private ImageView badfoodImageView;
    private LinkedList<BodyPart> snakeBodyParts;
    private Button leftButton, rightButton, upButton, downButton, gameButton, restartButton, exitButton;
    private FrameLayout gameFrameLayout;
    private int score = 0;
    private TextView scoreTextView, gameOverTextView;
    private ImageView gameOverImageView;
    private enum Direction { UP, DOWN, LEFT, RIGHT }
    private Direction currentDirection = Direction.RIGHT;
    private boolean isGameOver = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        snakeHeadImageView = findViewById(R.id.snakeHeadImageView);
        foodImageView = findViewById(R.id.foodImageView);
        food2ImageView = findViewById(R.id.food2ImageView);
        badfoodImageView = findViewById(R.id.badfoodImageView);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        gameButton = findViewById(R.id.gameButton);
        restartButton = findViewById(R.id.restartButton);
        exitButton = findViewById(R.id.exitButton);
        gameFrameLayout = findViewById(R.id.gameFrameLayout);
        scoreTextView = findViewById(R.id.scoreTextView);
        gameOverTextView = findViewById(R.id.gameOverTextView);
        gameOverImageView = findViewById(R.id.gameOverImageView);


        snakeBodyParts = new LinkedList<>();

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) handleDirectionButtonClick(Direction.LEFT);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) handleDirectionButtonClick(Direction.RIGHT);
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) handleDirectionButtonClick(Direction.UP);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isGameOver) handleDirectionButtonClick(Direction.DOWN);
            }
        });

        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                startGame();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрываем активность и возвращаемся к предыдущей
            }
        });

        // Скрываем текст, картинку и кнопки рестарта и выхода при запуске
        restartButton.setVisibility(View.GONE);
        exitButton.setVisibility(View.GONE);
        gameButton.setVisibility(View.GONE);
        leftButton.setVisibility(View.GONE);
        rightButton.setVisibility(View.GONE);
        upButton.setVisibility(View.GONE);
        downButton.setVisibility(View.GONE);
        snakeHeadImageView.setVisibility(View.GONE);
        foodImageView.setVisibility(View.GONE);
        badfoodImageView.setVisibility(View.GONE);
        food2ImageView.setVisibility(View.GONE);
        scoreTextView.setVisibility(View.INVISIBLE);


        // Находим изображение по его ID
        ImageView snake2ImageView = findViewById(R.id.Snake2ImageView);

        // Получаем высоту и ширину экрана
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;

        int initialX = screenWidth / 120;
        int initialY = 0; // Верхний край экрана

        snake2ImageView.setX(initialX);
        snake2ImageView.setY(initialY);

// Запускаем анимацию движения картинки по экрану
        snake2ImageView.setVisibility(View.VISIBLE);
        snake2ImageView.animate()
                .x((screenWidth / 120)) // Перемещаем картинку в центр по горизонтали
                .y(screenHeight - snake2ImageView.getHeight()) // Перемещаем картинку вниз до нижнего края экрана
                .setDuration(2500) // Продолжительность анимации в миллисекундах
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // После завершения анимации делаем кнопку игры видимой
                        gameButton.setVisibility(View.VISIBLE);
                        leftButton.setVisibility(View.VISIBLE);
                        rightButton.setVisibility(View.VISIBLE);
                        upButton.setVisibility(View.VISIBLE);
                        downButton.setVisibility(View.VISIBLE);
                        scoreTextView.setVisibility(View.VISIBLE);
                    }
                })
                .start();
    }

    private class BodyPart {
        private ImageView imageView;
        private float x;
        private float y;

        public BodyPart(ImageView imageView, float x, float y) {
            this.imageView = imageView;
            this.x = x;
            this.y = y;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    private void startGame() {
        resetGame();

        gameButton.setVisibility(View.GONE);
        snakeHeadImageView.setVisibility(View.VISIBLE);
        foodImageView.setVisibility(View.VISIBLE);
        food2ImageView.setVisibility(View.VISIBLE);
        badfoodImageView.setVisibility(View.VISIBLE);

        isGameOver = false;

        int centerX = gameFrameLayout.getWidth() / 2;
        int centerY = gameFrameLayout.getHeight() / 2;
        snakeHeadImageView.setX(centerX);
        snakeHeadImageView.setY(centerY);

        addSnakeBodyPart();

        placeFood(); // Добавляем первую еду
        placeFood2(); // Добавляем вторую еду
        placeBadFood(); // Добавляем плохую еду

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isGameOver) {
                            moveSnake();
                        }
                    }
                });
            }
        }, 0, 100); // Snake movement speed

        // Schedule the task to update food positions every 10 seconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isGameOver) {
                            updateFoodPositions();
                        }
                    }
                });
            }
        }, 0, 10000); // 10 seconds interval

        startFoodPulseAnimations(); // Здесь вызывается метод startFoodPulseAnimations()
    }



    private void resetGame() {
        gameButton.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.GONE);
        exitButton.setVisibility(View.GONE);
        gameOverTextView.setVisibility(View.GONE);
        gameOverImageView.setVisibility(View.GONE);
        isGameOver = false;

        for (BodyPart snakeBodyPart : snakeBodyParts) {
            ((ViewGroup) snakeBodyPart.getImageView().getParent()).removeView(snakeBodyPart.getImageView());
        }
        snakeBodyParts.clear();
        score = 0;
        updateScoreDisplay();
        currentDirection = Direction.RIGHT;
    }

    private void placeFood() {
        Random random = new Random();
        int x = random.nextInt(gameFrameLayout.getWidth() - foodImageView.getWidth());
        int y = random.nextInt(gameFrameLayout.getHeight() - foodImageView.getHeight());

        foodImageView.setX(x);
        foodImageView.setY(y);
    }
    private void placeFood2() {
        Random random = new Random();
        int x = random.nextInt(gameFrameLayout.getWidth() - food2ImageView.getWidth());
        int y = random.nextInt(gameFrameLayout.getHeight() - food2ImageView.getHeight());

        food2ImageView.setX(x);
        food2ImageView.setY(y);
    }

    private void placeBadFood() {
        Random random = new Random();
        int x = random.nextInt(gameFrameLayout.getWidth() - badfoodImageView.getWidth());
        int y = random.nextInt(gameFrameLayout.getHeight() - badfoodImageView.getHeight());

        badfoodImageView.setX(x);
        badfoodImageView.setY(y);
    }


    private void addSnakeBodyPart() {
        ImageView newBodyPartImageView = new ImageView(this);
        newBodyPartImageView.setImageResource(R.drawable.snake_green_blob_32);
        newBodyPartImageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        BodyPart newBodyPart;
        if (snakeBodyParts.isEmpty()) {
            newBodyPart = new BodyPart(newBodyPartImageView, snakeHeadImageView.getX(), snakeHeadImageView.getY());
        } else {
            BodyPart lastPart = snakeBodyParts.getLast();
            float newX = lastPart.getX()+20;
            float newY = lastPart.getY()+20;
            newBodyPart = new BodyPart(newBodyPartImageView, newX, newY);
        }
        snakeBodyParts.addLast(newBodyPart);

        // Добавляем новую часть тела змеи в начало FrameLayout
        gameFrameLayout.addView(newBodyPartImageView, 1);
    }

    private void handleDirectionButtonClick(Direction newDirection) {
        if ((currentDirection == Direction.LEFT && newDirection != Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && newDirection != Direction.LEFT) ||
                (currentDirection == Direction.UP && newDirection != Direction.DOWN) ||
                (currentDirection == Direction.DOWN && newDirection != Direction.UP)) {
            currentDirection = newDirection;
        }
    }

    private void moveSnake() {
        float headX = snakeHeadImageView.getX();
        float headY = snakeHeadImageView.getY();
        float stepSize = 20;

        if (headX <= 0) {
            snakeHeadImageView.setX(gameFrameLayout.getWidth() - snakeHeadImageView.getWidth());
        } else if (headX >= gameFrameLayout.getWidth() - snakeHeadImageView.getWidth()) {
            snakeHeadImageView.setX(0);
        }

        if (headY <= 0) {
            snakeHeadImageView.setY(gameFrameLayout.getHeight() - snakeHeadImageView.getHeight());
        } else if (headY >= gameFrameLayout.getHeight() - snakeHeadImageView.getHeight()) {
            snakeHeadImageView.setY(0);
        }

        switch (currentDirection) {
            case UP:
                snakeHeadImageView.setY(headY - stepSize);
                break;
            case DOWN:
                snakeHeadImageView.setY(headY + stepSize);
                break;
            case LEFT:
                snakeHeadImageView.setX(headX - stepSize);
                break;
            case RIGHT:
                snakeHeadImageView.setX(headX + stepSize);
                break;
        }

        checkFoodCollision();
        checkBadFoodCollision();
        moveSnakeBody();
    }

    private void moveSnakeBody() {
        float previousX = snakeHeadImageView.getX();
        float previousY = snakeHeadImageView.getY();

        // Перемещаем каждую часть тела змеи к предыдущей части
        for (int i = 0; i < snakeBodyParts.size(); i++) {
            BodyPart currentPart = snakeBodyParts.get(i);
            float tempX = currentPart.getX();
            float tempY = currentPart.getY();

            currentPart.setX(previousX);
            currentPart.setY(previousY);

            currentPart.getImageView().setX(currentPart.getX());
            currentPart.getImageView().setY(currentPart.getY());

            previousX = tempX;
            previousY = tempY;
        }
    }

    private void checkFoodCollision() {
        float headX = snakeHeadImageView.getX();
        float headY = snakeHeadImageView.getY();
        float foodX = foodImageView.getX();
        float foodY = foodImageView.getY();
        float food2X = food2ImageView.getX();
        float food2Y = food2ImageView.getY();
        float collisionDistance = 30;

        // Проверка столкновения с первой едой
        if (Math.abs(headX - foodX) < collisionDistance && Math.abs(headY - foodY) < collisionDistance) {
            score++;
            updateScoreDisplay();
            placeFood();
            addSnakeBodyPart();
            addSnakeBodyPart();
        }

        // Проверка столкновения со второй едой
        if (Math.abs(headX - food2X) < collisionDistance && Math.abs(headY - food2Y) < collisionDistance) {
            score++;
            score++;
            updateScoreDisplay();
            placeFood2(); // Метод для размещения второй еды
            addSnakeBodyPart();
            addSnakeBodyPart();
        }
    }

    private void checkBadFoodCollision() {
        float headX = snakeHeadImageView.getX();
        float headY = snakeHeadImageView.getY();
        float badFoodX = badfoodImageView.getX();
        float badFoodY = badfoodImageView.getY();
        float collisionDistance = 30;

        if (Math.abs(headX - badFoodX) < collisionDistance && Math.abs(headY - badFoodY) < collisionDistance) {
            endGame();
        }
    }

    private void updateScoreDisplay() {
        scoreTextView.setText("Score: " + score);
    }

    private void endGame() {
        isGameOver = true;
        gameOverTextView.setVisibility(View.VISIBLE);
        gameOverImageView.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.VISIBLE);
    }

    private void changeFoodPosition() {
        Random random = new Random();
        int x = random.nextInt(gameFrameLayout.getWidth() - foodImageView.getWidth());
        int y = random.nextInt(gameFrameLayout.getHeight() - foodImageView.getHeight());

        foodImageView.setX(x);
        foodImageView.setY(y);
    }
    private void changeFood2Position() {
        Random random = new Random();
        int x = random.nextInt(gameFrameLayout.getWidth() - food2ImageView.getWidth());
        int y = random.nextInt(gameFrameLayout.getHeight() - food2ImageView.getHeight());

        food2ImageView.setX(x);
        food2ImageView.setY(y);
    }

    private void changeBadFoodPosition() {
        Random random = new Random();
        int x = random.nextInt(gameFrameLayout.getWidth() - badfoodImageView.getWidth());
        int y = random.nextInt(gameFrameLayout.getHeight() - badfoodImageView.getHeight());

        badfoodImageView.setX(x);
        badfoodImageView.setY(y);
    }
    private void updateFoodPositions() {
        changeFoodPosition();
        changeFood2Position();
        changeBadFoodPosition();
    }
    private void pulseAnimation(final ImageView imageView) {
        imageView.animate()
                .scaleX(1.2f)
                .scaleY(1.2f)
                .setDuration(1500)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        imageView.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(1500)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        pulseAnimation(imageView); // Рекурсивный вызов для повторения анимации
                                    }
                                })
                                .start();
                    }
                })
                .start();
    }


    private void startFoodPulseAnimations() {
        pulseAnimation(foodImageView);
        pulseAnimation(badfoodImageView);
        pulseAnimation(food2ImageView);
    }
}