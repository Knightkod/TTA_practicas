package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import Modelo.ServerConnection;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v){
        Intent intent = new Intent(this,MenuActivity.class);
        String login = ((EditText)findViewById(R.id.user)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.passwd)).getText().toString();
        ServerConnection srvConn=new ServerConnection();
        if(srvConn.verificaLogin(login,passwd)){
            intent.putExtra(MenuActivity.LOGIN_ID,login);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.loginError,Toast.LENGTH_SHORT).show();
        }
    }
}
