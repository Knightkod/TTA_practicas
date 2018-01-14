package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import Modelo.ProgressTask;
import Modelo.ServerConnection;
import Modelo.Test;
import Modelo.User;

public class MainActivity extends AppCompatActivity{
    private ServerConnection srvConn;
    String login ="";
    String passwd="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v){
        login = ((EditText)findViewById(R.id.user)).getText().toString();
        passwd = ((EditText)findViewById(R.id.passwd)).getText().toString();
        srvConn=new ServerConnection(this,getResources().getString(R.string.baseUrl));

        new ProgressTask<User>(this){
            @Override
            protected User work() throws Exception {
                return srvConn.verificaLogin(login,passwd);
            }

            @Override
            protected void onFinish(User result) {
                if(result!=null) {
                    Intent intent = new Intent(context, MenuActivity.class);
                    intent.putExtra(MenuActivity.LOGIN_ID, result);
                    startActivity(intent);
                }
            }
        }.execute();

    }
}
