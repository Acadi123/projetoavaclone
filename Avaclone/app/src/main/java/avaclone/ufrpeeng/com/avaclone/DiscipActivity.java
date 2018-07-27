package avaclone.ufrpeeng.com.avaclone;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiscipActivity extends AppCompatActivity {

    String parametros1 = "";
    String parametros2 = "";
    String userid = "";
    String token;

    String url = "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json";

    ListView listdiscW;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listdiscW = findViewById(R.id.listdisc);

        Toast erro = Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_LONG);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.disciplinas:
                Toast actatual = Toast.makeText(DiscipActivity.this,"Você já esta nessa pagina",Toast.LENGTH_SHORT);
                actatual.show();
                return true;

            case R.id.perfil:
                Intent it = new Intent(DiscipActivity.this, CadastroActivity.class);
                it.putExtra("token",token);
                startActivity(it);
                return true;

            case R.id.sobresist:
                //
                return true;
            case R.id.grade:
                //
                return true;
            case R.id.logout:
                //
                return true;

        }
        return super.onOptionsItemSelected(item);
    }






    public class SolocitaDados1 extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            return Conexao1.postDados(urls[0], parametros1);
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


    public class SolocitaDados2 extends AsyncTask<String, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(String... urls) {
            return Conexao2.postDados(urls[0], parametros2);
        }

        protected void onPostExecute(JSONArray resultado) {
            Toast erro = Toast.makeText(getApplicationContext(), "Algo deu errado.", Toast.LENGTH_LONG);

            if (resultado != null) {
                try {
                    ArrayList<String> listdisc = new ArrayList<>();

                    for (int i = 0; i < resultado.length(); i++){
                        JSONObject row = resultado.getJSONObject(i);
                        String nomedisc = row.getString("fullname");
                        if (nomedisc.startsWith("2018.1")){
                            listdisc.add(nomedisc);

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(DiscipActivity.this, android.R.layout.simple_list_item_2, listdisc);
                            listdiscW.setAdapter(arrayAdapter);

                        }
                    }
                    Toast sucesso = Toast.makeText(getApplicationContext(), "Disciplinas carregadas", Toast.LENGTH_SHORT);
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

    public static class Conexao1 {

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

        public static JSONArray postDados(String urlUsuario, String parametrosUsuario) {
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

                JSONArray jsona1 = new JSONArray(result);

                JSONObject jsonobj;
                jsonobj = jsona1.getJSONObject(0);

                JSONArray jsona2 = jsonobj.getJSONArray("enrolledcourses");

                return jsona2;

            } catch (IOException e) {
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
