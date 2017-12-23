package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    public static final String LOGIN_ID="login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        TextView textLogin = (TextView) findViewById(R.id.menu_login);
        textLogin.setText(R.string.welcome+" "+intent.getStringExtra(LOGIN_ID));
    }
    public void test(){
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);
    }
    public void exercise(){
        Intent intent = new Intent(this,ExerciseActivity.class);
        startActivity(intent);
    }
    public void seguimiento(){
        Toast.makeText(getApplicationContext(),R.string.noFunction,Toast.LENGTH_SHORT).show();
    }
}
