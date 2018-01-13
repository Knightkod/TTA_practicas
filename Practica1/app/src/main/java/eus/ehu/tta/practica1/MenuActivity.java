package eus.ehu.tta.practica1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Modelo.ProgressTask;
import Modelo.ServerConnection;
import Modelo.Test;

public class MenuActivity extends AppCompatActivity {
    public static final String LOGIN_ID="login";
    ServerConnection srvConn;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        TextView textLogin = (TextView) findViewById(R.id.menu_login);

        textLogin.setText(getString(R.string.welcome)+" "+(intent.getStringExtra(LOGIN_ID)));
        srvConn=new ServerConnection(this,Integer.toString(R.string.baseUrl));
        context=this;

    }
    public void test(View v){
        new ProgressTask<Test>(this){

            @Override
            protected Test work() throws Exception {

                return srvConn.getTest(1);
            }

            @Override
            protected void onFinish(Test result) {

                Intent intent = new Intent(context,TestActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
    public void exercise(View v){
        Intent intent = new Intent(this,ExerciseActivity.class);
        startActivity(intent);
    }
    public void seguimiento(View v){
        Toast.makeText(getApplicationContext(),R.string.noFunction,Toast.LENGTH_SHORT).show();
    }
}
