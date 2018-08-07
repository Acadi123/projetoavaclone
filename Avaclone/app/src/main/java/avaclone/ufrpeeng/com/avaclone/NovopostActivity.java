package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

public class NovopostActivity extends AppCompatActivity {

    String token;
    String disciplina;
    String aluno;

    EditText assuntoTXT;
    EditText conteudoTXT;
    EditText anexosTXT;

    Button enviarBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novopost);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
            disciplina = extras.getString("disciplina");
            aluno = extras.getString("aluno");
        }

        assuntoTXT = findViewById(R.id.assuntotxt);
        conteudoTXT = findViewById(R.id.conteudotxt);
        anexosTXT = findViewById(R.id.anexotxt);
        enviarBTN = findViewById(R.id.btnenviar);

        enviarBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Setanovopost();
            }
        });


        //menu
        Drawable menuicon = ContextCompat.getDrawable(NovopostActivity.this, R.drawable.ic_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(NovopostActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(NovopostActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);
                                disc.putExtra("aluno", aluno);
                                disc.putExtra("disciplina", disciplina);
                                startActivity(disc);
                                return true;


                            case R.id.perfil:
                                Intent perfil = new Intent(NovopostActivity.this, CadastroActivity.class);
                                perfil.putExtra("token", token);
                                perfil.putExtra("aluno", aluno);
                                perfil.putExtra("disciplina", disciplina);
                                startActivity(perfil);
                                return true;


                            case R.id.sobresist:
                                Intent sobresist = new Intent(NovopostActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                sobresist.putExtra("aluno", aluno);
                                sobresist.putExtra("disciplina", disciplina);
                                startActivity(sobresist);
                                return true;


                            case R.id.grade:
                                Intent grade = new Intent(NovopostActivity.this, GradeActivity.class);
                                grade.putExtra("token", token);
                                grade.putExtra("aluno", aluno);
                                grade.putExtra("disciplina", disciplina);
                                startActivity(grade);

                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(NovopostActivity.this, LoginActivity.class);
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
    public void Setanovopost(){
       String assunto = assuntoTXT.getText().toString();
       String conteudo = conteudoTXT.getText().toString();
       String anexos = anexosTXT.getText().toString();
       if(assunto.isEmpty() || conteudo.isEmpty()){
           Toast errovazio = Toast.makeText(getApplicationContext(), "Campos: Assunto,Conteúdo não podem estar vazios", Toast.LENGTH_SHORT);
           errovazio.show();
           }

       else {
        if(anexos.isEmpty()){
            anexos = "Sem anexos";
        }
        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
            bancoDados.execSQL("INSERT INTO posts(disciplina, aluno, token, assunto, conteudo, anexo) VALUES ('" + disciplina + "','" + aluno + "','" + token + "','" + assunto + "','" + conteudo + "','" + anexos +"')");
            Toast sucesso = Toast.makeText(getApplicationContext(), "Post feito com sucesso", Toast.LENGTH_LONG);
            sucesso.show();
            Intent retornapostpage = new Intent(NovopostActivity.this, PostsActivity.class);
            retornapostpage.putExtra("token", token);
            retornapostpage.putExtra("aluno", aluno);
            retornapostpage.putExtra("disciplina", disciplina);
            startActivity(retornapostpage);
        }
        catch (Exception e){
            Toast erro = Toast.makeText(getApplicationContext(), "Campos: Assunto,Conteúdo não podem estar vazios", Toast.LENGTH_SHORT);
            erro.show();
            e.printStackTrace();
        }
       }

    }
}


