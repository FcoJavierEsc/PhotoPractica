package com.utad.photopractica.modelo.sqlite3;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;


public class MySQLiteHelper extends SQLiteOpenHelper {

    private static MySQLiteHelper sInstance = null;

    private static SQLiteDatabase mDataBase = null;


    private static final String DATABASE_NAME = "phototags.db";
    private static final int DATABASE_VERSION = 2;
    private static Esquema mEsquema = null;


    public static boolean getInstance(Context context) {
        Log.v("FFFFF", "INTENTO" + (context != null ? " BIEN Context" : "MAL Context"));
        if (sInstance == null) {
            Log.v("FFFFF", "INTENTO 3");
            synchronized (MySQLiteHelper.class) {
                Log.v("FFFFF", "INTENTO 5");
                if (sInstance == null) {
                    sInstance = new MySQLiteHelper(context);
                    mDataBase = sInstance.getWritableDatabase();
                    if (mDataBase != null) {
                        Log.v("NOMBRE", "BASE DE DATOS" + mDataBase.getPath());
                    }
                }
            }
        }
        return sInstance != null;
    }

    public static SQLiteDatabase getDataBase() {
        return mDataBase;
    }

    private MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mEsquema = new Esquema();
        Log.v("MySQLiteHelper", "seguimos " + DATABASE_NAME);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("Oncreate", "seguimos");

        for (String uno : mEsquema.CreateAllTables()) {
            db.execSQL(uno);
        }


        LinkedList<String> tablas = new LinkedList<>();
        LinkedList<ContentValues> valores = new LinkedList<>();

        mEsquema.ValoresIniciales(valores, tablas);

        String tabla;
        ContentValues valor;
        long insertId;
        while (valores.size() != 0) {
            valor = valores.pop();
            tabla = tablas.pop();
            Log.v("INSERTAR", "VALORES INICIALES" + tabla + "<->" + valor.toString() + "<=>");
            insertId = db.insert(tabla, null, valor);
            Log.v("INSERTAR", "<=>" + insertId);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        for (String uno : mEsquema.upgradeVersion(oldVersion,newVersion)) {
            db.execSQL(uno);
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}