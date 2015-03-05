package com.utad.photopractica.modelo;


import android.database.Cursor;
import android.util.Log;

import com.utad.photopractica.Comun;
import com.utad.photopractica.modelo.scope.ScopeApp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhotoTags implements PhotoInterfaz {

    private long id;
    private String title;
    private String uriSite;
    private int category;
    private int subCategory;
    private String date;
    private PhotoGeoLocalizacion mPhotoGeoLocalizacion;

    public PhotoTags() {
        title = null;
        id = -1;
        category = 0;
        subCategory = 0;
        uriSite = null;
        date = null;
        mPhotoGeoLocalizacion = new PhotoGeoLocalizacion();
    }

    public PhotoTags(String theTitle, String fotoPath, PhotoGeoLocalizacion gps) {
        title = theTitle;
        uriSite = fotoPath;
        category = ScopeApp.getCatDefault();
        subCategory = ScopeApp.getSubCatDefault();
        id = -1;
        date = null;
        mPhotoGeoLocalizacion = new PhotoGeoLocalizacion(gps);

    }


    @Override
    public void setGeoLocalizacion(PhotoGeoLocalizacion photoGeoLocalizacion) {
        mPhotoGeoLocalizacion.setLatitud(photoGeoLocalizacion.getLatitud());
        mPhotoGeoLocalizacion.setLongitud(photoGeoLocalizacion.getLongitud());
        mPhotoGeoLocalizacion.setProvincia(photoGeoLocalizacion.getProvincia());
        mPhotoGeoLocalizacion.setCodpostal(photoGeoLocalizacion.getCodpostal());
        mPhotoGeoLocalizacion.setNombreTipoVia(photoGeoLocalizacion.getNombreTipoVia());
        mPhotoGeoLocalizacion.setMunicipio(photoGeoLocalizacion.getMunicipio());
    }


    @Override
    public String getCategoriaIcon() {
        return Comun.getIcon(Comun.CATEGORY, category);
    }

    @Override
    public String getSubCategoriaIcon() {
        return Comun.getIcon(Comun.SUBCATEGORY, subCategory);
    }

    @Override
    public int getCategoria() {
        return category;
    }

    @Override
    public int getSubCategoria() {
        return subCategory;
    }

    @Override
    public void setPhotoItem(PhotoInterfaz m) {

        setTitulo(m.getTitulo());
        setUriSite(m.getImagenPath());
        setCategoria(m.getCategoria());
        setSubCategoria(m.getSubCategoria());
        setId(m.getID());
        setDate(m.getFecha_Hora());
        setGeoLocalizacion(m.getPhotoGeoLocalizacion());
    }

    @Override
    public void setTitulo(String titulo) {
        title = titulo;
    }

    @Override
    public void setCategoria(int id) {

        category = id;
    }

    @Override
    public void setSubCategoria(int id) {

        subCategory = id;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public String getImagenPath() {
        return uriSite;
    }

    @Override
    public String getTitulo() {
        return title;
    }

    @Override
    public PhotoGeoLocalizacion getPhotoGeoLocalizacion() {
        return mPhotoGeoLocalizacion;
    }


    public PhotoTags(Cursor cursor) {
        int cols = cursor.getColumnCount();
        mPhotoGeoLocalizacion = new PhotoGeoLocalizacion();
        String[] colNames = cursor.getColumnNames();
        // String[] colPhoto = P_Photo.getTableCol();
        for (int i = 0; i < cols; i++) {
            String columna = colNames[i];
            if (columna.equals(P_Photo.getColumnId())) {
                setId(cursor.getLong(i));
            } else if (columna.equals(P_Photo.getColumnTitle())) {
                setTitulo(cursor.getString(i));
            } else if (columna.equals(P_Photo.getColumnUriSite())) {
                setUriSite(cursor.getString(i));
            } else if (columna.equals(P_Photo.getColumnCategoryId())) {
                setCategory(cursor.getInt(i));
            } else if (columna.equals(P_Photo.getColumnSubCategoryId())) {
                setSubCategory(cursor.getInt(i));
            } else if (columna.equals(P_Photo.getColumnCreateAt())) {
                setDate(cursor.getString(i));
            } else if (columna.equals(P_Photo.getColumnLat())) {
                mPhotoGeoLocalizacion.setLatitud(cursor.getDouble(i));
            } else if (columna.equals(P_Photo.getColumnLong())) {
                mPhotoGeoLocalizacion.setLongitud(cursor.getDouble(i));
            } else if (columna.equals(P_Photo.getColumnCodPostal())) {
                mPhotoGeoLocalizacion.setCodpostal(cursor.getString(i));
            } else if (columna.equals(P_Photo.getColumnMunicipio())) {
                mPhotoGeoLocalizacion.setMunicipio(cursor.getString(i));
            } else if (columna.equals(P_Photo.getColumnNombreTipoVia())) {
                mPhotoGeoLocalizacion.setNombreTipoVia(cursor.getString(i));
            } else if (columna.equals(P_Photo.getColumnProvincia())) {
                mPhotoGeoLocalizacion.setProvincia(cursor.getString(i));
            } else {
                Log.v("BBDDPHOTOTAGS", "ALGO FALTA " + columna);
            }
        }
        Log.v("dATOS", cursor.getString(2));
    }

    private void setId(long id) {
        this.id = id;
    }


    private void setUriSite(String uriSite) {
        this.uriSite = uriSite;
    }


    private void setCategory(int category) {
        this.category = category;
    }

    private void setSubCategory(int subCategory) {
        this.subCategory = subCategory;
    }

    private void setDate(String date) {
        this.date = date;
    }


    @Override
    public String getFecha_Hora() {

        return date;
    }


    @Override
    public String getFecha() {

        String retorno = "";
        try {
            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date ff = fecha.parse(date);
            retorno = new SimpleDateFormat("dd/MM/yyyy").format(ff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Log.v("STRING","FECHAS "+date+"<->"+retorno);
        return retorno;
    }

    @Override
    public String toString() {
        return "PhotoTags{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", uriSite='" + uriSite + '\'' +
                ", category=" + category +
                ", subCategory=" + subCategory +

                ", date='" + date + '\'' +
                '}';
    }


}