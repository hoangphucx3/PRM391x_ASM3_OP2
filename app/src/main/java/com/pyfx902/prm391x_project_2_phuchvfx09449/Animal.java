package com.pyfx902.prm391x_project_2_phuchvfx09449;

import android.graphics.Bitmap;

public class Animal {
    private String name;
    private Bitmap image;
    private Bitmap bgImg;
    private String path;
    private String detail;
    private boolean isLike;

    public Animal(String name, Bitmap image, Bitmap bgImg, String path, String detail) {
        this.name = name;
        this.image = image;
        this.bgImg = bgImg;
        this.path = path;
        this.detail = detail;
        this.isLike = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getBgImg() {
        return bgImg;
    }

    public void setBgImg(Bitmap bgImg) {
        this.bgImg = bgImg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
