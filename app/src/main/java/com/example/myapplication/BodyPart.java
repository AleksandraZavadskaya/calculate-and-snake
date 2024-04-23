package com.example.myapplication;

import android.widget.ImageView;

public class BodyPart {
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

    public float getX() {return x;}

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
