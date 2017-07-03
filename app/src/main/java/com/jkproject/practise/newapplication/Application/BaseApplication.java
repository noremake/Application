package com.jkproject.practise.newapplication.Application;

import android.app.Application;

import com.jkproject.practise.newapplication.Utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * 项目名：  NewApplication
 * 包名：    com.jkproject.practise.newapplication.Application
 * 文件名:   BaseApplication
 * 创建者:   JK
 * 创建时间:  ${DATA} 13:28
 * 描述：    TODO
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob；
        Bmob.initialize(this, StaticClass.BOMB_APP_ID);
    }
}
