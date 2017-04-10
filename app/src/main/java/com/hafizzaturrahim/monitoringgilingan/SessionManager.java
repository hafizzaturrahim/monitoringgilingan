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

    // User name (make variable public to access from outside)
    public static final String keyUsername = "name";

    // Email address (make variable public to access from outside)
    public static final String keyId = "id_user";

    // Constructor
    public SessionManager(Context context) {
        this.mcontext = context;
        pref = mcontext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String username,String id) {
        // Storing login value as TRUE
        editor.putBoolean(isLogin, true);

        // Storing name in pref
        editor.putString(keyUsername, username);
        editor.putString(keyId,id);
        // commit changes
        editor.commit();

        Log.d("Create Session : "+username +" " +id, " sukses");
    }

    public String getIdLogin() {
        // return username
        return pref.getString(keyId, null);
    }

    public String getUsernameSession() {
        // return username
        return pref.getString(keyUsername, null);
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