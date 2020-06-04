package com.example.karinarkzmobile;

import android.content.SharedPreferences;

public class AuthRepository implements ISharedPreferences{

    private SharedPreferences sharedPreferences;
    private final String IP = "IP";
    private final String LOGIN = "LOGIN";
    private final String PASSWORD = "PASSWORD";
    private String token = "TOKEN";

    @Override
    public void saveIP(String ip) {
        sharedPreferences = App.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP, ip);
        editor.commit();
    }

    @Override
    public String loadIP() {
        sharedPreferences = App.getSharedPreferences();
        return sharedPreferences.getString(IP, "");
    }

    @Override
    public void saveLogin(String login) {
        sharedPreferences = App.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP, login);
        editor.commit();
    }

    @Override
    public String loadLogin() {
        sharedPreferences = App.getSharedPreferences();
        return sharedPreferences.getString(LOGIN, "");
    }

    @Override
    public void savePassword(String pass) {
        sharedPreferences = App.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IP, pass);
        editor.commit();
    }

    @Override
    public String loadPassword() {
        sharedPreferences = App.getSharedPreferences();
        return sharedPreferences.getString(PASSWORD, "");
    }

    @Override
    public void saveToken(String token) {
        this.token = token;
    }

    @Override
    public String loadToken() {
        return this.token;
    }

    @Override
    public void deleteToken() {
        this.token = "";
    }
}
