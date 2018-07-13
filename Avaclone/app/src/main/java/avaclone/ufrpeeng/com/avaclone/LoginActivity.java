package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button buttonlogin = findViewById(R.id.BtnLogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Request request;

                EditText LoginTxt, PassTxt;
                String Login, Senha;

                LoginTxt = findViewById(R.id.LoginTxtInput);
                PassTxt = findViewById(R.id.PassTxtInput);

                OkHttpClient okHttpClient = new OkHttpClient();

                Login = LoginTxt.getText().toString();
                Senha = PassTxt.getText().toString();

                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://ava.ufrpe.br/login/token.php").newBuilder();
                urlBuilder.addQueryParameter("username", Login);
                urlBuilder.addQueryParameter("password", Senha);
                urlBuilder.addQueryParameter("service", "moodle_mobile_app");
                String url = urlBuilder.build().toString();

                request = new Request.Builder().url(url).build();

                okHttpClient.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        Object returnRequest;
                        //returnRequest.retrieveData("");
                        //TOAST COM PROBLEMA DE CONEXAO E REINICIA A PAGINA

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        try {
                            JSONObject jsonObj = new JSONObject(result);
                            String token = jsonObj.getString("token");
                            Object returnRequest;
                            //returnRequest.retrieveData(token);
                            //INTENT PARA A PROXIMA PAGINA AQUI E REINICIA A PAGINA E GUARDA O TOKEN
                            //PARA PROXIMA PAGINA
                        } catch (Exception e) {
                            Object returnRequest;
                            //returnRequest.retrieveData(null);
                            //TOAST AQUI
                        }
                    }
                });
            }

            public void Trocarpagina() {
                Intent it = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(it);
            }
        });
    }
}
//                    Toast toast = Toast.makeText(getApplicationContext(), "Usuario ou senha errado(s)", Toast.LENGTH_SHORT);
//                    toast.show();