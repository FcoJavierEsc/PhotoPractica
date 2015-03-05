package com.utad.photopractica;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.utad.photopractica.camara.TakePhoto;
import com.utad.photopractica.modelo.scope.ScopeApp;
import com.utad.photopractica.modelo.sqlite3.SqlitePersist;
import com.utad.photopractica.recycler_view.UsoRecyclerView;


public class PhotoPractica extends Activity implements View.OnLongClickListener, ActionMode.Callback {
    private UsoRecyclerView mUsoRecyclerView = null;
    private ActionMode mActionMode;
    private TakePhoto mTakePhoto;
    private SqlitePersist mSqlitePersist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        if (mUsoRecyclerView == null) {
            mUsoRecyclerView = new UsoRecyclerView(this, this, this, new UsoRecyclerView.UsoRecyclerViewListener() {
                @Override
                public void startIntent(Intent intent) {
                    startActivityForResult(intent, PhotoActivity.REQUEST_PHOTO);
                }
            });
            mUsoRecyclerView.activa();
        }

        mSqlitePersist = new SqlitePersist().getServiceSqlite();

        if (mTakePhoto == null) {
            if (mTakePhoto.checkCameraHardware(this))
                mTakePhoto = new TakePhoto();
        }

        ImageButton imgPhoto = (ImageButton) findViewById(R.id.action_Photo);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionMode == null) {
                    Intent intent = mTakePhoto.dispatchTakePictureIntent(PhotoPractica.this);
                    if (intent != null) {
                        startActivityForResult(intent, TakePhoto.REQUEST_TAKE_PHOTO);
                    } else {
                        Log.v("FOTO", "Sin poder arrancar camara o escribir en fichero");

                    }
                    Log.v("PULSO", "tomar foto");
                }
            }
        });

        ImageButton imgSettings = (ImageButton) findViewById(R.id.action_settings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("PULSO", "settings");
                if (mActionMode != null) {
                    Intent intent = new Intent(PhotoPractica.this, SettingListValues.class);
                    if (intent != null) {
                        startActivityForResult(intent, SettingListValues.RESULT_SETTINGS);
                    }
                } else {
                    Intent intent = new Intent(PhotoPractica.this, GeneralSetting.class);
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int request, int resultCode, Intent intent) {

        super.onActivityResult(request, resultCode, intent); // hasta lollipop no necesario pero recomendable

        switch (request) {
            case TakePhoto.REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Log.v("ActResult", "TOMADA " + mTakePhoto.getCurrentPhotoPath());
                    ModelImplement modelImplement = new ModelImplement(mSqlitePersist.putPhoto(mTakePhoto.getCurrentPhotoPath()));
                    mUsoRecyclerView.addModel(modelImplement);
                }
                break;

            case SettingListValues.RESULT_SETTINGS:
                if (resultCode == RESULT_OK) {

                    String mititulo;
                    int micat;
                    int misubcat;

                    Bundle respuesta = intent.getExtras();

                    mititulo = respuesta.getString(SettingListValues.RESP_TITULO);
                    micat = respuesta.getInt(SettingListValues.RESP_CATALOGO);
                    misubcat = respuesta.getInt(SettingListValues.RESP_SUBCATALOGO);

                    mUsoRecyclerView.masivoUpdate(mititulo, micat, misubcat);

                }
                break;

            case PhotoActivity.REQUEST_PHOTO:

                if (resultCode == RESULT_OK) {
                    Bundle respuesta = intent.getExtras();
                    switch (respuesta.getInt(PhotoActivity.RETORNO)) {
                        case PhotoActivity.UPDATE:
                            ScopeApp.updatePhoto(ScopeApp.getActCardBuff());
                            mUsoRecyclerView.posConCambios(ScopeApp.getTransfer());
                            Log.v("ActResult", "pide update");
                            break;
                        case PhotoActivity.DELETE:
                            ScopeApp.deletePhoto(ScopeApp.getActCardBuff());
                            mUsoRecyclerView.posSeBorra(ScopeApp.getTransfer());
                            Log.v("ActResult", "pide delete");
                            break;
                        case PhotoActivity.CANCEL:
                            Log.v("ActResult", "pide cancel");
                            break;
                    }
                }

                Log.v("ActResult", "Intent " + intent == null ? " SIN INTENT" : "HAY INTENT");
                break;
        }
    }


    // Long click listener


    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        // MenuInflater inflater = mode.getMenuInflater();
        // inflater.inflate(R.menu.menu_masivo, menu);
        mUsoRecyclerView.modoSeleccion(true);

        Log.v("ALGO", "ESOES 2*****************************");
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        Log.v("ALGO", "ESOES 2");
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Log.v("ALGO", "ESOES 3");/*
        switch (item.getItemId()) {
            case R.id.action_texto:
                Toast.makeText(this, "TEXTO ", Toast.LENGTH_SHORT).show();

                mode.finish(); // Action picked, so close the CAB
                return true;
            case R.id.action_cat:
                Toast.makeText(this, "Categoria ", Toast.LENGTH_SHORT).show();

                mode.finish(); // Action picked, so close the CAB
                return true;
            case R.id.action_subcat:
                Toast.makeText(this, "subcategoria", Toast.LENGTH_SHORT).show();

                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }*/
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        Log.v("ALGO", "ESOES 4");
        mUsoRecyclerView.modoSeleccion(false);
        mActionMode = null;

    }

    @Override
    public boolean onLongClick(View v) {
        Log.v("ALGO", "ESOES 5");
        // if actionmode is null "not started"
        if (mActionMode != null) {
            return false;
        }

        // Start the CAB
        //TODO: no se cual es el que ha recibido el longclick

        mActionMode = this.startActionMode(this);
        mActionMode.setTitle("Selecciona");

        mUsoRecyclerView.modoSeleccion(true);

        return true;

    }

    //hasta aqui
}