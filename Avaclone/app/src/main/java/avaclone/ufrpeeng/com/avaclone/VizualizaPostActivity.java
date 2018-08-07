package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.w3c.dom.Text;

public class VizualizaPostActivity extends AppCompatActivity {

    String token;
    int id;

    TextView assuntoW,alunoW,conteudoW,anexoW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vizualiza_post);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
            id = extras.getInt("id");
        }
        assuntoW = findViewById(R.id.cabassunto);
        alunoW = findViewById(R.id.alunoSET);
        conteudoW = findViewById(R.id.conteudoSET);
        anexoW = findViewById(R.id.anexosSET);
        Setapost();

        //menu
        Drawable menuicon = ContextCompat.getDrawable(VizualizaPostActivity.this, R.drawable.ic_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(VizualizaPostActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(VizualizaPostActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);
                                startActivity(disc);
                                return true;


                            case R.id.perfil:
                                Intent perfil = new Intent(VizualizaPostActivity.this, CadastroActivity.class);
                                perfil.putExtra("token", token);
                                startActivity(perfil);
                                return true;


                            case R.id.sobresist:
                                Intent sobresist = new Intent(VizualizaPostActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                startActivity(sobresist);
                                return true;


                            case R.id.grade:
                                Intent grade = new Intent(VizualizaPostActivity.this, GradeActivity.class);
                                grade.putExtra("token", token);
                                startActivity(grade);

                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(VizualizaPostActivity.this, LoginActivity.class);
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

    public void Setapost() {
        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            Cursor cursor = bancoDados.rawQuery("SELECT assunto,aluno,conteudo,anexo FROM posts WHERE id = '" + id + "' ", null);
            int colunaassunto = cursor.getColumnIndex("assunto");
            int colunaaluno = cursor.getColumnIndex("aluno");
            int colunaconteudo = cursor.getColumnIndex("conteudo");
            int colunaanexo = cursor.getColumnIndex("anexo");
            cursor.moveToFirst();
            String assunto = cursor.getString(colunaassunto);
            String aluno = cursor.getString(colunaaluno);
            String conteudo = cursor.getString(colunaconteudo);
            String anexo = cursor.getString(colunaanexo);

            assunto = "Assunto: " + assunto;

            assuntoW.setText(assunto);
            alunoW.setText(aluno);
            conteudoW.setText(conteudo);
            anexoW.setText(anexo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


