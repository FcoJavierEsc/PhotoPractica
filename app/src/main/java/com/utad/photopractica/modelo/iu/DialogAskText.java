package com.utad.photopractica.modelo.iu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class DialogAskText {
    String mEleccion;

    String mTitulo;

    public DialogAskText(String titulo) {
        mTitulo = titulo;
    }

    private DialogAskTextListener mDialogAskTextListener;

    public interface DialogAskTextListener {
        public void dialogFinish(DialogAskText dialogAskText);
    }

    public boolean hayCambio (String anterior) {
        return !anterior.equals(mEleccion);
    }

    public String getEleccion(){
        return mEleccion;
    }

    public void HazText(Context context,
                        int recurso,
                        int rTextView,
                        int rEditText,
                        DialogAskTextListener dialogAskTextListener) {

        mDialogAskTextListener = dialogAskTextListener;

        LayoutInflater mLayoutInflater ;
        View promptView ;
        AlertDialog.Builder alertDialogBuilder ;
        AlertDialog alertDialog ;
        final  EditText userInput;
        TextView textView;

        mLayoutInflater = LayoutInflater.from(context);
        promptView = mLayoutInflater.inflate(recurso, null);

        alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(promptView);

        textView = (TextView) promptView.findViewById(rTextView);
        textView.setText(mTitulo);
        userInput = (EditText) promptView
                .findViewById(rEditText);


        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                mEleccion = userInput.getText().toString();
                                mDialogAskTextListener.dialogFinish(DialogAskText.this);
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
