package Modelo;

import android.net.Uri;

import Interfaces.LoginInterface;
/**
 * Created by iubuntu on 23/12/17.
 */

public class ServerConnection implements LoginInterface {


    @Override
    public boolean verificaLogin(String user, String password) {
        /*puenteando consulta a server*/
        boolean verification=false;
        if(user.equals("prueba")){
            if(password.equals("prueba"))
                verification=true;
        }
        return verification;
    }

    @Override
    public void enviaFichero(Uri uri){

    }
}
