package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iubuntu on 23/12/17.
 */

public class Test {
    private String enunciado="¿Cúal de las siguientes opciones NO se indica en el fichero de manifiesto de la app?";
    private String opc1="Versión de la aplicación";
    private String opc2="Listado de componentes de la aplicación";
    private String opc3="Opciones del menú de ajustes";
    private String opc4="Nivel mínimo de la API android requerida";
    private String opc5="Nombre del paquete java de la aplicación";

    private String advise="<p>The manifest describes the components of  the application: the activities," +
            "services, broadcast receivers, and content providers that the application is composed of." +
            "It names the classes that implement each of the components and publishes their capabilities" +
            " (for example, which iIntent messages they can handle). These declarations let the Android" +
            "system know that the components are and under that conditions that can be launched </p>";


    private List<Choice> Choices = new ArrayList<Choice>();

    public Test(){
        Choices.clear();
        //para probar, estos valores se recogerían del servidor
        String[] str = {opc1,opc2,opc3,opc4,opc5};
        boolean[] boolArray={false,false,true,false,false};
        String[] mimeTypeArray={"video","audio",null,"text/html","text/html"};
        String[] helpResourceArray={
                "http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4",
                "http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4",
                null,
                "http://google.es",
                advise
        };
        for(int i=0;i<str.length;i++){
            Choice choice = new Choice(str[i],boolArray[i],mimeTypeArray[i],helpResourceArray[i]);
            Choices.add(choice);
        }
    }

    public class Choice{

        private String opcText;
        private boolean isCorrect;
        private String helpMimeType = "";
        private String helpResource="";

        public Choice(String opcText,boolean isCorrect, String helpMimeType,String helpResource){
            this.opcText=opcText;
            this.isCorrect=isCorrect;
            this.helpMimeType=helpMimeType;
            this.helpResource=helpResource;
        }

        public String getOpcText() {
            return opcText;
        }

        public boolean isCorrect(){
            return isCorrect;
        }

        public String getHelpMimeType() {
            return helpMimeType;
        }

        public String getHelpResource(){
            return helpResource;
        }
    }

    public List<Choice> getChoices() {

        return Choices;
    }

    public void setChoice(List<Choice> choice) {
        Choices = choice;
    }


    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getOpc1() {
        return opc1;
    }

    public void setOpc1(String opc1) {
        this.opc1 = opc1;
    }

    public String getOpc2() {
        return opc2;
    }

    public void setOpc2(String opc2) {
        this.opc2 = opc2;
    }

    public String getOpc3() {
        return opc3;
    }

    public void setOpc3(String opc3) {
        this.opc3 = opc3;
    }

    public String getOpc4() {
        return opc4;
    }

    public void setOpc4(String opc4) {
        this.opc4 = opc4;
    }

    public String getOpc5() {
        return opc5;
    }

    public void setOpc5(String opc5) {
        this.opc5 = opc5;
    }
}
