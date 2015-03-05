package com.utad.photopractica.modelo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.utad.photopractica.modelo.sqlite3.MySQLiteHelper;
import com.utad.photopractica.modelo.sqlite3.SqlitePersist;


public class ServBBDD extends Service {

    private final IBinder mBinder = new SqlitePersist();

    private  boolean mSqliteDataBase = false;


    @Override
    public IBinder onBind(Intent intent) {
        mSqliteDataBase =  MySQLiteHelper.getInstance(this);
        return mBinder;
    }

   }