package com.utad.photopractica;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Comun {

    public final static String CATEGORY = "Categoria";
    public static final int MAXCATEGORIA = 27;

    public static final String SUBCATEGORY = "Subcategoria";
    public static final int MAXSUBCATEGORIA = 19;

    public static final String TEXTO = "Texto";




    public static void showRAM(String mensaje) {
        long freeSize = 0L;
        long totalSize = 0L;
        //long usedSize = -1L;
        try {
            Runtime info = Runtime.getRuntime();
            freeSize = info.freeMemory();
            totalSize = info.totalMemory();
            //usedSize = totalSize - freeSize;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("MEM: ", mensaje + "(" + totalSize + ", " + freeSize + ")");
    }


    public static int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.v("RotateImage", "Exif orientation: " + orientation);
            Log.v("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }


    public static Bitmap assetsImage(Application sAplication, String desc) {
        Bitmap retorno = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        Resources resources = sAplication.getResources();
        //      showRAM("ANTES");
        if (resources != null) {
            try {
                if (desc.startsWith("file:/")) {
                    URLConnection conn = new URL(desc).openConnection();
                    conn.connect();
                    inputStream = conn.getInputStream();
                    bufferedInputStream = new BufferedInputStream(inputStream);
                    retorno = BitmapFactory.decodeStream(bufferedInputStream);
                } else {
                    AssetManager assetManager = sAplication.getAssets();
                    inputStream = assetManager.open(desc);
                    retorno = BitmapFactory.decodeStream(inputStream);
                }
                //Log.v("CUAL", "ocupa " + retorno.getAllocationByteCount() + " " + retorno.getHeight() + " " + retorno.getWidth() + " ");
            } catch (Exception e) {
                Log.v("ALGO NP", "esta es " + desc);
                e.printStackTrace();
            } finally {
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //  showRAM("DESPUES");
        //Log.v("HW", desc + " H[" + retorno.getHeight() + "] W[" + retorno.getWidth() + "]");
        return retorno;
    }

    public static String getIcon(String catSub, int cual) {
        String retorno= String.format("%s/%03d.png", catSub, cual);
        return retorno;
    }

    public static String[] getOptions(){

        String retorno[]={"Ver en mapa","Ver Foto","Comp Facebook","Comp twiter"};
        return retorno;
    }
}
