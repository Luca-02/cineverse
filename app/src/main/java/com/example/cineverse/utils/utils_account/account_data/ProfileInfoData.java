package com.example.cineverse.utils.utils_account.account_data;

import androidx.annotation.DrawableRes;

public class ProfileInfoData {
    private String text;
    private int number;
    @DrawableRes private int imageRes;

    public ProfileInfoData(String text, int number, int imageRes) {
        this.text = text;
        this.number = number;
        this.imageRes = imageRes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

}
