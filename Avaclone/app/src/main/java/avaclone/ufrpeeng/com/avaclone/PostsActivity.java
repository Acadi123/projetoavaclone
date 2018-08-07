package avaclone.ufrpeeng.com.avaclone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity {

    String token;
    String disciplina;
    String aluno;

    ArrayList listpost;
    ArrayList listpostid;

    TextView discname;

    ListView listpostsW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
            disciplina = extras.getString("disciplina");
            aluno = extras.getString("aluno");
        }
        listpostsW = findViewById(R.id.listposts);
        discname = findViewById(R.id.discSet);
        discname.setText(disciplina);

        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS posts( id INTEGER PRIMARY KEY AUTOINCREMENT, disciplina VARCHAR , aluno VARCHAR, token VARCHAR, assunto VARCHAR, conteudo VARCHAR, anexo VARCHAR )");

        listpost = Coletaposts();
        listpostid = Coletapostsid();
        Setalistposts(listpost);

        listpostsW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Integer id = (Integer) listpostid.get(i);
                Intent vizualpost = new Intent(PostsActivity.this, VizualizaPostActivity.class);
                vizualpost.putExtra("token", token);
                vizualpost.putExtra("id",id);
                startActivity(vizualpost);}
        });


        //menu
        Drawable menuicon = ContextCompat.getDrawable(PostsActivity.this, R.drawable.ic_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(PostsActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(PostsActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);

                                startActivity(disc);
                                return true;


                            case R.id.perfil:
                                Intent perfil = new Intent(PostsActivity.this, CadastroActivity.class);
                                perfil.putExtra("token", token);
                                startActivity(perfil);
                                return true;


                            case R.id.sobresist:
                                Intent sobresist = new Intent(PostsActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                startActivity(sobresist);
                                return true;


                            case R.id.grade:
                                Intent grade = new Intent(PostsActivity.this, GradeActivity.class);
                                grade.putExtra("token", token);
                                startActivity(grade);

                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(PostsActivity.this, LoginActivity.class);
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.posts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novopost:
                Intent novopost = new Intent(PostsActivity.this, NovopostActivity.class);
                novopost.putExtra("token", token);
                novopost.putExtra("aluno", aluno);
                novopost.putExtra("disciplina", disciplina);
                startActivity(novopost);

                return true;

            case R.id.meusposts:
                Intent meusposts = new Intent(PostsActivity.this, MeuspostsActivity.class);
                meusposts.putExtra("token", token);
                meusposts.putExtra("aluno", aluno);
                meusposts.putExtra("disciplina", disciplina);
                startActivity(meusposts);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public ArrayList Coletaposts() {

        ArrayList<String> Lista = new ArrayList<>();

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            Cursor cursor = bancoDados.rawQuery("SELECT aluno,assunto FROM posts WHERE disciplina = '" + disciplina + "' ", null);
            int colunaaluno = cursor.getColumnIndex("aluno");
            int colunaassunto = cursor.getColumnIndex("assunto");

            cursor.moveToFirst();

            while (cursor != null) {

                String alunopost = cursor.getString(colunaaluno);
                String assunto = cursor.getString(colunaassunto);
                String anexolista = "Aluno: " + alunopost + "\n" + "Assunto: " + assunto;
                Lista.add(anexolista);

                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Lista.size() == 0 ){
            Lista.add("Não há nenhum post!!");
        }
        return Lista;
    }

    public ArrayList Coletapostsid() {

        ArrayList<Integer> Lista = new ArrayList<>();

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            Cursor cursor = bancoDados.rawQuery("SELECT id FROM posts WHERE disciplina = '" + disciplina + "' ", null);
            int colunaid = cursor.getColumnIndex("id");

            cursor.moveToFirst();

            while (cursor != null) {
                Integer id = cursor.getInt(colunaid);
                Lista.add(id);

                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Lista;
    }


    public void Setalistposts(ArrayList<String> Lista) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(PostsActivity.this, android.R.layout.simple_list_item_1, Lista);
        listpostsW.setAdapter(arrayAdapter);
    }
}

