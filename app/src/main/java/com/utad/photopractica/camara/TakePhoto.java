package com.utad.photopractica.camara;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TakePhoto {
    public static final int REQUEST_TAKE_PHOTO = 0xF9;
    private String mCurrentPhotoPath = null;

    public String getCurrentPhotoPath() {
        return mCurrentPhotoPath;
    }

    public Intent dispatchTakePictureIntent(Context context) {

        Intent intentTakePict = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intentTakePict.resolveActivity(context.getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

                Log.e("ERROR", "Fichero con problemas " + mCurrentPhotoPath + ex.getMessage());
            }

            if (photoFile != null) {
                intentTakePict.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                return intentTakePict;

            }
        }
        return null;
    }

/*
    public boolean onResult(Context context,int request, int resultCode, Intent intent) {

        if (intent==null){
            Log.v("VISIBLE","EL INTENT ES NULL");
        }

        return (request == REQUEST_TAKE_PHOTO) ;
    }
*/
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".png",
                storageDir
        );


        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }


    public static boolean  checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
    }
}
