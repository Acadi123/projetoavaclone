package avaclone.ufrpeeng.com.avaclone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    EditText userName, userSenha;

    String parametros = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.LoginTxtInput);
        userSenha = findViewById(R.id.PassTxtInput);

        final Button button = findViewById(R.id.BtnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                enviar_ava();

            }
        });
    }

    public void enviar_ava() {


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            String username = userName.getText().toString();
            String password = userSenha.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio.", Toast.LENGTH_LONG).show();
            } else {
                String url = "http://ava.ufrpe.br/login/token.php";

                parametros = "username=" + username + "&password=" + password + "&service=" + "moodle_mobile_app";

                new SolocitaDados().execute(url);

            }

        } else {
            Toast.makeText(getApplicationContext(), "Nenhuma conexao foi detectada.", Toast.LENGTH_LONG).show();
        }

    }


    private class SolocitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros);
        }

        protected void onPostExecute(String resultado) {
            if (resultado != null) {
                Toast.makeText(getApplicationContext(), "Logado com sucesso.", Toast.LENGTH_SHORT).show();
                Intent it = new Intent(LoginActivity.this, DiscipActivity.class);
                it.putExtra("token",resultado);
                startActivity(it);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Login Invalido.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static class Conexao {

        public static String postDados(String urlUsuario, String parametrosUsuario) {
            OkHttpClient client = new OkHttpClient();

            Request.Builder builder1 = new Request.Builder();

            builder1.url(urlUsuario);

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

            RequestBody body1 = RequestBody.create(mediaType, parametrosUsuario);
            builder1.post(body1);

            Request request1 = builder1.build();

            try {
                Response response = client.newCall(request1).execute();
                String result = response.body().string();
                JSONObject jsonobj = new JSONObject(result);
                String token = jsonobj.getString("token");
                return token;
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                return null;
            }
        }
    }
}


