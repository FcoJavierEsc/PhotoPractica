package com.utad.photopractica.modelo.iu;


import android.app.Application;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.utad.photopractica.Comun;


public class Grid_Adapter_INT extends BaseAdapter {

    private int mMaxCount;
    private Application mApplication;
    private String mPathIcon;

    public Grid_Adapter_INT(Application application, int maxCount, String pathIcon) {
        mApplication = application;
        mMaxCount = maxCount;
        mPathIcon = pathIcon;
    }

    public String getPathIcon(){return mPathIcon;}

    @Override
    public int getCount() {
        return mMaxCount;
    }

    @Override
    public Object getItem(int position) {
        return new Integer(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mApplication.getApplicationContext());
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(6, 6, 6, 6);
        } else {
            imageView = (ImageView) convertView;
        }


        imageView.setImageBitmap(Comun.assetsImage(mApplication, Comun.getIcon(mPathIcon, position)));
        return imageView;
    }
}
