package com.utad.photopractica.modelo.scope;


import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.util.LongSparseArray;

import com.utad.photopractica.ModelImplement;
import com.utad.photopractica.modelo.P_ParClaveValor;
import com.utad.photopractica.modelo.PhotoInterfaz;
import com.utad.photopractica.modelo.geo.GpsLocalication;
import com.utad.photopractica.modelo.service.ServBBDD;
import com.utad.photopractica.modelo.sqlite3.SqlitePersist;
import com.utad.photopractica.ModelInterfaz;

import java.util.ArrayList;
import java.util.List;


public class ScopeApp extends Application {


    private static List<ModelImplement> sListPhoto = null; // lista de fotos
    private static LongSparseArray<ModelImplement> sPhotoMap = null; // mapa para encontrar las fotos

    private static int sTransfer = -1; // de la lista de fotos cual es la que estamos tratando

    private static volatile SqlitePersist sSqlitePersist = null; // interfaz con la persistencia servicio

    private static ModelImplement sModelImplement;

    @Override
    public void onCreate() {
        Log.v("SECUENCIA", "onCREATEAPP");
        super.onCreate();
        if (sListPhoto == null) {
            synchronized (ScopeApp.class) {
                if (sListPhoto == null) {
                    GpsLocalication.getInstance(getApplicationContext());
                    sListPhoto = new ArrayList<>();
                    sPhotoMap = new LongSparseArray<>();
                    Intent intent = new Intent(this, ServBBDD.class);
                    bindService(intent, sConnection, Context.BIND_AUTO_CREATE);
                }
            }
        }
        Log.v("SECUENCIA", "offCREATEAPP");
    }

    public ScopeApp getInstance() {
        return this;
    }


    public static void setTitulo(String titulo) {
        sSqlitePersist.savePreferencesTit(titulo);
    }

    public static void setCatDefault(int valor) {
        sSqlitePersist.savePreferencesCat(valor);
    }

    public static void setSubCatDefault(int valor) {
        sSqlitePersist.savePreferencesSubCat(valor);
    }

    public static int getCatDefault() {
        return sSqlitePersist.getPreferencesCat();
    }

    public static int getSubCatDefault() {
        return sSqlitePersist.getPreferencesSubCat();
    }


    public static void updatePhoto(PhotoInterfaz photo) {
        sSqlitePersist.updatePhoto(photo);
    }

    public static void deletePhoto(PhotoInterfaz photo) {
        removeItemFromList(getPositionById((int)photo.getID()));
        sSqlitePersist.deletePhoto(photo);
    }


    public static List<ModelImplement> getListPhoto() {
        return sListPhoto;
    }

    private static ModelImplement modelFromPhoto(PhotoInterfaz photo) {

        return new ModelImplement(photo);

    }

    public static void addPhotoToList(ModelImplement model, int position) {

        sListPhoto.add(position, model);
        sPhotoMap.put(model.getIndex(), model);

    }

    public static void addPhotoToList(PhotoInterfaz photo, int position) {
        ModelImplement model = modelFromPhoto(photo);

        sListPhoto.add(position, model);
        sPhotoMap.put(photo.getID(), model);

    }

    private static void removeItemFromList(int position) {

        sPhotoMap.remove(sListPhoto.get(position).getIndex());
        sListPhoto.remove(position);
    }

    private static void reorganizaCatSub(int org) {

        sListPhoto.clear();
        sPhotoMap.clear();

        for (PhotoInterfaz i : sSqlitePersist.getListPhoto(sSqlitePersist.getPreferencesCat(), sSqlitePersist.getPreferencesSubCat())) {
            addPhotoToList(i, 0);
        }
    }

    private static void reorganizaTemporal(int org) {

        sListPhoto.clear();
        sPhotoMap.clear();

        if (org != -1)
            sSqlitePersist.savePreferencesOrg(org);

        for (PhotoInterfaz i : sSqlitePersist.getListPhoto(-1, -1)) {
            addPhotoToList(i, 0);
        }
    }


    public static void reorganiza(int como) {
        switch (como) {
            case P_ParClaveValor.VAL_ORG_CAT_SUB:
                reorganizaCatSub(como);
                break;
            case P_ParClaveValor.VAL_ORG_COMENTARIO:
            case P_ParClaveValor.VAL_ORG_CRONO:
            default:
                reorganizaTemporal(como);
                break;
        }
    }

    public static void removeItemFromList() {
        removeItemFromList(sTransfer);
    }


    public static ModelImplement getPassed() {
        return getByPosition(sTransfer);
    }

    public static ModelImplement findById(int id) {
        return sPhotoMap.get(id);
    }


    public static ModelImplement getByPosition() {
        return getByPosition(sTransfer);
    }

    public static ModelImplement getByPosition(int position) {
        return sListPhoto.get(position);
    }

    public static int getPositionById(int byId) {
        return sPhotoMap.indexOfKey(byId);
    }

    public static int size() {
        return sListPhoto.size();
    }

    public static int getTransfer() {
        return sTransfer;
    }

    public static void logTransfer() {

        if (sTransfer < 0) return;

        ModelInterfaz ModelInterfaz = sListPhoto.get(sTransfer);

        Log.v("TRANSFER", "ES " + ModelInterfaz.toString());
    }

    public static void setTransfer(int position) {
        sTransfer = position;
    }


    private static ServiceConnection sConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            SqlitePersist binder = (SqlitePersist) service;
            sSqlitePersist = binder.getServiceSqlite();
            Log.v("SERVICE", "CONECTADOS");

            sSqlitePersist.readPrerencesTit();
            sSqlitePersist.readPreferencesCat();
            sSqlitePersist.readPreferencesSubCat();
            sSqlitePersist.readPreferencesOrg();


            reorganiza(sSqlitePersist.getPreferencesOrg());

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            sSqlitePersist = null;
        }
    };

    public static void setActCardBuff(PhotoInterfaz photoInterfaz) {
        sModelImplement = new ModelImplement(photoInterfaz);
    }

    public static PhotoInterfaz getActCardBuff() {
        return  sModelImplement;
    }
}