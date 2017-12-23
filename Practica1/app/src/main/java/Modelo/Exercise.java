package Modelo;

/**
 * Created by iubuntu on 23/12/17.
 */

public class Exercise {

    private String enunciado="Explica cómo aplicarías el patrón de diseño MVP en el desarrollo de una app para Android";
    private String fileUrl="";

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


}
