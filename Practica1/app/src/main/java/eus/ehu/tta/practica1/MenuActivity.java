package eus.ehu.tta.practica1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import Modelo.ProgressTask;
import Modelo.ServerConnection;
import Modelo.Test;
import Modelo.User;

public class MenuActivity extends AppCompatActivity {
    public static final String LOGIN_ID="login";
    private ServerConnection srvConn;
    private Context context;
    public User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        user=(User) intent.getSerializableExtra(LOGIN_ID);
        TextView textLogin = (TextView) findViewById(R.id.menu_login);
        textLogin.setText(getString(R.string.welcome)+" "+user.getUser());

        TextView menuTitle = (TextView) findViewById(R.id.menu_title);
        menuTitle.setText(user.getLessonNumber()+": "+user.getLessonTitle());
        srvConn=new ServerConnection(this,getResources().getString(R.string.baseUrl));
        context=this;

    }
    public void test(View v){
        new ProgressTask<Test>(this){
            @Override
            protected Test work() throws Exception {

                return srvConn.getTest(user.getNextTest());
            }

            @Override
            protected void onFinish(Test result) {
                if(result!=null) {
                    Intent intent = new Intent(context, TestActivity.class);
                    intent.putExtra(TestActivity.TEST_ID, result);
                    startActivity(intent);
                }
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
