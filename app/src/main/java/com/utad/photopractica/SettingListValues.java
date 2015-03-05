package com.utad.photopractica;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;


public class SettingListValues extends Activity {
    public static final int RESULT_SETTINGS = 245;
    public static final String RESP_TITULO = "com.utad.photopractica.PhotoActivity.TITULO";
    public static final String RESP_CATALOGO = "com.utad.photopractica.PhotoActivity.CATALOGO";
    public static final String RESP_SUBCATALOGO = "com.utad.photopractica.PhotoActivity.SUBCATALOGO";

    SettingInterfaz mSettingInterfaz;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_list_values);

        mSettingInterfaz = new SettingImplements();


        final EditText editText = (EditText) findViewById(R.id.card_titulo_ET);
        editText.setText("");


        final GridView gridviewCat = (GridView) findViewById(R.id.gridcategoria);
        final Grid_Adapter grid_adapterCat = new Grid_Adapter(getApplication(), Comun.MAXCATEGORIA, Comun.CATEGORY);
        gridviewCat.setAdapter(grid_adapterCat);
        final EscuchaGrid escuchaGridCat = new EscuchaGrid(R.id.card_imgexp1, grid_adapterCat);
        gridviewCat.setOnItemClickListener(escuchaGridCat);


        GridView gridviewSubCat = (GridView) findViewById(R.id.gridsubcategoria);
        Grid_Adapter grid_adapterSubCat = new Grid_Adapter(getApplication(), Comun.MAXSUBCATEGORIA, Comun.SUBCATEGORY);
        gridviewSubCat.setAdapter(grid_adapterSubCat);
        final EscuchaGrid escuchaGridSubCat = new EscuchaGrid(R.id.show_imgexp2, grid_adapterSubCat);
        gridviewSubCat.setOnItemClickListener(escuchaGridSubCat);

        putImage(R.id.card_imgexp1, mSettingInterfaz.getPreferencesCat(), grid_adapterCat);
        putImage(R.id.show_imgexp2, mSettingInterfaz.getPreferencesSubCat(), grid_adapterSubCat);

        ImageButton imgPhoto = (ImageButton) findViewById(R.id.action_accept);
        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(RESP_TITULO, editText.getText().toString());
                data.putExtra(RESP_CATALOGO, escuchaGridCat.getEleccion());
                data.putExtra(RESP_SUBCATALOGO, escuchaGridSubCat.getEleccion());
                setResult(RESULT_OK, data);
                finish();
            }
        });

        ImageButton imgSettings = (ImageButton) findViewById(R.id.action_cancel);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("PULSO", "settings");
                finish();

            }
        });

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