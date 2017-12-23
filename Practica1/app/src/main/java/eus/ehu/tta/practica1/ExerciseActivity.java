package eus.ehu.tta.practica1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import Modelo.Exercise;

public class ExerciseActivity extends AppCompatActivity {

    Exercise exercise = new Exercise();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        TextView textView = (TextView) findViewById(R.id.exercise_wording);
        textView.setText(exercise.getEnunciado());
    }

    public void sendFile(View v){
        Toast.makeText(getApplicationContext(),R.string.noFunction,Toast.LENGTH_SHORT).show();
    }
    public void takePhoto(View v){
        Toast.makeText(getApplicationContext(),R.string.noFunction,Toast.LENGTH_SHORT).show();
    }
    public void recordAudio(View v){
        Toast.makeText(getApplicationContext(),R.string.noFunction,Toast.LENGTH_SHORT).show();
    }
    public void recordVideo(View v){
        Toast.makeText(getApplicationContext(),R.string.noFunction,Toast.LENGTH_SHORT).show();
    }
}
