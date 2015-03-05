package com.utad.photopractica.modelo;

import android.content.ContentValues;

import java.util.LinkedList;
import java.util.List;


public interface InterfazEsquema {


    public List<String> AllTables();
    public List<String> CreateAllTables();

    public  void ValoresIniciales(LinkedList<ContentValues> retorno, LinkedList<String> lista);
    public List<String>  upgradeVersion(int oldVersion, int newVersion);
}
