package Modelo;

import java.io.Serializable;

/**
 * Created by iubuntu on 23/12/17.
 */

public class Exercise implements Serializable{

    private String enunciado="Explica cómo aplicarías el patrón de diseño MVP en el desarrollo de una app para Android";
    private String fileUrl="";
    private int id=0;


    public String getEnunciado(){
        return enunciado;
    }

    public String getFileUrl(){
        return fileUrl;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
