package com.jkproject.practise.newapplication.UI;
/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.UI
 *  文件名:   ForgetActivity
 *  创建者:   JK
 *  创建时间:  2017/5/15 14:23
 *  描述：    忘记密码设置页
 */

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jkproject.practise.newapplication.Entity.MyUser;
import com.jkproject.practise.newapplication.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_oldpass;
    private EditText et_newpass;
    private EditText et_newpass2;
    private EditText et_email;
    private Button bt_confirm;
    private Button bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        et_oldpass = (EditText) findViewById(R.id.password_old);
        et_newpass = (EditText) findViewById(R.id.password_new);
        et_newpass2 = (EditText) findViewById(R.id.password_new2);
        et_email = (EditText) findViewById(R.id.email);
        bt_confirm = (Button) findViewById(R.id.bt_confirm);
        bt_send = (Button) findViewById(R.id.bt_send);

        bt_confirm.setOnClickListener(this);
        bt_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_confirm:
                String oldPassword = et_oldpass.getText().toString().trim();
                String newPassword = et_newpass.getText().toString().trim();
                String newPassword2 = et_newpass2.getText().toString().trim();
                //判断是否为空；
                if (!TextUtils.isEmpty(oldPassword) &
                        !TextUtils.isEmpty(newPassword) &
                        !TextUtils.isEmpty(newPassword2)){
                    //判断两次密码是否一致；
                    if (newPassword.equals(newPassword2)){
                        MyUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(ForgetActivity.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(ForgetActivity.this, "密码修改失败" + e, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(this, "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_send:
                final String email = et_email.getText().toString().trim();
                //判断是否为空；
                if (!TextUtils.isEmpty(email)){
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ForgetActivity.this, "邮件已发送，请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(ForgetActivity.this, "邮件发送失败:" + e, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
