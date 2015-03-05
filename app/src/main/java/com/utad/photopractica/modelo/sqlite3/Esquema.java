package com.utad.photopractica.modelo.sqlite3;


import android.content.ContentValues;

import com.utad.photopractica.modelo.InterfazEsquema;
import com.utad.photopractica.modelo.P_ParClaveValor;
import com.utad.photopractica.modelo.P_Photo;

import java.util.LinkedList;
import java.util.List;


public class Esquema implements InterfazEsquema {

    @Override
    public List<String> AllTables() {
        List<String> lista = new LinkedList<>();

        lista.add(P_ParClaveValor.getTableName());
        lista.add(P_Photo.getTableName());

        return lista;
    }

    @Override
    public List<String> CreateAllTables() {
        List<String> lista = new LinkedList<>();


        lista.add(P_ParClaveValor.getCreateTable());
        lista.add(P_Photo.getCreateTable());

        return lista;
    }

    @Override
    public void ValoresIniciales(LinkedList<ContentValues> retorno, LinkedList<String> salida) {

        ContentValues values ;
        String valKey[] = {
                P_ParClaveValor.DIC_TITULO,
                P_ParClaveValor.DIC_CATEGORIA,
                P_ParClaveValor.DIC_SUBCATEGORIA,
                P_ParClaveValor.DIC_ORGANIZADOPOR,
                P_ParClaveValor.DIC_GPSACTIVO,
        };
        String valValue[]={
                "Titulo por defecto. Modifícame",
                "0",
                "0",
                String.valueOf(P_ParClaveValor.VAL_ORG_CRONO),
                String.valueOf(P_ParClaveValor.VAL_GPS_ACTIVO)
        };

        for (int valor = 0; valor < valKey.length; valor++) {
            salida.add(P_ParClaveValor.getTableName());
            values = new ContentValues();
            values.put(P_ParClaveValor.getColumnKey(), valKey[valor]);
            values.put(P_ParClaveValor.getColumnValue(),valValue[valor]);
            retorno.add(values);
        }
    }

    private List<String> upgrade2(){
        List<String> lista = new LinkedList<>();
// de momento sólo hay un par oldversion to newversion

        String sqlInsert= "INSERT INTO "+ P_ParClaveValor.getTableName()+
                " ( "+ P_ParClaveValor.getColumnKey()+ ","+ P_ParClaveValor.getColumnValue()+")"+
                " VALUES ( '"+ P_ParClaveValor.DIC_ORGANIZADOPOR+"','"+ P_ParClaveValor.VAL_ORG_CRONO+"');";
        lista.add(sqlInsert);

        return lista;
    }

    @Override
    public List<String> upgradeVersion(int oldVersion, int newVersion) {
        List<String> lista = new LinkedList<>();
// de momento sólo hay un par oldversion to newversion

        if (newVersion>=2)
            lista=upgrade2();

        return lista;
    }
}
