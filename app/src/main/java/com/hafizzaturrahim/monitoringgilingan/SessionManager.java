package com.hafizzaturrahim.monitoringgilingan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context mcontext;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file username
    private static final String PREF_NAME = "Monitoring";

    // All Shared Preferences Keys
    private static final String isLogin = "IsLoggedIn";


    public static final String keyUsername = "name";
    public static final String keyId = "id_user";
    public static final String keyLevel = "level";

    // Constructor
    public SessionManager(Context context) {
        this.mcontext = context;
        pref = mcontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SessionManager(Context context,String token) {
        this.mcontext = context;
        pref = mcontext.getSharedPreferences("dataToken", PRIVATE_MODE);
        editor = pref.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public String getToken() {
        // return token
        return pref.getString("token", null);
    }

    /**
     * Create login session
     */
    public void createLoginSession(String username,String id, String level) {
        // Storing login value as TRUE
        editor.putBoolean(isLogin, true);

        // Storing name in pref
        editor.putString(keyUsername, username);
        editor.putString(keyId,id);
        editor.putString(keyLevel,level);
        // commit changes
        editor.commit();

        Log.d("Create Session : "+username +" " +id+ " " +level, " sukses");
    }

    public String getUsername() {
        // return username
        return pref.getString(keyUsername, null);
    }

    public String getLevel() {
        // return username
        return pref.getString(keyLevel, null);
    }

    public String getIdLogin() {
        // return username
        return pref.getString(keyId, null);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(isLogin, false);
    }
}