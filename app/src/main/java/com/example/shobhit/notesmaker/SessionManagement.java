package com.example.shobhit.notesmaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


/**
 * Created by shobhit on 18/6/17.
 */

public class SessionManagement {



    //Shared Preferences
    SharedPreferences sharedPreferences;

    //Editor for SharedPreferences
    SharedPreferences.Editor editor;

    //Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "LoginPref";

    //Shared preference key
    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_UNAME = "uname" ;


    //Constructor
    SessionManagement(Context context)
    {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }





    /**
     * Create Login Session
     */
    public void createLoginSession(String uname){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing uname in pref
        editor.putString(KEY_UNAME, uname);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Main Activity
            Intent i = new Intent(context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            context.startActivity(i);
        }

    }

    /**
    * Getting user name
    */
    public String getUname()
    {
        return sharedPreferences.getString(KEY_UNAME,null);
    }


    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(context, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Starting Login Activity
        context.startActivity(i);

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }
}
