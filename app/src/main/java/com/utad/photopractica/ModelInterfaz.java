package com.utad.photopractica;

import android.app.Application;
import android.graphics.Bitmap;


/**
 * Lo que el interfaz de usuario necesita saber del modelo de datos
 */
public interface ModelInterfaz {

//Comun.assetsImage(sAplication, model.getImageX2()

    public Bitmap getImagePrincipal(Application application);
    public Bitmap getImageX1(Application application);
    public Bitmap getImageX2(Application application);
    public String getEtiqueta();
    public String getAnotacion();
    public String getSubAnotacion();
    public long getIndex();


}
