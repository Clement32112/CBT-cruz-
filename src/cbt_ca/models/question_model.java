/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.models;

/**
 *
 * @author HP PROBOOK 430 G3
 */



import com.mysql.cj.jdbc.Blob;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;
import javafx.beans.property.StringProperty;

public class question_model {
    
    private SimpleStringProperty question;
    private SimpleStringProperty optionA;
    private SimpleStringProperty optionB;
    private SimpleStringProperty optionC;
    private SimpleStringProperty optionD;
    private SimpleStringProperty correctOption;

    public question_model (String question, String optionA, String optionB, String optionC, String optionD, String correctOption) {
        this.question = new SimpleStringProperty(question);
        this.optionA = new SimpleStringProperty(optionA);
        this.optionB = new SimpleStringProperty(optionB);
        this.optionC = new SimpleStringProperty(optionC);
        this.optionD = new SimpleStringProperty(optionD);
        this.correctOption = new SimpleStringProperty(correctOption);
    }

    // Getters and setters for each property
    public String getQuestion() {
        return question.get();
    }

    public void setQuestion(String question) {
        this.question.set(question);
    }

    public StringProperty questionProperty() {
        return question;
    }

    public String getOptionA() {
        return optionA.get();
    }

    public void setOptionA(String optionA) {
        this.optionA.set(optionA);
    }

    public StringProperty optionAProperty() {
        return optionA;
    }

    public String getOptionB() {
        return optionB.get();
    }

    public void setOptionB(String optionB) {
        this.optionB.set(optionB);
    }

    public StringProperty optionBProperty() {
        return optionB;
    }
  
    public String getOptionC() {
       return optionC.get();
    }

   public void setOptionC(String optionC) {
       this.optionC.set(optionC);
    }

   public StringProperty optionCProperty() {
       return optionC;
    }

    public String getOptionD() {
       return optionD.get();
    }

   public void setOptionD(String optionD) {
       this.optionD.set(optionD);
    }

    public StringProperty optionDProperty() {
        return optionD;
    }

    public String getCorrectOption() {
        return correctOption.get();
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption.set(correctOption);
    }

    public StringProperty correctOptionProperty() {
        return correctOption;
    }
}


