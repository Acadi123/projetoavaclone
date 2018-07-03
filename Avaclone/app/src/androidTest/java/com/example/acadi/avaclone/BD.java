package com.example.acadi.avaclone;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper {
    private static final String NOME_DO_BD = "Avaclone";
    private static final int VERSAO = 1;


    public BD(Context ctx) {
        super(ctx, NOME_DO_BD, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("create table usuario(_id integer primary key  autoincrement, nome text not null, senha text not null, email text not null); ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int arg1, int arg2) {
        bd.execSQL("drop table usuario;");
        onCreate(bd);
    }
}