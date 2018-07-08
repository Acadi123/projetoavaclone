package avaclone.ufrpeeng.com.avaclone;

import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText LoginTxtInput;
    private EditText PassTxtInput;
    private Button BtnLogin;
    private CheckBox RememberBox;
    private boolean LoggedIn = false;
    private SharedPreferences.Editor editor;

    private SQLiteDatabase conexao;

    private dadosopenhelper dadosopenhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       
    }
}
