package com.example.insiderapplication;

import android.app.Application;

import com.useinsider.insider.Insider;

public class InsiderApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        // DO NOT FORGET to change your_partner_name.
        // Use only lowercase and your_partner_name is provided by Insider.
        Insider.Instance.init(this, "your_partner_name");

        // DO NOT FORGET to change MySplashActivity.class
        Insider.Instance.setSplashActivity(SplashActivity.class);
    }
}