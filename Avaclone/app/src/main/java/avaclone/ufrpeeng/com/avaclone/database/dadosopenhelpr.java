package avaclone.ufrpeeng.com.avaclone.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dadosopenhelpr extends SQLiteOpenHelper {


    public dadosopenhelpr(Context context) {
        super(context, "dados", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL( ScriptDLL.getCreateTableCliente() );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
