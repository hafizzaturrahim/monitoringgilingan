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

        Log.d("Create Session : "+username, " sukses");
    }

    public void createIdLogin(String username) {
        // Storing login value as TRUE
        editor.putBoolean(isLogin, true);

        // Storing name in pref
        editor.putString("id", username);

        // commit changes
        editor.commit();

        Log.d("Create Session : "+username, " sukses");
    }

    public String getIdLogin() {
        // return username
        return pref.getString(keyId, null);
    }

    public void createNamaUser(String nama) {
        // Storing login value as TRUE
        editor.putBoolean(isLogin, true);

        // Storing name in pref
        editor.putString("nama", nama);

        // commit changes
        editor.commit();

    }

    public String getNamaUser() {
        // return username
        return pref.getString("nama", null);
    }


    public void createRawatJalan(boolean isCreated){
        editor.putBoolean("rawatJalan",isCreated);
        editor.commit();
    }

    public Boolean getSessionRawatJalan(){
        return pref.getBoolean("rawatJalan",false);
    }

    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(keyUsername, pref.getString(keyUsername, null));

        // return user
        return user;
    }

    public String getUsernameSession() {
        // return username
        return pref.getString(keyUsername, null);
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(mcontext, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//            Activity activity = (Activity) mcontext;
            // Staring Login Activity
            mcontext.startActivity(i);
//            activity.finish();
        }

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