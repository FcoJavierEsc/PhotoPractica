package com.utad.photopractica;

import android.app.Application;
import android.graphics.Bitmap;

import com.utad.photopractica.modelo.PhotoInterfaz;


public class ModelImplement implements ModelInterfaz ,PhotoInterfaz{

    private PhotoInterfaz mPhotoInterfaz;
    public ModelImplement (PhotoInterfaz photoInterfaz){
        mPhotoInterfaz = photoInterfaz;

    }

    @Override
    public Bitmap getImagePrincipal(Application application) {
        return Comun.assetsImage(application,mPhotoInterfaz.getImagenPath());
    }

    @Override
    public Bitmap getImageX1(Application application) {
        return Comun.assetsImage(application,mPhotoInterfaz.getCategoriaIcon());
    }

    @Override
    public Bitmap getImageX2(Application application) {
        return Comun.assetsImage(application,mPhotoInterfaz.getSubCategoriaIcon());
    }

    @Override
    public String getEtiqueta() {
        return mPhotoInterfaz.getTitulo();
    }

    @Override
    public String getAnotacion() {
        return mPhotoInterfaz.getPhotoGeoLocalizacion().getGeoInfo();
    }

    @Override
    public String getSubAnotacion() {
        return mPhotoInterfaz.getFecha();
    }

    @Override
    public long getIndex() {
        return mPhotoInterfaz.getID();
    }


    ///////////// Photointerfaz


    @Override
    public void setPhotoItem(PhotoInterfaz m) {
        mPhotoInterfaz=m;
    }

    @Override
    public void setTitulo(String titulo) {
        mPhotoInterfaz.setTitulo(titulo);

    }

    @Override
    public void setCategoria(int id) {
        mPhotoInterfaz.setCategoria(id);

    }

    @Override
    public void setSubCategoria(int id) {
        mPhotoInterfaz.setSubCategoria(id);

    }

    @Override
    public String getCategoriaIcon() {
        return mPhotoInterfaz.getCategoriaIcon();
    }

    @Override
    public String getSubCategoriaIcon() {
        return mPhotoInterfaz.getSubCategoriaIcon();
    }

    @Override
    public int getCategoria() {
        return mPhotoInterfaz.getCategoria();
    }

    @Override
    public int getSubCategoria() {
        return mPhotoInterfaz.getSubCategoria();
    }

    @Override
    public long getID() {
        return mPhotoInterfaz.getID();
    }

    @Override
    public String getImagenPath() {
        return mPhotoInterfaz.getImagenPath();
    }

    @Override
    public String getTitulo() {
        return mPhotoInterfaz.getTitulo();
    }

    @Override
    public PhotoGeoLocalizacion getPhotoGeoLocalizacion() {
        return mPhotoInterfaz.getPhotoGeoLocalizacion();
    }

    @Override
    public String getFecha_Hora() {
        return mPhotoInterfaz.getFecha_Hora();
    }

    @Override
    public String getFecha() {
        return mPhotoInterfaz.getFecha();
    }

    @Override
    public void setGeoLocalizacion(PhotoGeoLocalizacion photoGeoLocalizacion) {

        mPhotoInterfaz.setGeoLocalizacion(photoGeoLocalizacion);
    }
}
