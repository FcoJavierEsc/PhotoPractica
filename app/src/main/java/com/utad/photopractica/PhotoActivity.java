package com.utad.photopractica;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.utad.photopractica.modelo.iu.DialogAskInt;
import com.utad.photopractica.modelo.iu.DialogAskText;
import com.utad.photopractica.modelo.iu.Grid_Adapter_INT;
import com.utad.photopractica.modelo.scope.ScopeApp;
import com.utad.photopractica.photodrawer.DrawerLeft;


public class PhotoActivity extends Activity {
    public static final int REQUEST_PHOTO = 0xD9;

    public static final String RETORNO ="com.utad.photopractica.PhotoPractica";
    public static final int CANCEL = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    private TextView mtvTitulo;
    private ImageView mivFoto;
    private ImageView mivCat;
    private ImageView mivSub;
    private TextView mtvFecha;
    private TextView mtvGeoLocalizacion;

    private ModelImplement mPhotoInterfaz;

    private DrawerLeft mDrawerLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_photo);


        mDrawerLeft = new DrawerLeft(
                this,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                (RecyclerView) findViewById(R.id.left_drawer),
                getTitle(),
                getActionBar()
        );

        mPhotoInterfaz = ScopeApp.getByPosition();
        mtvTitulo = (TextView) findViewById(R.id.aps_photoTitle);
        mtvFecha = (TextView) findViewById(R.id.aps_fecha);
        mtvGeoLocalizacion = (TextView) findViewById(R.id.aps_geo_info);
        mivFoto = (ImageView) findViewById(R.id.aps_photoImage);
        mivCat = (ImageView) findViewById(R.id.aps_imgcategoria);
        mivSub = (ImageView) findViewById(R.id.aps_imgsubcategoria);

        mtvTitulo.setText(mPhotoInterfaz.getTitulo());
        mtvFecha.setText(mPhotoInterfaz.getFecha_Hora());
        mtvGeoLocalizacion.setText(mPhotoInterfaz.getPhotoGeoLocalizacion().getGeoInfo());
        mivFoto.setImageBitmap(mPhotoInterfaz.getImagePrincipal(getApplication()));
        mivCat.setImageBitmap(mPhotoInterfaz.getImageX1(getApplication()));
        mivSub.setImageBitmap(mPhotoInterfaz.getImageX2(getApplication()));
        mtvTitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strAnt = mPhotoInterfaz.getTitulo();
                DialogAskText dialogAskText;

                dialogAskText = new DialogAskText(strAnt);


                dialogAskText.HazText(PhotoActivity.this,
                        R.layout.dialog_title,
                        R.id.titulo_PT,
                        R.id.editTextDialogUserInput,
                        new DialogAskText.DialogAskTextListener() {
                            @Override
                            public void dialogFinish(DialogAskText dialogAskText) {
                                if (dialogAskText.hayCambio(strAnt)) {
                                    mPhotoInterfaz.setTitulo(dialogAskText.getEleccion());
                                    mtvTitulo.setText(mPhotoInterfaz.getTitulo());
                                }
                            }
                        }
                );
            }
        });

        mivCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.equals(mivCat)) {
                    final int anterior;
                    DialogAskInt dialogAskInt;
                    dialogAskInt = new DialogAskInt();
                    anterior = mPhotoInterfaz.getCategoria();
                    dialogAskInt.HazGrid(
                            getApplication(),
                            PhotoActivity.this,
                            new Grid_Adapter_INT(getApplication(), Comun.MAXCATEGORIA, Comun.CATEGORY),
                            R.layout.dialog_grid,
                            new DialogAskInt.DialogAskIntListener() {
                                @Override
                                public void dialogFinish(DialogAskInt dialogAskInt) {
                                    if (dialogAskInt.hayCambio(anterior)) {
                                        mPhotoInterfaz.setCategoria(dialogAskInt.getEleccion());
                                        mivCat.setImageBitmap(mPhotoInterfaz.getImageX1(getApplication()));
                                    }
                                }
                            });
                }
            }
        });
        mivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.equals(mivSub)) {
                    final int anterior;
                    DialogAskInt dialogAskInt;
                    dialogAskInt = new DialogAskInt();
                    anterior = mPhotoInterfaz.getSubCategoria();
                    dialogAskInt.HazGrid(
                            getApplication(),
                            PhotoActivity.this,
                            new Grid_Adapter_INT(getApplication(), Comun.MAXSUBCATEGORIA, Comun.SUBCATEGORY),
                            R.layout.dialog_grid,
                            new DialogAskInt.DialogAskIntListener() {
                                @Override
                                public void dialogFinish(DialogAskInt dialogAskInt) {
                                    if (dialogAskInt.hayCambio(anterior)) {
                                        mPhotoInterfaz.setSubCategoria(dialogAskInt.getEleccion());
                                        mivSub.setImageBitmap(mPhotoInterfaz.getImageX2(getApplication()));
                                    }
                                }
                            });
                }
            }
        });


        ImageView imageViewOk = (ImageView) findViewById(R.id.aps_0k);

        imageViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elFinal(UPDATE);

            }
        });

        ImageView imageViewDl = (ImageView) findViewById(R.id.aps_delete);
        imageViewDl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                elFinal(DELETE);

            }
        });
    }

    private void elFinal(int result) {
        ScopeApp.setActCardBuff(mPhotoInterfaz);
        Intent data = new Intent();
        data.putExtra(RETORNO, result);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void setTitle(CharSequence title) {
        Log.v("PONEMOS", "el tituo" + title);
        mDrawerLeft.setTitleAB(title);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.

        mDrawerLeft.syncState();//mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerLeft.onConfigurationChanged(newConfig);// mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLeft.isDrawerOpen();
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerLeft.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                String cual=mPhotoInterfaz.getAnotacion();
                if (!cual.equals("N/A")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://es.wikipedia.org/wiki/" +cual));


                    // catch event that there's no activity to handle intent
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                    }
                }else
                Toast.makeText(this, "Sin informacion geográfica", Toast.LENGTH_LONG).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}