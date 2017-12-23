package eus.ehu.tta.practica1;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener{
    private String advise="<p>The manifest describes the components of  the application: the activities," +
            "services, broadcast receivers, and content providers that the application is composed of." +
            "It names the classes that implement each of the components and publishes their capabilities" +
            " (for example, which iIntent messages they can handle). These declarations let the Android" +
            "system know that the components are and under that conditions that can be launched </p>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        /*Test test = data.getTest();
        TextView textWording = (TextView)findViewById(R.id.test_wording);
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int i = 0;
        for (Test.Choice choice : test.getChoices()){
            RadioButton radio = new RadioButton(this);
            radio.setText();
            radio.setOnClickListener(this);
            group.addView(radio);
            if(choice.isCorrect())
                correct=i;
            i++;
        }*/

    }


    @Override
    public void onClick(View v){
        findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
    }

    public void sendTest(View v){

    }

    public void sendHelp(View v){
        WebView web = new WebView(this);
        web.loadData(advise,"text/html",null);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);

    }

}
