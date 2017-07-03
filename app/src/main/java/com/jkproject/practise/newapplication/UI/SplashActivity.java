package com.jkproject.practise.newapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.Utils.SharedPreferencesTool;
import com.jkproject.practise.newapplication.Utils.StaticClass;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.UI
 *  文件名:   SplashActivity
 *  创建者:   JK
 *  创建时间:  2017/5/10 19:46
 *  描述：    闪屏页
 */

public class SplashActivity extends AppCompatActivity {

    private TextView textView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }else{
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        initView();
    }

    public void initView(){
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        textView = (TextView)findViewById(R.id.textView);
    }

    private boolean isFirst(){
        boolean isFirst = SharedPreferencesTool.getBoolean(this, StaticClass.SPLASH_IS_FIRST, true);

        if(isFirst){
           SharedPreferencesTool.putBoolean(this, StaticClass.SPLASH_IS_FIRST, false);
            return true;
        }else{
            return false;
        }
    }

    //禁止返回键

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
