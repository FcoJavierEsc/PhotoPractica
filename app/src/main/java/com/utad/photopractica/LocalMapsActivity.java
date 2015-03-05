package com.utad.photopractica;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.utad.photopractica.modelo.geo.GpsLocalication;
import com.utad.photopractica.modelo.scope.ScopeApp;

public class LocalMapsActivity extends Activity {
    GoogleMap map;

    private ModelImplement mModel;
    private LatLng mLatLngPhoto;
    private boolean markVisible = false;

    // ***********************************************************************
    //
    // ***********************************************************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_local_maps);

        int iRet = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        Log.v("MAPSMAP", "create");
        if (iRet != ConnectionResult.SUCCESS) {
            GooglePlayServicesUtil.getErrorDialog(iRet, this, 1);
        } else {


            mModel = ScopeApp.getByPosition(ScopeApp.getTransfer());

            mLatLngPhoto = new LatLng(mModel.getPhotoGeoLocalizacion().getLatitud(),
                    mModel.getPhotoGeoLocalizacion().getLongitud());

           /* mLatLngActual =  LatLng(GpsLocalication.getLatitud(),
                    GpsLocalication.getLongitud());
*/

            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

            if (map == null) {
                Toast.makeText(this, "Google Maps not available", Toast.LENGTH_LONG).show();
            } else {
                map.getUiSettings().setTiltGesturesEnabled(true);
                map.getUiSettings().setRotateGesturesEnabled(false);
                setPosition(mLatLngPhoto);
                putMarker();
            }
        }
    }


    void putMarker() {

        map.clear();
        map.addMarker(new MarkerOptions()
                .position(mLatLngPhoto)
                .title(mModel.getPhotoGeoLocalizacion().getMunicipio())
                .snippet(mModel.getPhotoGeoLocalizacion().getCodpostal() + " " + mModel.getPhotoGeoLocalizacion().getProvincia())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

        if (markVisible) {
            map.addMarker(new MarkerOptions()
                    .position(GpsLocalication.getLatLng())
                    .title("Está usted")
                    .snippet("Aquí")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        }


    }

    // ***********************************************************************
    //
    // ***********************************************************************

    @Override
    public void openOptionsMenu() {

        Log.v("MAPSMAP","MEnu  OPT");
        super.openOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.v("MAPSMAP","MEnu PREPARE");
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Crea el menú a partir del xml.
        Log.v("MAPSMAP","MEnu");
        getMenuInflater().inflate(R.menu.menu_local_map, menu);
        return true;
    }

    // ***********************************************************************
    //
    // ***********************************************************************


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_changetype:
                switch (map.getMapType()) {
                    case GoogleMap.MAP_TYPE_HYBRID:
                        map.setMapType(GoogleMap.MAP_TYPE_NONE);
                        item.setTitle("None->Normal");
                        break;
                    case GoogleMap.MAP_TYPE_NONE:
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        item.setTitle("Normal->Satelite");
                        break;
                    case GoogleMap.MAP_TYPE_NORMAL:
                        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        item.setTitle("Satelite->Terreno");
                        break;
                    case GoogleMap.MAP_TYPE_SATELLITE:
                        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        item.setTitle("Terreno->Hibrido");
                        break;
                    case GoogleMap.MAP_TYPE_TERRAIN:
                        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        item.setTitle("Hibrido->None");
                        break;
                    default:
                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        item.setTitle("SORPRESA");
                        break;
                }
                break;
            case R.id.menu_showphotolocation:
                setPosition(mLatLngPhoto);
                putMarker();
                break;

            case R.id.menu_showcurrentlocation:
                markVisible = true;
                LatLng latLng=GpsLocalication.getLatLng();
                Log.v("MAPS","vemos "+latLng.toString());
                setPosition(latLng);
                putMarker();
                //markVisible = !markVisible;
                break;

            case R.id.menu_lineconnecttwopoints:
                map.addPolyline(new PolylineOptions().add(GpsLocalication.getLatLng(), mLatLngPhoto).width(5).color(Color.RED));
                break;

            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }

    private void setPosition(LatLng latLngPhoto) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLngPhoto) // Centro del mapa
                .zoom(12)              // Nivel deZoom
                        //      .bearing(90)           // Orientación
                        //      .tilt(30)              // Inclinación
                .build();              // Crea el objeto
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

    }

}
