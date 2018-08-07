package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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

public class MeuspostsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_meusposts);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            token = extras.getString("token");
            disciplina = extras.getString("disciplina");
            aluno = extras.getString("aluno");
        }
        listpostsW = findViewById(R.id.listposts);
        discname = findViewById(R.id.discSet);
        discname.setText("Meus posts em:\n"+disciplina);

        listpost = Coletaposts();
        listpostid = Coletapostsid();
        Setalistposts(listpost);

        listpostsW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final Integer id = (Integer) listpostid.get(i);
                PopupMenu menupost = new PopupMenu(MeuspostsActivity.this, view);
                menupost.getMenuInflater().inflate(R.menu.menu_edit_post, menupost.getMenu());
                menupost.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.vizualpost:
                                Intent vizualpost = new Intent(MeuspostsActivity.this, VizualizaPostActivity.class);
                                vizualpost.putExtra("token", token);
                                vizualpost.putExtra("id",id);
                                vizualpost.putExtra("disciplina",disciplina);
                                startActivity(vizualpost);

                                return true;


                            case R.id.editarpost:
                                Intent editapost = new Intent(MeuspostsActivity.this, EditaPostActivity.class);
                                editapost.putExtra("token", token);
                                editapost.putExtra("id",id);
                                editapost.putExtra("disciplina",disciplina);
                                startActivity(editapost);

                                return true;

                            case R.id.excluirpost:
                                Deletapost(id);
                                Intent posts = new Intent(MeuspostsActivity.this, PostsActivity.class);
                                posts.putExtra("token", token);
                                posts.putExtra("aluno",aluno);
                                posts.putExtra("disciplina",disciplina);
                                startActivity(posts);


                                return true;


                            default:
                                return false;
                        }
                    }
                });
                menupost.show();
            }
        });



        //menu
        Drawable menuicon = ContextCompat.getDrawable(MeuspostsActivity.this, R.drawable.ic_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(MeuspostsActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(MeuspostsActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);
                                startActivity(disc);
                                return true;


                            case R.id.perfil:
                                Intent perfil = new Intent(MeuspostsActivity.this, CadastroActivity.class);
                                perfil.putExtra("token", token);
                                startActivity(perfil);
                                return true;


                            case R.id.sobresist:
                                Intent sobresist = new Intent(MeuspostsActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                startActivity(sobresist);
                                return true;


                            case R.id.grade:
                                Intent grade = new Intent(MeuspostsActivity.this, GradeActivity.class);
                                grade.putExtra("token", token);
                                startActivity(grade);

                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(MeuspostsActivity.this, LoginActivity.class);
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

    public ArrayList Coletaposts() {

        ArrayList<String> Lista = new ArrayList<>();

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            Cursor cursor = bancoDados.rawQuery("SELECT aluno,assunto FROM posts WHERE (token = '" + token + "' AND disciplina = '" + disciplina + "')", null);
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
            Lista.add("Você não tem nenhum post!!");
        }
        return Lista;
    }

    public ArrayList Coletapostsid() {

        ArrayList<Integer> Lista = new ArrayList<>();

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            Cursor cursor = bancoDados.rawQuery("SELECT id FROM posts WHERE (token = '" + token + "' AND disciplina = '" + disciplina + "')", null);
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
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MeuspostsActivity.this, android.R.layout.simple_list_item_1, Lista);
        listpostsW.setAdapter(arrayAdapter);
    }

    public void Deletapost(Integer iddin){
        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
        bancoDados.execSQL("DELETE FROM posts WHERE id = '"+iddin+"'");
        Toast deletado = Toast.makeText(getApplicationContext(), "Post deletado com sucesso, redirecionando.", Toast.LENGTH_SHORT);
        deletado.show();
    }
}

