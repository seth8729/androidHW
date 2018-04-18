package com.example.user.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by user on 2018/4/14.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] miImager;
    public ImageAdapter(Context context,Integer[] imgArr){
        mContext=context;
        miImager=imgArr;
    }
    @Override
    public int getCount() {
        return miImager.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView v;
        if(convertView==null){
            v = new ImageView(mContext);
            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
                v.setLayoutParams(new GridView.LayoutParams(params));
                v.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                v.setPadding(10,10,10,10);
        }
        else
            v = (ImageView) convertView;

        v.setImageResource(miImager[position]);
        return v;
    }
}
