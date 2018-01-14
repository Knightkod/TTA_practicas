package eus.ehu.tta.practica1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import Modelo.AudioPlayer;
import Modelo.ProgressTask;
import Modelo.ServerConnection;
import Modelo.Test;
import Modelo.User;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, Runnable{
    private RadioGroup group;
    private Test test;
    private User user;
    private int correct=0;
    private int selected=0;
    private ServerConnection srvConn;

    public static final String TEST_ID="testID";
    public static final String USER="user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        test = (Test) intent.getSerializableExtra(TEST_ID);
        user = (User) intent.getSerializableExtra(USER);
        srvConn=new ServerConnection(this,getResources().getString(R.string.baseUrl));
        TextView textWording = (TextView)findViewById(R.id.test_wording);
        textWording.setText(test.getEnunciado());
        group = (RadioGroup)findViewById(R.id.test_choices);

        int i = 0;
        for (Test.Choice choice : test.getChoices()){
            RadioButton radio = new RadioButton(this);
            radio.setText(choice.getOpcText());
            radio.setOnClickListener(this);
            group.addView(radio);
            if(choice.isCorrect())
                correct=i;
            i++;
        }

    }


    @Override
    public void onClick(View v){
        findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
        selected = group.indexOfChild(v);
    }

    public void sendTest(View v){

        int choices = group.getChildCount();

        for(int i = 0;i<choices;i++){
            group.getChildAt(i).setEnabled(false);
        }
        ViewGroup layout = (ViewGroup) v.getParent();
        layout.removeView(findViewById(R.id.button_send_test));
        group.getChildAt(correct).setBackgroundColor(Color.GREEN);
        if((selected)!=correct){
            group.getChildAt(selected).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), R.string.testError,Toast.LENGTH_SHORT).show();

            if(test.getChoices().get(selected).getHelpResource()!=null)
                findViewById(R.id.button_send_help).setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(getApplicationContext(), R.string.testOk, Toast.LENGTH_SHORT).show();

        sendResults();

    }

    private void sendResults(){

        new ProgressTask<Void>(this){
            @Override
            protected Void work() throws Exception {

                srvConn.uploadChoice(user.getId(),test.getChoices().get(selected).getId(),user.getDni(),user.getPasswd());
                return null;
            }

            @Override
            protected void onFinish(Void result) {

            }
        }.execute();
    }

    public void sendHelp(View v){
        findViewById(R.id.button_send_help).setEnabled(false);

        String helpType= test.getChoices().get(selected).getHelpMimeType();
        String helpResource=test.getChoices().get(selected).getHelpResource();
        if(helpType.equals("text/html"))
            viewHtmlHelp(helpResource,v);
        if(helpType.equals("video/mp4"))
            viewVideoHelp(helpResource,v);
        if(helpType.equals("audio/mpeg"))
            viewAudioHelp(helpResource,v);


    }

    private void viewHtmlHelp(String resource, View v){
        if(resource.substring(0,10).contains("://")){
            Uri uri = Uri.parse(resource);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }else {
            WebView web = new WebView(this);
            web.loadData(resource, "text/html", null);
            web.setBackgroundColor(Color.TRANSPARENT);
            web.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

            ViewGroup layout = (ViewGroup) v.getParent();
            layout.addView(web);
        }
    }

    private void viewVideoHelp(String resource, View v){
        VideoView videoView = new VideoView(this);
        videoView.setVideoURI(Uri.parse(resource));

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        videoView.setLayoutParams(layoutParams);

        MediaController mediaController = new MediaController(this){
            @Override
            public void hide(){

            }
            @Override
            public boolean dispatchKeyEvent(KeyEvent keyEvent){
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_BACK)
                    finish();
                return super.dispatchKeyEvent(keyEvent);

            }

        };

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        ViewGroup layout = (ViewGroup) v.getParent();
        layout.addView(videoView);
        videoView.start();
    }

    private void viewAudioHelp(String resource, View v){
        LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        AudioPlayer audioPlayer= new AudioPlayer(linearLayout,this);
        try {
            audioPlayer.setAudioUri(Uri.parse(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ViewGroup layout = (ViewGroup) v.getParent();
        layout.addView(linearLayout);
    }


    @Override
    public void run() {
        this.finish();
    }
}
