package avaclone.ufrpeeng.com.avaclone;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class GradeActivity extends AppCompatActivity {

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            token = extras.getString("token");}

        Drawable menuicon = ContextCompat.getDrawable(GradeActivity.this, R.drawable.ic_menu_nav);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(menuicon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(GradeActivity.this, view);
                menu.getMenuInflater().inflate(R.menu.menu_main, menu.getMenu());
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.disciplinas:
                                Intent disc = new Intent(GradeActivity.this, DiscipActivity.class);
                                disc.putExtra("token", token);
                                startActivity(disc);
                                return true;

                            case R.id.perfil:
                                Intent perf = new Intent(GradeActivity.this, CadastroActivity.class);
                                perf.putExtra("token", token);
                                startActivity(perf);
                                return true;

                            case R.id.sobresist:
                                Intent sobresist = new Intent(GradeActivity.this, SobresistActivity.class);
                                sobresist.putExtra("token", token);
                                startActivity(sobresist);
                                return true;

                            case R.id.grade:
                                Toast actatual = Toast.makeText(GradeActivity.this, "Você já esta nessa pagina", Toast.LENGTH_SHORT);
                                actatual.show();
                                return true;

                            case R.id.logout:
                                Intent logout = new Intent(GradeActivity.this, LoginActivity.class);
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
}
