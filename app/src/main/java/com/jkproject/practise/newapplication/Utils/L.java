package com.jkproject.practise.newapplication.Utils;
/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Utils
 *  文件名:   L
 *  创建者:   JK
 *  创建时间:  2017/5/10 18:59
 *  描述：    TODO
 */

import android.util.Log;

public class L {

    public final static String TAG = "smartbutler";

    public final static boolean DEBUG = true;

    public static void d(String text) {
        if(DEBUG){
            Log.d(TAG, text);
        }
    }

    public static void i(String text) {
        if(DEBUG){
            Log.i(TAG, text);
        }
    }

    public static void w(String text) {
        if(DEBUG){
            Log.w(TAG, text);
        }
    }

    public static void e(String text) {
        if(DEBUG){
            Log.e(TAG, text);
        }
    }
}
