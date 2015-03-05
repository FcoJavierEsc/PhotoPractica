package com.utad.photopractica.recycler_view;


import android.app.Application;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.utad.photopractica.ModelInterfaz;
import com.utad.photopractica.R;


public class RecyclerViewItem  {
    private ImageView mImagePrincipal;
    private TextView mTitulo;
    private ImageView mImageX1;
    private ImageView mImageX2;
    private TextView mAnotacion;
    private TextView mSubAnotacion;
    private Application mApplication;



    public RecyclerViewItem(Application application,View itemView) {

        mApplication = application;
        setPhotoImage((ImageView) itemView.findViewById(R.id.show_imgprincipal));
        setPhotoTitle((TextView) itemView.findViewById(R.id.show_titulo));
        setImageX1((ImageView) itemView.findViewById(R.id.show_imgexp1));
        setImageX2((ImageView) itemView.findViewById(R.id.show_imgexp2));
        setAnotacion((TextView) itemView.findViewById(R.id.show_anotacion));
        setSubAnotacion((TextView) itemView.findViewById(R.id.show_subanotacion));
    }



    public void setPhotoItemViewers(ModelInterfaz model,
                                    boolean setOneLineTitle) {
        if (model == null)
            return;

        mImagePrincipal.setImageBitmap(model.getImagePrincipal(mApplication));
        if (setOneLineTitle) {
            mTitulo.setMaxLines(1);
            mTitulo.setHint("...");
        }
        mTitulo.setText(model.getEtiqueta());


        mImageX1.setImageBitmap(model.getImageX1(mApplication));
        mImageX2.setImageBitmap(model.getImageX2(mApplication));
        mAnotacion.setText(model.getAnotacion());
        mSubAnotacion.setText((model.getSubAnotacion()));
    }


    public TextView getSubAnotacion() {
        return mSubAnotacion;
    }

    public void setSubAnotacion(TextView subAnotacion) {
        this.mSubAnotacion = subAnotacion;
    }

    public ImageView getPhotoImage() {
        return mImagePrincipal;
    }

    public void setPhotoImage(ImageView photoImage) {
        this.mImagePrincipal = photoImage;
    }

    public TextView getPhotoTitle() {
        return mTitulo;
    }

    public void setPhotoTitle(TextView photoTitle) {
        this.mTitulo = photoTitle;
    }

    public ImageView getImageX1() {
        return mImageX1;
    }

    public void setImageX1(ImageView imageX1) {
        this.mImageX1 = imageX1;
    }

    public ImageView getImageX2() {
        return mImageX2;
    }

    public void setImageX2(ImageView imageX2) {
        this.mImageX2 = imageX2;
    }

    public TextView getAnotacion() {
        return mAnotacion;
    }

    public void setAnotacion(TextView anotacion) {
        this.mAnotacion = anotacion;
    }

}