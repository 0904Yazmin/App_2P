package com.example.app_segundoparcial.json;

import java.io.Serializable;

public class MyData implements Serializable {

    private String password;
    private int img;
    private String red;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }
}
