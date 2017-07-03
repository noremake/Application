package com.jkproject.practise.newapplication.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/*
 * 项目名：  NewApplication
 * 包名：    com.jkproject.practise.newapplication.UI
 * 文件名:   BaseActivity
 * 创建者:   JK
 * 创建时间:  ${DATE} 13:31
 * 描述：    TODO
 */

public class BaseActivity extends AppCompatActivity {

    /*
    * 统一的属性；
    * 统一的接口；
    * 统一的方法；
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
