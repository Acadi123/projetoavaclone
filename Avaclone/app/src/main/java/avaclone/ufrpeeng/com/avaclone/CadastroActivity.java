package avaclone.ufrpeeng.com.avaclone;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
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
    String token;

    String url = "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Toast erro = Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_LONG);

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

        //menu nav
        Drawable menuicon = ContextCompat.getDrawable(CadastroActivity.this,R.drawable.ic_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(CadastroActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(CadastroActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);
                                startActivity(disc);
                                return true;

                            case R.id.perfil:
                                Toast actatual = Toast.makeText(CadastroActivity.this, "Você já esta nessa pagina", Toast.LENGTH_SHORT);
                                actatual.show();
                                return true;

                            case R.id.sobresist:
                                Intent sobresist = new Intent(CadastroActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                startActivity(sobresist);
                                return true;


                            case R.id.grade:
                                Intent grade = new Intent(CadastroActivity.this, GradeActivity.class);
                                grade.putExtra("token", token);
                                startActivity(grade);
                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(CadastroActivity.this, LoginActivity.class);
                                startActivity(logout);
                                finish();
                                return true;


                            default:
                                return false;
                        }
                    }
                });
                menu.show();
            }
        });
        //

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reload_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.recarregar:
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    new SolocitaDados1().execute(url);
                }
                else {
                    Toast erro = Toast.makeText(getApplicationContext(), "Verifique sua conexão com a internet.", Toast.LENGTH_LONG);
                    erro.show();
                }

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
            Toast erro = Toast.makeText(getApplicationContext(), "Erro ao coletar informações do servidor.", Toast.LENGTH_LONG);
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
            Toast erro = Toast.makeText(getApplicationContext(), "Erro ao coletar informações do servidor.", Toast.LENGTH_LONG);


            if (resultado != null) {
                try {

                    String nome = trabnome(resultado.getString("fullname"));
                    String cpf = resultado.getString("idnumber");
                    String curso = trabtexto(resultado.getString("department"));
                    String email = resultado.getString("email");
                    String cidade = trabtexto(resultado.getString("city"));
                    String univ = trabtexto(resultado.getString("institution"));

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

                    Toast sucesso = Toast.makeText(getApplicationContext(), "Informações de perfil carregadas", Toast.LENGTH_SHORT);
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

    public String trabnome(String texto) {
        texto = texto.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder(texto);
        stringBuilder.setCharAt(0,Character.toUpperCase((texto.charAt(0))));
        for (int k = 0; k < texto.length(); k++) {
            if (Character.toString(texto.charAt(k)).equals(" ")) {
                stringBuilder.setCharAt(k+1,Character.toUpperCase((texto.charAt(k + 1))));
            }

        }
        return stringBuilder.toString();
    }
    public String trabtexto(String texto) {
        texto = texto.toLowerCase();
        StringBuilder stringBuilder = new StringBuilder(texto);
        stringBuilder.setCharAt(0,Character.toUpperCase((texto.charAt(0))));
        return stringBuilder.toString();
    }

}

