package com.jkproject.practise.newapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jkproject.practise.newapplication.Entity.MyUser;
import com.jkproject.practise.newapplication.MainActivity;
import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.Utils.SharedPreferencesTool;
import com.jkproject.practise.newapplication.View.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.UI
 *  文件名:   LoginActivity
 *  创建者:   JK
 *  创建时间:  2017/5/14 18:18
 *  描述：    登陆页
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_password;
    private Button bt_login;
    private Button bt_register;
    private TextView tv_forgetPassword;
    private CheckBox keep_password;

    private CustomDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();
    }

    private void initView() {
        et_name = (EditText) findViewById(R.id.edit_name);
        et_password = (EditText) findViewById(R.id.edit_password);
        bt_login = (Button) findViewById(R.id.button_login);
        bt_register = (Button) findViewById(R.id.button_registered);
        tv_forgetPassword = (TextView) findViewById(R.id.forget_password);
        keep_password  = (CheckBox) findViewById(R.id.keep_password);
        dialog = new CustomDialog(this, 100, 100, R.layout.dialog_loading, R.style.Theme_dialog, Gravity.CENTER/*,R.style.pop_anim_style*/);
        dialog.setCancelable(false);


        //设置选中状态；
        boolean isChecked = SharedPreferencesTool.getBoolean(this, "keeppassword", false);
        keep_password.setChecked(isChecked);
        if (isChecked){
            et_name.setText(SharedPreferencesTool.getString(this, "name", " "));
            et_password.setText(SharedPreferencesTool.getString(this, "password", " "));
        }

        bt_login.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        keep_password.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                //判断是否为空；
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)){
                    dialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);

                    user.login(new SaveListener<MyUser>() {

                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            if (e == null){
                                //判断邮箱是否验证；
                                if (user.getEmailVerified()){
                                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "请验证邮箱再进行登陆", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "登录失败:" + e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.button_registered:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forget_password:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferencesTool.putBoolean(this, "keeppassword", keep_password.isChecked());

        if (keep_password.isChecked()){
            //保存信息；
            SharedPreferencesTool.putString(this, "name", et_name.getText().toString().trim());
            SharedPreferencesTool.putString(this, "password", et_password.getText().toString().trim());
        }else {
            //删除填写的信息；
            SharedPreferencesTool.deleteOne(this, "name");
            SharedPreferencesTool.deleteOne(this, "password");
        }
    }
}
