package Interfaces;

import android.net.Uri;

import java.io.InputStream;

import Modelo.Exercise;
import Modelo.Test;
import Modelo.User;

/**
 * Created by iubuntu on 23/12/17.
 */

public interface LoginInterface {
    public abstract User verificaLogin(String user, String password);
    public abstract void enviaFichero(int exerciseId, int userId, Uri uri, String user, String passwd);
    public abstract Test getTest(int id, String user, String password);
    public abstract String putTest(Test test);
    public abstract Exercise getExercise(int id, String user, String password);
    public abstract void uploadChoice(int userId, int choiceId, String user, String passwd);
}
