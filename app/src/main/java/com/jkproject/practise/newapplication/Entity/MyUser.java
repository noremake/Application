package com.jkproject.practise.newapplication.Entity;

import cn.bmob.v3.BmobUser;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Entity
 *  文件名:   MyUser
 *  创建者:   JK
 *  创建时间:  2017/5/14 19:59
 *  描述：    TODO
 */

public class MyUser extends BmobUser {

    private boolean sex;
    private int age;
    private String introduction;

    public boolean isSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
