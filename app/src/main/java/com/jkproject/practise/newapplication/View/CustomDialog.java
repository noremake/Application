package com.jkproject.practise.newapplication.View;
/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.View
 *  文件名:   CustomDialog
 *  创建者:   JK
 *  创建时间:  2017/5/16 16:23
 *  描述：    TODO
 */

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

public class CustomDialog extends Dialog {


    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity/*,int anim*/){
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
        //window.setWindowAnimations(anim);

    }
}
