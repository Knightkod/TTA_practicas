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
import Modelo.ServerConnection;

public class ExerciseActivity extends AppCompatActivity {

    ServerConnection srvConnection;
    private Exercise exercise = new Exercise();
    public static final int PICTURE_REQUEST_CODE = 1;
    public static final int VIDEO_REQUEST_CODE = 2;
    public static final int AUDIO_REQUEST_CODE = 3;
    public static final int READ_REQUEST_CODE = 4;

    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        TextView textView = (TextView) findViewById(R.id.exercise_wording);
        textView.setText(exercise.getEnunciado());
        srvConnection = new ServerConnection();
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
                    System.out.println("Nombre3: ");
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
                infoFichero(data.getData());
                break;
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                Toast.makeText(getApplicationContext(),("guardado!"),Toast.LENGTH_SHORT).show();
                srvConnection.enviaFichero(data.getData());
                break;
            case PICTURE_REQUEST_CODE:
                Toast.makeText(getApplicationContext(),("guardada!"),Toast.LENGTH_SHORT).show();
                srvConnection.enviaFichero(pictureUri);
                break;
        }
    }

    private void infoFichero(Uri uri){
        Cursor cursor = this.getContentResolver().query(uri,null,null,null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            String size = null;
            if(!cursor.isNull(sizeIndex))
                size=cursor.getString(sizeIndex);
            else
                size="Unknown";
            Toast.makeText(getApplicationContext(),("Nombre: "+displayName+", y tamaño: "+size),Toast.LENGTH_SHORT).show();
        }
    }

}
