package com.utad.photopractica.modelo;

import android.database.Cursor;


public class ParClaveValor {

    private String mKey;
    private String mValue;


    public ParClaveValor(Cursor cursor) {
        int cols = cursor.getColumnCount();
        String[] colNames = cursor.getColumnNames();

        for (int i = 0; i < cols; i++) {
            String columna = colNames[i];
            if (columna.equals(P_ParClaveValor.getColumnKey())) {
                setKey(cursor.getString(i));
            } else if (columna.equals(P_ParClaveValor.getColumnValue())) {
                setValue(cursor.getString(i));
            }
        }
    }



    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String mValue) {
        this.mValue = mValue;
    }

}
