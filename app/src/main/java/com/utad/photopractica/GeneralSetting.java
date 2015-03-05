package com.utad.photopractica;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;

import com.utad.photopractica.modelo.P_ParClaveValor;


public class GeneralSetting extends Activity {

    private boolean mGps;
    private String mEtiqueta;
    private int mImX1;
    private int mImX2;
    private int mOrden;


    private Switch mSwGps;
    private EditText mComentario;
    private EscuchaGrid mEscuchaGridCat;
    private EscuchaGrid mEscuchaGridSubCat;
    private int mActualOrden;

    SettingInterfaz mSettingInterfaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_general_setting);

        Log.v("EMMMMM", "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMmm");
        mSettingInterfaz = new SettingImplements();
        mOrden = mSettingInterfaz.getPreferencesOrg();
        mGps = mSettingInterfaz.isGps();
        mEtiqueta = mSettingInterfaz.getPrerencesTit();
        mImX1 = mSettingInterfaz.getPreferencesCat();
        mImX2 = mSettingInterfaz.getPreferencesSubCat();
        mOrden = mSettingInterfaz.getPreferencesOrg();

        int recurso = R.id.ags_ord_crono;
        switch (mOrden) {
            case 0:
                recurso = R.id.ags_ord_crono;
                break;
            case 1:
                recurso = R.id.ags_ord_titulo;
                break;
            case 2:
                recurso = R.id.ags_ord_cat_sub;
                break;
        }

        RadioButton radioButton = (RadioButton) findViewById(recurso);

        radioButton.setChecked(true);

        mSwGps = (Switch) findViewById(R.id.ags_gps);

        boolean kk = mSettingInterfaz.isGps();
        Log.v("ELGPS", "GPS ES" + kk);
        if (kk)
            Log.v("ELGPS", "VERDAD");
        else
            Log.v("ELGPS", "FALSO");

        mSwGps.setChecked(kk);


        mComentario = (EditText) findViewById(R.id.ags_comentario);
        mComentario.setText(mSettingInterfaz.getPrerencesTit());

        GridView gridviewCat = (GridView) findViewById(R.id.ags_categoria);
        Grid_Adapter grid_adapterCat = new Grid_Adapter(getApplication(), Comun.MAXCATEGORIA, Comun.CATEGORY);
        gridviewCat.setAdapter(grid_adapterCat);
        mEscuchaGridCat = new EscuchaGrid(R.id.card_imgexp1, grid_adapterCat);
        gridviewCat.setOnItemClickListener(mEscuchaGridCat);


        GridView gridviewSubCat = (GridView) findViewById(R.id.ags_subcategoria);
        Grid_Adapter grid_adapterSubCat = new Grid_Adapter(getApplication(), Comun.MAXSUBCATEGORIA, Comun.SUBCATEGORY);
        gridviewSubCat.setAdapter(grid_adapterSubCat);
        mEscuchaGridSubCat = new EscuchaGrid(R.id.show_imgexp2, grid_adapterSubCat);
        gridviewSubCat.setOnItemClickListener(mEscuchaGridSubCat);

        putImage(R.id.card_imgexp1, mSettingInterfaz.getPreferencesCat(), grid_adapterCat);
        putImage(R.id.show_imgexp2, mSettingInterfaz.getPreferencesSubCat(), grid_adapterSubCat);


        ImageButton imgAceptar = (ImageButton) findViewById(R.id.gs_aceptar);
        imgAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("PULSO", "settings");
                Aceptar();
            }

        });
        ImageButton imgCancelar = (ImageButton) findViewById(R.id.gs_cancelar);
        imgCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("PULSO", "settings");
                Cancelar();
            }

        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.ags_ord_crono:
                if (checked)
                    mActualOrden = P_ParClaveValor.VAL_ORG_CRONO;
                break;
            case R.id.ags_ord_titulo:
                if (checked)
                    mActualOrden = P_ParClaveValor.VAL_ORG_COMENTARIO;
                break;
            case R.id.ags_ord_cat_sub:
                if (checked)
                    mActualOrden = P_ParClaveValor.VAL_ORG_CAT_SUB;
                break;
        }
    }

    public void Aceptar() {
        Log.v("ACEPTO", "ACABO");

        if (mSwGps.isChecked() != mGps)
            mSettingInterfaz.savePreferencesGps(mSwGps.isChecked());
        if (!mEtiqueta.equals(mComentario.getText().toString()))
            mSettingInterfaz.savePreferencesTit(mComentario.getText().toString());
        if (mImX1 != mEscuchaGridCat.getEleccion())
            mSettingInterfaz.savePreferencesCat(mEscuchaGridCat.getEleccion());
        if (mImX2 != mEscuchaGridSubCat.getEleccion())
            mSettingInterfaz.savePreferencesSubCat(mEscuchaGridSubCat.getEleccion());
        if (mOrden != mActualOrden)
            mSettingInterfaz.savePreferencesOrg(mActualOrden);

        finish();
    }

    public void Cancelar() {
        Log.v("Cancelo", "ACABO");
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_general_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class EscuchaGrid implements AdapterView.OnItemClickListener {
        private int mEleccion;

        private int mRecurso;

        private Grid_Adapter mGrid_adapter;

        private EscuchaGrid(int recurso, Grid_Adapter grid_adapter) {
            mEleccion = -1;
            mRecurso = recurso;
            mGrid_adapter = grid_adapter;

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mEleccion = position;
            putImage(mRecurso, position, mGrid_adapter);
        }

        public int getEleccion() {
            return mEleccion;
        }

        public void setEleccion(int eleccion) {
            mEleccion = eleccion;
        }
    }

    private void putImage(int recurso, int position, Grid_Adapter grid_adapter) {

        ImageView imageView = (ImageView) findViewById(recurso);
        if (imageView == null) {
            Log.v("OTRO", "MAL Muy mal");
        } else {
            imageView.setImageBitmap(Comun.assetsImage(getApplication(), Comun.getIcon(grid_adapter.getPathIcon(), position)));

        }
    }
}
