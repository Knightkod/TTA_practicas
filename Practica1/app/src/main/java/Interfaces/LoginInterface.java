package Interfaces;

import android.net.Uri;

import Modelo.Exercise;
import Modelo.Test;

/**
 * Created by iubuntu on 23/12/17.
 */

public interface LoginInterface {
    public abstract boolean verificaLogin(String user,String password);
    public abstract void enviaFichero(Uri uri);
    public abstract Test getTest(int id);
    public abstract String putTest(Test test);
    public abstract Exercise getExercise(int id);
    public abstract void uploadChoice(int userId, int choiceId);
}
