package Modelo;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import Interfaces.LoginInterface;
import eus.ehu.tta.practica1.R;

/**
 * Created by iubuntu on 23/12/17.
 */

public class ServerConnection implements LoginInterface {

    public static final int EXTRA_TEST = 1;
    private Context context = null;
    private ConnectivityManager connMng = null;
    private RestClient restClient;

    public ServerConnection(Context context, String baseUrl) {
        this.context = context;
        connMng = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        restClient = new RestClient(baseUrl);
    }

    @Override
    public User verificaLogin(String dni, String password) {
        boolean verification = false;
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            restClient.setHttpBasicAuth(dni,password);
            try {
                JSONObject json=restClient.getJson(String.format("getStatus?dni=%s", dni));
                User user = new User();
                user.setId(json.getInt("id"));
                user.setUser(json.getString("user"));
                user.setLessonTitle(json.getString("lessonNumber"));
                user.setLessonNumber(json.getString("lessonTitle"));
                user.setNextTest(Integer.parseInt(json.getString("nextTest")));
                user.setNextExercise(Integer.parseInt(json.getString("nextExercise")));

                return user;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


    @Override
    public void enviaFichero(int exerciseId, int userId, Uri uri, String user, String passwd){
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            InputStream is=null;
            String fileName=null;
            if(uri.toString().startsWith("file")){
                try {
                    is=new FileInputStream(uri.toString().substring(7));
                    String[] pathSections=uri.toString().split("/");
                    fileName=pathSections[pathSections.length-1];
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    is=context.getContentResolver().openInputStream(uri);
                    fileName=infoFichero(uri).get(0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            if(is != null && !fileName.isEmpty() && fileName!=null)
            try {
                restClient.setHttpBasicAuth(user, passwd);
                int code = restClient.postFile(String.format("postExercise?user%d&id=%d",userId,exerciseId),is,fileName);
                System.out.println(code);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    private List<String> infoFichero(Uri uri){
        Cursor cursor = context.getContentResolver().query(uri,null,null,null,null,null);
        List<String> str=new ArrayList<String>();
        if(cursor != null && cursor.moveToFirst()){
            String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            String size = null;
            if(!cursor.isNull(sizeIndex))
                size=cursor.getString(sizeIndex);
            else
                size="Unknown";

            str.add(displayName);
            str.add(size);

            cursor.close();
        }
        return str;
    }

    @Override
    public Test getTest(int id, String user, String passwd) {
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            try {

            restClient.setHttpBasicAuth(user, passwd);
            Test test = new Test();
            JSONObject json = restClient.getJson(String.format("getTest?id=%d", id));
               test.setEnunciado(json.getString("wording"));
                JSONArray array = json.getJSONArray("choices");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    Test.Choice choice = new Test.Choice();
                    choice.setId(item.getInt("id"));
                    choice.setOpcText(item.getString("answer"));
                    choice.setIsCorrect(item.getBoolean("correct"));
                    choice.setHelpResource(item.optString("advise", null));
                    if(!item.isNull("resourceType")) {
                        JSONObject resType = item.getJSONObject("resourceType");
                        choice.setHelpMimeType(resType.getString("mime"));
                    }
                    else
                        choice.setHelpMimeType(null);
                    test.getChoices().add(choice);
                }

              return test;
            } catch (JSONException e) {
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }


        return null;
    }

    @Override
    public String putTest(Test test) {
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                JSONObject json = new JSONObject();
                json.put("wording", test.getEnunciado());
                JSONArray jsonArray = new JSONArray();
                for (Test.Choice choice : test.getChoices()) {
                    JSONObject item = new JSONObject();
                    item.put("id", choice.getId());
                    item.put("wording", choice.getOpcText());
                    item.put("correct", choice.isCorrect());
                    item.put("advise", choice.getHelpResource());
                    item.put("mime", choice.getHelpMimeType());
                    jsonArray.put(item);
                }
                json.put("choices", jsonArray);
                return json.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Exercise getExercise(int id, String user, String passwd) {
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                restClient.setHttpBasicAuth(user, passwd);
                JSONObject jsonObject = restClient.getJson(String.format("getExercise?id=%d", id));
                Exercise exercise = new Exercise();
                exercise.setId(jsonObject.getInt("id"));
                exercise.setEnunciado(jsonObject.getString("wording"));
                return exercise;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public void uploadChoice(int userId, int choiceId, String user, String passwd) {
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            JSONObject jsonObject = new JSONObject();
            try {
                restClient.setHttpBasicAuth(user, passwd);
                jsonObject.put("userId", userId);
                jsonObject.put("choiceId", choiceId);
                int code = restClient.postJson(jsonObject, "postChoice");


                    System.out.println("Code: "+code);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
