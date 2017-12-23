package Modelo;

import Interfaces.loginInterface;
/**
 * Created by iubuntu on 23/12/17.
 */

public class ServerConnection implements loginInterface{


    @Override
    public boolean verificaLogin(String user, String password) {
        /*puenteando consulta a server*/
        boolean verification=false;
        if(user=="prueba"){
            if(password=="prueba")
                verification=true;
        }
        return verification;
    }
}
