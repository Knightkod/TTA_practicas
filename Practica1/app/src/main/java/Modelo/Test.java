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

    private List<Choice> Choices = new ArrayList<Choice>();

    public class Choice{

        private String opcText;
        private boolean isCorrect;

        public Choice(String opcText,boolean isCorrect){
            this.opcText=opcText;
            this.isCorrect=isCorrect;
        }

        public String getOpcText() {
            return opcText;
        }

        public boolean isCorrect(){
            return isCorrect;
        }
    }

    public List<Choice> getChoices() {
        Choices.clear();
        //para probar, estos valores se recogerían del servidor
        String[] str = {opc1,opc2,opc3,opc4,opc5};
        boolean[] boolArray={false,false,true,false,false};
        for(int i=0;i<str.length;i++){
            Choice choice = new Choice(str[i],boolArray[i]);
            Choices.add(choice);
        }
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
