package avaclone.ufrpeeng.com.avaclone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CadastroActivity extends AppCompatActivity {

    String parametros1 = "";
    String parametros2 = "";
    String userid = "";
    TextView nometxt, cpftxt, cursotxt, emailtxt, cidadetxt, univtxt;

    String url = "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Toast erro = Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_LONG);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String token = extras.getString("token");

            parametros1 = "wsfunction=" + "core_webservice_get_site_info" + "&wstoken=" + token;
            parametros2 = "wsfunction=" + "core_user_get_users_by_id" + "&wstoken=" + token;

            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                new SolocitaDados1().execute(url);
            }
            else {
                erro.show();
            }
        }
         else {
            erro.show();
        }
    }


    public class SolocitaDados1 extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            return Conexao.postDados(urls[0], parametros1);
        }

        protected void onPostExecute(JSONObject resultado) {
            Toast erro = Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_LONG);
            if (resultado != null) try {
                userid = resultado.getString("userid");
                parametros2 = parametros2 + "&userids[0]=" + userid;
            } catch (JSONException e) {
                erro.show();
            }
            else {
                erro.show();
            }
            new SolocitaDados2().execute(url);
        }
    }


    public class SolocitaDados2 extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            return Conexao2.postDados(urls[0], parametros2);
        }

        protected void onPostExecute(JSONObject resultado) {
            Toast erro = Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_LONG);

            if (resultado != null) {
                try {

                    String nome = resultado.getString("fullname");
                    String cpf = resultado.getString("idnumber");
                    String curso = resultado.getString("department");
                    String email = resultado.getString("email");
                    String cidade = resultado.getString("city");
                    String univ = resultado.getString("institution");

                    nometxt = findViewById(R.id.NomeSet);
                    cpftxt = findViewById(R.id.CpfSet);
                    cursotxt = findViewById(R.id.CursoSet);
                    emailtxt = findViewById(R.id.EmailSet);
                    cidadetxt = findViewById(R.id.CidadeSet);
                    univtxt = findViewById(R.id.UnivSet);

                    nometxt.setText(nome);
                    cpftxt.setText(cpf);
                    cursotxt.setText(curso);
                    emailtxt.setText(email);
                    cidadetxt.setText(cidade);
                    univtxt.setText(univ);

                    Toast sucesso = Toast.makeText(getApplicationContext(), "Informações de perfil carregadas.", Toast.LENGTH_LONG);
                    sucesso.show();



                } catch (JSONException e) {
                    erro.show();
                }
            }
            else {
                erro.show();
                }
            }
        }

    public static class Conexao {

        public static JSONObject postDados(String urlUsuario, String parametrosUsuario) {
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
                return jsonobj;
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class Conexao2 {

        public static JSONObject postDados(String urlUsuario, String parametrosUsuario) {
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
                JSONArray jsonArray = new JSONArray(result); ///PROBLEMA AQUI JSONARRAY
                JSONObject jsonobj;
                jsonobj = jsonArray.getJSONObject(0);
                return jsonobj;
            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

