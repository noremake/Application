package com.jkproject.practise.newapplication.UI;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jkproject.practise.newapplication.Entity.MyUser;
import com.jkproject.practise.newapplication.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.UI
 *  文件名:   RegisterActivity
 *  创建者:   JK
 *  创建时间:  2017/5/14 19:18
 *  描述：    注册页
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText ed_name;
    private EditText ed_age;
    private EditText ed_introduction;
    private EditText ed_password;
    private EditText ed_password2;
    private EditText ed_mail;
    private Button ed_confirm;
    private RadioGroup mRadioGroup;

    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
    }

    private void initView(){
        ed_name = (EditText) findViewById(R.id.edit_name);
        ed_age = (EditText) findViewById(R.id.edit_age);
        ed_introduction = (EditText) findViewById(R.id.edit_introduction);
        ed_password = (EditText) findViewById(R.id.edit_password);
        ed_password2 = (EditText) findViewById(R.id.edit_password2);
        ed_mail = (EditText) findViewById(R.id.edit_mail);
        ed_confirm = (Button) findViewById(R.id.confirm);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);

        ed_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                String name = ed_name.getText().toString().trim();
                String age = ed_age.getText().toString().trim();
                String mail = ed_mail.getText().toString().trim();
                String password = ed_password.getText().toString().trim();
                String password2 = ed_password2.getText().toString().trim();
                String introduction = ed_introduction.getText().toString().trim();

                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(password2) &
                        !TextUtils.isEmpty(mail)){
                    //判断两次输入密码师是否一致；
                    if (password.equals(password2)){
                        //判断性别；
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                if (checkedId == R.id.rb_boy){
                                    isGender = true;
                                }else if (checkedId == R.id.rb_girl){
                                    isGender = false;
                                }
                            }
                        });
                        //判断简介是否为空；
                        if (TextUtils.isEmpty(introduction)){
                            introduction = "这个人很懒，什么都没有留下。";
                        }
                        MyUser myUser = new MyUser();
                        myUser.setAge(Integer.parseInt(age));
                        myUser.setEmail(mail);
                        myUser.setSex(isGender);
                        myUser.setPassword(password);
                        myUser.setUsername(name);
                        myUser.setIntroduction(introduction);

                        myUser.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if(e==null){
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegisterActivity.this, "注册失败:" + e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        finish();
                    }else {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
