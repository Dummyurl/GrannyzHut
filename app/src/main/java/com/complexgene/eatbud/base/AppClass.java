package com.complexgene.eatbud.base;

import android.app.Application;

import com.complexgene.eatbud.model.Cart;

import java.util.LinkedHashMap;

public class AppClass extends Application {

    private static AppClass instance;

    public LinkedHashMap<String,Cart> cart;

    public static AppClass getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

    }
}
