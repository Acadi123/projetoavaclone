package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.Scanner;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button buttonlogin = findViewById(R.id.BtnLogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Logar();
                }

                catch (Exception ex) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario ou senha errado(s)", Toast.LENGTH_SHORT);
                    toast.show();

                }
            }
        });
    }

    private  void  Logar() throws IOException, JSONException {

        EditText LoginTxt, PassTxt;
        String Login, Senha;

        LoginTxt = findViewById(R.id.LoginTxtInput);
        PassTxt = findViewById(R.id.PassTxtInput);
        Login = LoginTxt.getText().toString();
        Senha = PassTxt.getText().toString();
        String tokenout = "";

        JSONObject jsonAVA = new JSONObject();
        jsonAVA.put("username", Login);
        jsonAVA.put("password", Senha);
        jsonAVA.put("service", "moodle_mobile_app");

        URL url = new URL("http://ava.ufrpe.br/login/token.php");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.connect();

        PrintStream printStream = new PrintStream(conn.getOutputStream());
        printStream.println(jsonAVA);

        String jsonResposta = new Scanner(conn.getInputStream()).next();

        conn.disconnect();

        tokenout=jsonResposta;

        //IF O TOKEN FOR RECEBIDO -> Trocar a pagina()

        if (!tokenout.equals("")) Trocarpagina();
    }

    public void Trocarpagina(){
        Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(it);
    }
}
