package com.example.karinarkzmobile;

public interface ISharedPreferences {
    void saveIP(String ip);
    String loadIP ();
    void saveLogin(String login);
    String loadLogin();
    void savePassword(String pass);
    String loadPassword();
    void saveToken(String token);
    String loadToken();
    void deleteToken();
}
