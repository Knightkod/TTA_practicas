package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iubuntu on 23/12/17.
 */

public class Test implements Serializable{

    private List<Choice> Choices = new ArrayList<Choice>();

    private String enunciado="";

    public Test() {
        Choices.clear();
    }

    public static class Choice implements Serializable{

        private int opcId = 0;
        private String opcText;
        private boolean isCorrect;
        private String helpMimeType = "";
        private String helpResource = "";

        public int getId() {
            return opcId;
        }

        public void setId(int opcId) {
            this.opcId = opcId;
        }

        public String getOpcText() {
            return opcText;
        }

        public void setOpcText(String opcText) {
            this.opcText = opcText;
        }

        public boolean isCorrect() {
            return isCorrect;
        }

        public void setIsCorrect(boolean isCorrect) {
            this.isCorrect = isCorrect;
        }

        public String getHelpMimeType() {
            return helpMimeType;
        }

        public void setHelpMimeType(String helpMimeType) {
            this.helpMimeType = helpMimeType;
        }

        public String getHelpResource() {
            return helpResource;
        }

        public void setHelpResource(String helpResource) {
            this.helpResource = helpResource;
        }
    }

    public List<Choice> getChoices() {

        return Choices;
    }

    public void setChoice(List<Choice> choice) {
        Choices = choice;
    }

    public void setEnunciado(String enunciado){
        this.enunciado=enunciado;
    }

    public String getEnunciado(){
        return enunciado;
    }

}
