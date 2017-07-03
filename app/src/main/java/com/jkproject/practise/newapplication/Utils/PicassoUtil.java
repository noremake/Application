package com.jkproject.practise.newapplication.Utils;
/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Utils
 *  文件名:   PicassoUtil
 *  创建者:   JK
 *  创建时间:  2017/5/22 9:47
 *  描述：    TODO
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.jkproject.practise.newapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class PicassoUtil {

    public static void loadImageView(Context context, String imageUrl, ImageView imageView){
        Picasso.with(context).load(imageUrl).into(imageView);
    }

    public static void loadImageSize(Context context, String imageUrl, ImageView imageView, int width, int height){
        Picasso.with(context).load(imageUrl).resize(width, height).centerCrop().into(imageView);
    }

    //加载默认图片
    public static void holderImage(Context context, String imageUrl, ImageView imageView){
        Picasso.with(context).load(imageUrl).placeholder(R.drawable.point_green).error(R.mipmap.ic_launcher).into(imageView);
    }

    public static void cropImage(Context context, String imageUrl, ImageView imageView){
        Picasso.with(context).load(imageUrl).transform(new CropSquareTransformation()).into(imageView);
    }

    public static class CropSquareTransformation implements Transformation {

        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() {
            return "square()";
        }
    }
}
