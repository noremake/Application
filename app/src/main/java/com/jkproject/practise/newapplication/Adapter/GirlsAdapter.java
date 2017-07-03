package com.jkproject.practise.newapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.jkproject.practise.newapplication.Entity.GirlsData;
import com.jkproject.practise.newapplication.R;
import com.jkproject.practise.newapplication.Utils.PicassoUtil;

import java.util.List;

/*
 *  项目名：  NewApplication 
 *  包名：    com.jkproject.practise.newapplication.Adapter
 *  文件名:   GirlsAdapter
 *  创建者:   JK
 *  创建时间:  2017/5/22 12:29
 *  描述：    TODO
 */

public class GirlsAdapter extends BaseAdapter {

    private Context mContext;
    private GirlsData data;
    private LayoutInflater inflater;
    private List<GirlsData> mList;
    private WindowManager wm;

    private int width;

    public GirlsAdapter(Context mContext, List<GirlsData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.girls_item, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.girl_img);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        //解析图片
        String url = data.getGirlsUrl();

        PicassoUtil.loadImageSize(mContext, url, viewHolder.imageView, width / 2, 500);

        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}
