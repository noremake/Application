package com.jkproject.practise.newapplication.Utils;
/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Utils
 *  文件名:   SharedPreferencesTool
 *  创建者:   JK
 *  创建时间:  2017/5/10 19:07
 *  描述：    SharedPreferences封装
 */

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesTool {

    public final static String NAME = "config";

    public static void putString(Context mContext, String key, String value){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        prefs.edit().putString(key, value).commit();
    }

    public static String getString(Context mContext, String key, String deValue){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return prefs.getString(key, deValue);
    }

    public static void putInt(Context mContext, String key, int value){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(key, value).commit();
    }

    public static int getInt(Context mContext, String key, int deValue){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return prefs.getInt(key, deValue);
    }

    public static void putBoolean(Context mContext, String key, boolean value){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context mContext, String key, boolean deValue){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, deValue);
    }

    //删除单个
    public static void deleteOne(Context mContext, String key){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(key).commit();
    }

    //删除所有
    public static void deleteAll(Context mContext){
        SharedPreferences prefs = mContext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        prefs.edit().clear().commit();
    }
}
