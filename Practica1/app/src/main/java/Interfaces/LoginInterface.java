package Interfaces;

import android.net.Uri;

/**
 * Created by iubuntu on 23/12/17.
 */

public interface LoginInterface {
    public abstract boolean verificaLogin(String user,String password);
    public abstract void enviaFichero(Uri uri);
}
