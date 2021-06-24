package chefOnly.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PreparationStep {
    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private int stepNumber;
    private  String content;

    public PreparationStep(int stepNumber,String content) {
        this.stepNumber = stepNumber;
        this.content = content;
    }

}
