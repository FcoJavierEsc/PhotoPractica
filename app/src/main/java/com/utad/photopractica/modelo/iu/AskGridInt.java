package com.utad.photopractica.modelo.iu;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.utad.photopractica.Comun;
import com.utad.photopractica.R;


public class AskGridInt {

    private Application mApplication;
    private int eleccion = -1;
    private View mView = null;
    private Grid_Adapter_INT mGrid_adapter_int =null;

    public AskGridInt(Application application, View view, Grid_Adapter_INT grid_adapter_int) {

        setView(view);
        setGrid_adapter_int(grid_adapter_int);
        GridView gridview = (GridView) getView().findViewById(R.id.griview_INT);

        setApplication(application);
        gridview.setAdapter(getGrid_adapter_int());

        gridview.setVisibility(View.VISIBLE);
        gridview.setOnItemClickListener(new EscuchaGrid());
    }

    private class EscuchaGrid implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            eleccion = position;

            parent.setVisibility(View.GONE);
            ImageView imageView = (ImageView) getView().findViewById(R.id.image_INT);

            if (imageView == null){
                Log.v("OTRO","MAL Muy mal");
            }else {
                imageView.setImageBitmap(Comun.assetsImage(mApplication, Comun.getIcon(getGrid_adapter_int().getPathIcon(), position)));
                imageView.setVisibility(View.VISIBLE);
            }

        }
    }


    private Grid_Adapter_INT getGrid_adapter_int() {
        return mGrid_adapter_int;
    }

    private void setGrid_adapter_int(Grid_Adapter_INT grid_adapter_int) {
        mGrid_adapter_int = grid_adapter_int;
    }

    private View getView() {
        return mView;
    }

    private void setView(View view) {
        mView = view;
    }

    public int getEleccion() {
        return eleccion;
    }


    private void setApplication(Application application) {
        this.mApplication = application;
    }
}
