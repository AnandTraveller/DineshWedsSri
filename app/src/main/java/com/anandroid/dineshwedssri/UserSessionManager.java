///*
//package com.anandroid.dineshwedssri;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//
//import com.app.api.ServerJsonResponseKey;
//import com.app.api.ServerParams;
//import com.app.views.activity.HomeAct;
//import com.wekancode.vms.App;
//
//import java.util.HashMap;
//
//*/
///**
// * Created by Anand  on 1/30/2017.
// *//*
//
//
//public class UserSessionManager {
//
//    private static UserSessionManager mInstance;
//
//    // Shared Preferences reference
//    //private static
//    SharedPreferences pref;
//
//    // Editor reference for Shared preferences
//    Editor editor;
//
//    // Context
//    //Context _context;
//
//    // Shared pref mode
//    int PRIVATE_MODE = 0;
//
//
//    // Constructor
//    private UserSessionManager() {
//        //this._context = context;
//        pref = getSharedPreferences("wedding", PRIVATE_MODE);
//        editor = pref.edit();
//    }
//
//    */
///**
//     * Singleton construct design pattern.
//     *
//     * @return single instance of SessionManagerSingleton
//     *//*
//
//    public static synchronized UserSessionManager getInstance() {
//        if (mInstance == null) {
//            mInstance = new UserSessionManager();
//        }
//        return mInstance;
//    }
//
//    */
///**
//     * Check login method will check user login status
//     * If false it will redirect user to login page
//     * Else do anything
//     *//*
//
//    public boolean checkLogin(Context context) {
//        // Check login status
//        if (!this.isUserLoggedIn()) {
//
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(context, HomeAct.class);
//
//            // Closing all the Activities from stack
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            context.startActivity(i);
//
//            return true;
//        }
//        return false;
//    }
//
//  */
///*  public void saveTennisTimingIds(String morningId
//            , String afternoonId, String eveningId, String nightId) {
//        editor.putString(DataBaseKey.MORNING_TIME, morningId);
//        editor.putString(DataBaseKey.AFTERNOON_TIME, afternoonId);
//        editor.putString(DataBaseKey.EVENING_TIME, eveningId);
//        editor.putString(DataBaseKey.NIGHT_TIME, nightId);
//        editor.commit();
//    }*//*
//
//
//    // VMS
//
//    // Check for login
//    public void userLoggedIn() {
//        editor.putBoolean("IS_USER_LOGIN", true);
//        editor.commit();
//    }
//
//    public boolean isUserLoggedIn() {
//        return pref.getBoolean("IS_USER_LOGIN", false);
//    }
//
//
//}*/
