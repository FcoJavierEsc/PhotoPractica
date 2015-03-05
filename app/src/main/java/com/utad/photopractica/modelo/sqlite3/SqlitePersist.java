package com.utad.photopractica.modelo.sqlite3;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.util.Log;

import com.utad.photopractica.modelo.P_ParClaveValor;
import com.utad.photopractica.modelo.ParClaveValor;
import com.utad.photopractica.modelo.PhotoInterfaz;
import com.utad.photopractica.modelo.P_Photo;
import com.utad.photopractica.modelo.PhotoTags;
import com.utad.photopractica.modelo.geo.GeoToponimo;
import com.utad.photopractica.modelo.geo.GpsLocalication;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class SqlitePersist extends Binder {


    private static String sTitle;
    private static int sCat;
    private static int sSubcat;
    private static int sPrefOrg;
    private static boolean sIsGps;


    public SqlitePersist getServiceSqlite() {
        // Return this instance of LocalService so clients can call public methods
        return SqlitePersist.this;
    }


    private void getZonaGeo(final PhotoInterfaz photoInterfaz) {

        PhotoInterfaz.PhotoGeoLocalizacion geoLocalizacion = photoInterfaz.getPhotoGeoLocalizacion();
        PhotoInterfaz parametro;
        GeoToponimo geoToponimo = new GeoToponimo(geoLocalizacion, new GeoToponimo.GeoToponimoListener() {
            @Override
            public void dialogFinish(PhotoInterfaz.PhotoGeoLocalizacion geoLocalizacion) {
                Log.v("RECIBO", " ALGO " + geoLocalizacion.getMunicipio());
                photoInterfaz.setGeoLocalizacion(geoLocalizacion);
                updatePhotoGeoLocalizacion(photoInterfaz);
            }
        });

        new Thread(geoToponimo).start();


    }

    //TODO: AVISO A NAVEGANTES

    private void savePreferences(String key, String value) {


        ContentValues values = new ContentValues();
        SQLiteDatabase que = MySQLiteHelper.getDataBase();

        if (que != null) {
            // values.put(P_Dictionary.getColumnKey(),clave);
            values.put(P_ParClaveValor.getColumnValue(), value);
            que.update(P_ParClaveValor.getTableName(),
                    values,
                    P_ParClaveValor.getColumnKey() + " = \"" + key + "\"",
                    null);
        }
    }




    private void savePreferences(String key, boolean value) {


        ContentValues values = new ContentValues();
        SQLiteDatabase que = MySQLiteHelper.getDataBase();

        if (que != null) {
            // values.put(P_Dictionary.getColumnKey(),clave);
            values.put(P_ParClaveValor.getColumnValue(), value);
            que.update(P_ParClaveValor.getTableName(),
                    values,
                    P_ParClaveValor.getColumnKey() + " = \"" + key + "\"",
                    null);
        }
    }

    private void savePreferences(String key, int value) {


        ContentValues values = new ContentValues();
        SQLiteDatabase que = MySQLiteHelper.getDataBase();

        if (que != null) {
            // values.put(P_Dictionary.getColumnKey(),clave);
            values.put(P_ParClaveValor.getColumnValue(), value);
            que.update(P_ParClaveValor.getTableName(),
                    values,
                    P_ParClaveValor.getColumnKey() + " = \"" + key + "\"",
                    null);
        }
    }




    public void savePreferencesGps(boolean gps) {
        String clave = P_ParClaveValor.DIC_GPSACTIVO;
        sIsGps = gps;
        savePreferences(clave, gps);
    }


    public void savePreferencesTit(String str) {
        String clave = P_ParClaveValor.DIC_TITULO;
        sTitle = str;
        savePreferences(clave, str);
    }

    public void savePreferencesCat(int cat) {

        String clave = P_ParClaveValor.DIC_CATEGORIA;
        sCat=cat;
        savePreferences(clave, String.valueOf(cat));
    }


    public void savePreferencesSubCat(int subcat) {

        String clave = P_ParClaveValor.DIC_SUBCATEGORIA;
        sSubcat=subcat;
        savePreferences(clave, String.valueOf(subcat));
    }



    public void savePreferencesOrg(int org) {
        String clave = P_ParClaveValor.DIC_ORGANIZADOPOR;
        sPrefOrg = org;
        savePreferences(clave, org);
    }


    private String readPrerences(String cual) {
        String rawSql = "select * " //+ P_Dictionary.getColumnValue()
                + " from " + P_ParClaveValor.getTableName()
                + " where " + P_ParClaveValor.getColumnKey() + " = \"" + cual + "\"";

        SQLiteDatabase que = MySQLiteHelper.getDataBase();
        ParClaveValor parClaveValor = null;
        if (que != null) {
            Cursor cursor = que.rawQuery(rawSql, null);
            cursor.moveToFirst();
            parClaveValor = new ParClaveValor(cursor);
            cursor.close();
        }

        if (parClaveValor == null)
            return "";
        String retorno = parClaveValor.getValue();

        switch (cual){
            case P_ParClaveValor.DIC_ORGANIZADOPOR:
                sPrefOrg= Integer.parseInt(retorno);
                break;
            case P_ParClaveValor.DIC_SUBCATEGORIA:
                sSubcat = Integer.parseInt(retorno);
                break;
            case P_ParClaveValor.DIC_CATEGORIA:
                sCat=Integer.parseInt(retorno);
                break;
            case P_ParClaveValor.DIC_TITULO:
                sTitle = retorno;
                break;
            case P_ParClaveValor.DIC_GPSACTIVO:
                sIsGps = retorno.equals("true");
                break;
        }

        return retorno;
    }




    public int readPreferencesOrg()  {
        return Integer.parseInt(readPrerences(P_ParClaveValor.DIC_ORGANIZADOPOR));
    }


    public String readPrerencesTit() {
        return readPrerences(P_ParClaveValor.DIC_TITULO);
    }


    public int readPreferencesCat() {
        return Integer.parseInt(readPrerences(P_ParClaveValor.DIC_CATEGORIA));
    }


    public int readPreferencesSubCat() {
        return Integer.parseInt(readPrerences(P_ParClaveValor.DIC_SUBCATEGORIA));
    }

    private void updatePhotoGeoLocalizacion(PhotoInterfaz photoItemIntrf) {


        PhotoInterfaz.PhotoGeoLocalizacion geoLocalizacion = photoItemIntrf.getPhotoGeoLocalizacion();

        ContentValues contentValues = new ContentValues();

        contentValues.put(P_Photo.getColumnProvincia(), geoLocalizacion.getProvincia());
        contentValues.put(P_Photo.getColumnCodPostal(), geoLocalizacion.getCodpostal());
        contentValues.put(P_Photo.getColumnMunicipio(), geoLocalizacion.getMunicipio());
        contentValues.put(P_Photo.getColumnNombreTipoVia(), geoLocalizacion.getNombreTipoVia());
        long res;
        SQLiteDatabase que = MySQLiteHelper.getDataBase();
        res = que.update(P_Photo.getTableName(),
                contentValues,
                P_Photo.getColumnId() + "=" + photoItemIntrf.getID(),
                null);
        Log.v("SQLITEGEO", "REs " + res + " ID " + photoItemIntrf.getID());
    }



    public void updatePhoto(PhotoInterfaz photoItemIntrf) {

        long res = -1;
        ContentValues contentValues = new ContentValues();

        contentValues.put(P_Photo.getColumnCategoryId(), photoItemIntrf.getCategoria());
        contentValues.put(P_Photo.getColumnSubCategoryId(), photoItemIntrf.getSubCategoria());
        contentValues.put(P_Photo.getColumnTitle(), photoItemIntrf.getTitulo());
        SQLiteDatabase que = MySQLiteHelper.getDataBase();
        if (que != null) {
            res = que.update(P_Photo.getTableName(),
                    contentValues,
                    P_Photo.getColumnId() + "=" + photoItemIntrf.getID(),
                    null);
            Log.v("SQLITEUPDATE", "REs " + res + " ID " + photoItemIntrf.getID());
        }
    }


    public void deletePhoto(PhotoInterfaz photoItemIntrf) {
        long res = -1;
        SQLiteDatabase que = MySQLiteHelper.getDataBase();
        if (que != null) {
            res = que.delete(P_Photo.getTableName(),
                    P_Photo.getColumnId() + "=" + photoItemIntrf.getID(),
                    null);

            File img = new File(photoItemIntrf.getImagenPath());
            boolean k = img.delete();

            Log.v("SQLITEDELETE", "REs " + res + " ID " + photoItemIntrf.getID() + " BBB " + k);
        }
    }


    public PhotoInterfaz putPhoto(String title, String fileName, int category, int subCategory, double lngtd, double lttd) {


        long res = -1;
        PhotoInterfaz uno = null;


        ContentValues values = new ContentValues();

        values.put(P_Photo.getColumnTitle(), title);
        values.put(P_Photo.getColumnUriSite(), fileName);
        values.put(P_Photo.getColumnCategoryId(), category);
        values.put(P_Photo.getColumnSubCategoryId(), subCategory);
        values.put(P_Photo.getColumnLat(), lttd);
        values.put(P_Photo.getColumnLong(), lngtd);

        SQLiteDatabase que = MySQLiteHelper.getDataBase();

        if (que != null) {
            res = que.insert(P_Photo.getTableName(), null, values);

            Cursor cursor = que.query(P_Photo.getTableName(),
                    P_Photo.getTableCol(), P_Photo.getColumnId() + " = " + res, null,
                    null, null, null);
            cursor.moveToFirst();
            uno = new PhotoTags(cursor);

            cursor.close();
            getZonaGeo(uno);
        }



        return uno;
    }


    public PhotoInterfaz putPhoto(String fileName) {
        return putPhoto(sTitle,fileName,sCat,sSubcat, GpsLocalication.getLongitud(),GpsLocalication.getLatitud());
    }


    public List<PhotoInterfaz> getListPhoto(int tipo, int subTipo) {

        List<PhotoInterfaz> retorno = new LinkedList<PhotoInterfaz>();

        String whereTipo = null;
        String whereSubtipo = null;
        String where = null;
        String order = null;

        if (tipo != -1)
            whereTipo = P_Photo.getColumnCategoryId() + "= \"" + tipo + "\"";
        if (subTipo != -1) {
            whereSubtipo = P_Photo.getColumnSubCategoryId() + "= \"" + subTipo + "\"";
        }

        if (whereTipo != null) {
            if (whereSubtipo != null) {
                where = whereTipo + " and " + whereSubtipo;
                order = ", " + P_Photo.getColumnCategoryId() + ", " + P_Photo.getColumnSubCategoryId();
            } else {
                where = whereTipo;
                order = ", " + P_Photo.getColumnCategoryId();
            }
        } else if (whereSubtipo != null) {
            where = whereSubtipo;
            order = ", " + P_Photo.getColumnSubCategoryId();
        } else {
            where = "";
            order = "";
        }


        String notRawSql = null;
        String rawSql;
        if (where.equals("")) {
            rawSql = "select * from " + P_Photo.getTableName() + " order by " + P_Photo.getColumnCreateAt()  + order;
            notRawSql = null;
            Log.v("OA", ">>>>>>" + rawSql + "<<<<<<<<<<<<");

        } else {
            rawSql = "select * from " + P_Photo.getTableName() + " where " + where + " order by " + P_Photo.getColumnCreateAt() + " DESC" + order;
            notRawSql = "select * from " + P_Photo.getTableName() + " where not (" + where + ") order by " + P_Photo.getColumnCreateAt() + " DESC" + order;
            Log.v("OA", ">>>>>>" + rawSql + "<<<<<<<<<<<<");
            Log.v("OA", ">>>>>>" + notRawSql + "<<<<<<<<<<<<");
        }


        SQLiteDatabase que = MySQLiteHelper.getDataBase();
        if (que != null) {
            Cursor cursor;
            int files;

            if (notRawSql != null) {
                cursor = que.rawQuery(notRawSql, null);

                files = cursor.getCount();
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    retorno.add(new PhotoTags(cursor));
                    Log.v("XXXXX", "VA otras mas " + files);
                }
            }
            cursor = que.rawQuery(rawSql, null);
            files = cursor.getCount();
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                retorno.add(new PhotoTags(cursor));
                Log.v("XXXXX", "VA" + files);
            }

        }
        return retorno;
    }


    public String getPrerencesTit() {
        return sTitle;
    }


    public int getPreferencesCat() {
        return sCat;
    }


    public int getPreferencesSubCat() {
        return sSubcat;
    }


    public int getPreferencesOrg() {
        return sPrefOrg;
    }

    public boolean isGps() {
        return sIsGps;
    }

}
