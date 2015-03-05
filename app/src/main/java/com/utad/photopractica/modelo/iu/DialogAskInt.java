package com.utad.photopractica.modelo.iu;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Pone en dialogo un grid en el que hay una serie de iconos numerados correlativamente
 * muestra la elección y pide si se acepta o no.
 */

public class DialogAskInt {

    int mEleccion;
    boolean mCambio = false;

    private DialogAskIntListener mDialogAskIntListener;

    public interface DialogAskIntListener {
        public void dialogFinish(DialogAskInt dialogAskInt);
    }

    public boolean hayCambio(int anterior) {
        return anterior != mEleccion;
    }

    public int getEleccion() {
        return mEleccion;
    }

    public void HazGrid(Application application,
                        Context context,
                        Grid_Adapter_INT grid_adapter_int,
                        int recurso,
                        DialogAskIntListener dialogAskIntListener) {

        mDialogAskIntListener = dialogAskIntListener;

        LayoutInflater mLayoutInflater;
        View promptView;
        AlertDialog.Builder alertDialogBuilder;
        AlertDialog alertDialog;
        final AskGridInt askGridInt;

        mLayoutInflater = LayoutInflater.from(context);
        promptView = mLayoutInflater.inflate(recurso, null);

        alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);

        askGridInt = new AskGridInt(application, promptView, grid_adapter_int);


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mEleccion = askGridInt.getEleccion();
                                mCambio = true;
                                mDialogAskIntListener.dialogFinish(DialogAskInt.this);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
