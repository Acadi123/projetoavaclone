package avaclone.ufrpeeng.com.avaclone.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class BancoController {

    private SQLiteDatabase db;
    private dadosopenhelpr.CriaBanco banco;

    public BancoController(Context context){
        banco = new dadosopenhelpr.CriaBanco(context);
    }

    public String insereDado(String Fórum){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(dadosopenhelpr.CriaBanco.FÓRUM, Fórum);


        resultado = db.insert(dadosopenhelpr.CriaBanco.TABELA, null, valores);
        banco.close();//x

        if (resultado ==-1)
            return "Erro ao inserir registro";
        else
            return "Registro Inserido com sucesso”;

    }
}