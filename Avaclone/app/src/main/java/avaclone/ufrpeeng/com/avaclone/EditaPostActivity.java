package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class EditaPostActivity extends AppCompatActivity {

    String token;
    String disciplina;
    String aluno;

    EditText assuntoTXT;
    EditText conteudoTXT;
    EditText anexosTXT;

    Button enviarBTN;

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edita_post);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");
            disciplina = extras.getString("disciplina");
            aluno = extras.getString("aluno");
            id = extras.getInt("id");
        }

        assuntoTXT = findViewById(R.id.assuntotxt);
        conteudoTXT = findViewById(R.id.conteudotxt);
        anexosTXT = findViewById(R.id.anexotxt);
        enviarBTN = findViewById(R.id.btnenviar);

        Setinicial();

        enviarBTN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editapost();
            }
        });


        //menu
        Drawable menuicon = ContextCompat.getDrawable(EditaPostActivity.this, R.drawable.ic_menu_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(EditaPostActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(EditaPostActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);
                                disc.putExtra("aluno", aluno);
                                disc.putExtra("disciplina", disciplina);
                                startActivity(disc);
                                return true;


                            case R.id.perfil:
                                Intent perfil = new Intent(EditaPostActivity.this, CadastroActivity.class);
                                perfil.putExtra("token", token);
                                perfil.putExtra("aluno", aluno);
                                perfil.putExtra("disciplina", disciplina);
                                startActivity(perfil);
                                return true;


                            case R.id.sobresist:
                                Intent sobresist = new Intent(EditaPostActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                sobresist.putExtra("aluno", aluno);
                                sobresist.putExtra("disciplina", disciplina);
                                startActivity(sobresist);
                                return true;


                            case R.id.grade:
                                Intent grade = new Intent(EditaPostActivity.this, GradeActivity.class);
                                grade.putExtra("token", token);
                                grade.putExtra("aluno", aluno);
                                grade.putExtra("disciplina", disciplina);
                                startActivity(grade);

                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(EditaPostActivity.this, LoginActivity.class);
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
    public void Setinicial(){
        SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

        Cursor cursor = bancoDados.rawQuery("SELECT assunto,aluno,conteudo,anexo FROM posts WHERE id = '" + id + "' ", null);
        int colunaassunto = cursor.getColumnIndex("assunto");
        int colunaaluno = cursor.getColumnIndex("aluno");
        int colunaconteudo = cursor.getColumnIndex("conteudo");
        int colunaanexo = cursor.getColumnIndex("anexo");
        cursor.moveToFirst();
        String assunto = cursor.getString(colunaassunto);
        String conteudo = cursor.getString(colunaconteudo);
        String anexo = cursor.getString(colunaanexo);

        assuntoTXT.setText(assunto, TextView.BufferType.EDITABLE);
        conteudoTXT.setText(conteudo, TextView.BufferType.EDITABLE);
        anexosTXT.setText(anexo, TextView.BufferType.EDITABLE);

    }
    public void Editapost(){
        String assunto = assuntoTXT.getText().toString();
        String conteudo = conteudoTXT.getText().toString();
        String anexos = anexosTXT.getText().toString();
        if(assunto.isEmpty() || conteudo.isEmpty()){
            Toast errovazio = Toast.makeText(getApplicationContext(), "Campos: Assunto,Conteúdo não podem estar vazios", Toast.LENGTH_SHORT);
            errovazio.show();
        }
        else {
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);
            bancoDados.execSQL("UPDATE posts SET assunto = '" +assunto+"'"+"WHERE id = '"+id+"'");
            bancoDados.execSQL("UPDATE posts SET conteudo = '" +conteudo+"'"+"WHERE id = '"+id+"'");
            bancoDados.execSQL("UPDATE posts SET anexo = '" +anexos+"'"+"WHERE id = '"+id+"'");
            Toast editsucesso = Toast.makeText(getApplicationContext(), "Post editado com sucesso, Redirecionando", Toast.LENGTH_SHORT);
            editsucesso.show();
            Intent meusposts = new Intent(EditaPostActivity.this, MeuspostsActivity.class);
            meusposts.putExtra("token", token);
            meusposts.putExtra("aluno", aluno);
            meusposts.putExtra("disciplina", disciplina);
            startActivity(meusposts);
            finish();

        }


    }

}

