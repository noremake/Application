package com.jkproject.practise.newapplication.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jkproject.practise.newapplication.Entity.MyUser;
import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.UI.LoginActivity;
import com.jkproject.practise.newapplication.Utils.L;
import com.jkproject.practise.newapplication.Utils.StaticClass;
import com.jkproject.practise.newapplication.View.CustomDialog;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.jkproject.practise.newapplication.R.id.bt_photo;
import static com.jkproject.practise.newapplication.R.id.edit_user;
import static com.jkproject.practise.newapplication.R.id.et_age;
import static com.jkproject.practise.newapplication.R.id.et_desc;
import static com.jkproject.practise.newapplication.R.id.et_sex;
import static com.jkproject.practise.newapplication.R.id.et_username;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Fragment
 *  文件名:   UserFragment
 *  创建者:   JK
 *  创建时间:  2017/5/8 19:13
 *  描述：    TODO
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private TextView editUser;
    private EditText username;
    private EditText userage;
    private EditText usersex;
    private EditText userdesc;
    private Button confirm;
    private Button userexit;
    private CustomDialog photoDialog;
    private CircleImageView circleImag;
    private Button btPhoto;
    private Button pic;
    private Button btReturn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, null);
        findView(view);
        return view;
    }

    private void findView(View v) {
        editUser = (TextView) v.findViewById(edit_user);
        username = (EditText) v.findViewById(et_username);
        userage = (EditText) v.findViewById(et_age);
        usersex = (EditText) v.findViewById(et_sex);
        userdesc = (EditText) v.findViewById(et_desc);
        confirm = (Button) v.findViewById(R.id.et_confirm);
        userexit = (Button) v.findViewById(R.id.bt_exit);
        circleImag = (CircleImageView) v.findViewById(R.id.circle_img);

        editUser.setOnClickListener(this);
        confirm.setOnClickListener(this);
        userexit.setOnClickListener(this);
        circleImag.setOnClickListener(this);


        photoDialog = new CustomDialog(getActivity(), 100, 100, R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM/*,R.style.pop_anim_style*/);
        photoDialog.setCancelable(false);

        btPhoto = (Button) photoDialog.findViewById(bt_photo);
        pic = (Button) photoDialog.findViewById(R.id.bt_pic);
        btReturn = (Button) photoDialog.findViewById(R.id.bt_return);

        btPhoto.setOnClickListener(this);
        pic.setOnClickListener(this);
        btReturn.setOnClickListener(this);

        setEnabled(false);

        //通过本地缓存设置具体的值；
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        username.setText(userInfo.getUsername());
        userage.setText(userInfo.getAge() + "");
        usersex.setText(userInfo.isSex() ? "男" : "女");
        userdesc.setText(userInfo.getIntroduction());
    }

    private void setEnabled(boolean is) {
        userdesc.setEnabled(is);
        username.setEnabled(is);
        userage.setEnabled(is);
        usersex.setEnabled(is);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_user:
                setEnabled(true);
                confirm.setVisibility(View.VISIBLE);
                break;
            case R.id.et_confirm:
                String name = username.getText().toString().trim();
                String age = userage.getText().toString().trim();
                String sex = usersex.getText().toString().trim();
                String desc = userdesc.getText().toString().trim();

                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)){
                    MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setAge(Integer.parseInt(age));
                    //性别
                    if (sex.equals("男")) {
                        user.setSex(true);
                    } else {
                        user.setSex(false);
                    }
                    //简介
                    if (!TextUtils.isEmpty(desc)) {
                        user.setIntroduction(desc);
                    } else {
                        user.setIntroduction("这个人很懒，什么都没有留下。");
                    }
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                //修改成功
                                setEnabled(false);
                                confirm.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "输入框不能为空", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_exit:
                BmobUser.logOut();   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.circle_img:
                photoDialog.show();
                break;
            case bt_photo:
                toPhoto();
                break;
            case R.id.bt_pic:
                toPicture();
                break;
            case R.id.bt_return:
                photoDialog.dismiss();
                break;
        }
    }

    //存储裁剪后的图片；
    private File tempFile = null;

    //跳转相册；
    private void toPicture() {
        //调用相册；
        Intent picIntent = new Intent(Intent.ACTION_PICK);
        //设置类型；
        picIntent.setType("image/*");
        startActivityForResult(picIntent, StaticClass.REQUEST_CODE_IMAGE);
        photoDialog.dismiss();
    }

    //跳转相机；
    private void toPhoto() {
        //准备系统相机；
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //putExtra(键，值)；
        //new File（地址，文件名）---创建文件；
        //Environment.getExternalStorageDirectory()---返回存储卡的名称，在该目录下创建临时文件；
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME)));
        //调用相机；
        startActivityForResult(photoIntent, StaticClass.REQUEST_CODE_PHOTO);
        photoDialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED){
            switch (requestCode){
                //图库操作结束后返回的请求码；
                case StaticClass.REQUEST_CODE_IMAGE:
                    //裁剪图片；
                    setImageCrop(data.getData());
                    break;
                //相机操作结束后返回的请求码；
                case StaticClass.REQUEST_CODE_PHOTO:
                    //设置相机返回的图片所需要存储的地址；
                    tempFile = new File(Environment.getExternalStorageDirectory(), StaticClass.PHOTO_IMAGE_FILE_NAME);
                    //将图片进行裁剪；
                    setImageCrop(Uri.fromFile(tempFile));
                    break;
                //裁剪操作结束后返回的请求码；
                case StaticClass.RESULT_REQUEST_CODE:
                    //判断是否有数据返回，因为用户可能取消裁剪；
                    if (data != null){
                        setImageToView(data);
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //将图片设置到circleImageView中；
    private void setImageToView(Intent data) {
        //getExtras() 返回 Bundle 对象；
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            //bundle序列化数据；
            Bitmap bitmap = bundle.getParcelable("data");
            circleImag.setImageBitmap(bitmap);
        }
    }

    //裁剪图片；
    private void setImageCrop(Uri uri) {
        if (uri == null) {
            L.e("uri == null");
            return;
        }
        //准备系统自带裁剪工具；
        Intent intent = new Intent("com.android.camera.action.CROP");
        //给出图片地址和类型；
        intent.setDataAndType(uri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        //裁剪宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //裁剪图片的质量
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        //发送数据
        intent.putExtra("return-data", true);
        //开始调用裁剪工具；
        startActivityForResult(intent, StaticClass.RESULT_REQUEST_CODE);
    }
}
