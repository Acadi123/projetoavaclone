package avaclone.ufrpeeng.com.avaclone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toolbar;


public class GradeActivity extends AppCompatActivity {

    String token;

    String url = "http://ava.ufrpe.br/webservice/rest/server.php?moodlewsrestformat=json";

    ListView listgradeW;


    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_grade);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listgradeW = findViewById(R.id.listgrade);
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }
}
