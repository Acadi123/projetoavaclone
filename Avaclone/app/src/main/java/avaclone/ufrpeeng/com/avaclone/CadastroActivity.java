package avaclone.ufrpeeng.com.avaclone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CadastroActivity extends AppCompatActivity {

    TextView nometxt,cpftxt,cursotxt,emailtxt,cidadetxt;

    String url = "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Toast erro = Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_LONG);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String token = extras.getString("token");

            String parametros1 = "wsfunction=" + "core_webservice_get_site_info" + "&wstoken=" + token;

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

                String resposta1 = Requisitor.recolhedados(url, parametros1);


                if (resposta1 != null) {

                    try {
                        JSONObject retorno1 = new JSONObject(resposta1);
                        String id = retorno1.getString("id");
                        String parametros2 = "wsfunction=" + "core_user_get_users_by_id" + "&wstoken=" + token + "&userids[0]=" + id;

                        String resposta2 = Requisitor.recolhedados(url, parametros2);

                        JSONObject retorno2 = new JSONObject(resposta2);
                        String Nome = retorno2.getString("fullname");
                        String CPF = retorno2.getString("idnumber");
                        String Curso = retorno2.getString("department");
                        String Email = retorno2.getString("email");
                        String Cidade = retorno2.getString("city");

                        nometxt.setText(Nome);
                        cpftxt.setText(CPF);
                        cursotxt.setText(Curso);
                        emailtxt.setText(Email);
                        cidadetxt.setText(Cidade);



                    } catch (JSONException e) {
                        erro.show();
                    }

                }
                else {
                    erro.show();
                }
            }
            else {
                erro.show();
            }
        }
        else {
            erro.show();
        }
    }

    public static class Requisitor{


        public static String recolhedados(String urlUsuario, String parametrosUsuario) {

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
                    return result;
                } catch (IOException e) {
                    return null;
                }
            }
    }
}

