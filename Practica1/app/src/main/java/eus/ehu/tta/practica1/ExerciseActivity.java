package eus.ehu.tta.practica1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import Modelo.Exercise;
import Modelo.ProgressTask;
import Modelo.ServerConnection;
import Modelo.Test;
import Modelo.User;

public class ExerciseActivity extends AppCompatActivity {

    ServerConnection srvConnection;
    private Exercise exercise = new Exercise();

    private User user;
    private Uri uri;
    private ServerConnection srvConn=null;

    public static final String EXERCISE_ID = "exercise";
    public static final String USER="user";

    public static final int PICTURE_REQUEST_CODE = 1;
    public static final int VIDEO_REQUEST_CODE = 2;
    public static final int AUDIO_REQUEST_CODE = 3;
    public static final int READ_REQUEST_CODE = 4;

    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        Intent intent = getIntent();
        exercise = (Exercise) intent.getSerializableExtra(EXERCISE_ID);
        user = (User) intent.getSerializableExtra(USER);
        TextView textView = (TextView) findViewById(R.id.exercise_wording);
        textView.setText(exercise.getEnunciado());
        srvConnection = new ServerConnection(this, getResources().getString(R.string.baseUrl));
    }

    public void sendFile(View v) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

    public void takePhoto(View v) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getApplicationContext(), R.string.noCamera, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile("ttaImage", ".jpg", dir);
                    pictureUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);

                } catch (IOException e) {
                    System.out.println(e);
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.noApp, Toast.LENGTH_SHORT).show();
            }
        }


    }

    public void recordAudio(View v) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)) {
            Toast.makeText(getApplicationContext(), R.string.noMicro, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, AUDIO_REQUEST_CODE);
            } else {
                Toast.makeText(getApplicationContext(), R.string.noApp, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordVideo(View v) {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(getApplicationContext(), R.string.noCamera, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            } else {
                Toast.makeText(getApplicationContext(), R.string.noApp, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode) {
            case READ_REQUEST_CODE:
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
               uri=data.getData();
                break;
            case PICTURE_REQUEST_CODE:
                uri=pictureUri;
                break;
        }
        if(uri!=null) {
            new ProgressTask<Void>(this){
                @Override
                protected Void work() throws Exception {
                    srvConnection.enviaFichero(exercise.getId(), user.getId(), uri, user.getDni(), user.getPasswd());
                    return null;
                }

                @Override
                protected void onFinish(Void result) {
                }
            }.execute();

        }
    }



}
