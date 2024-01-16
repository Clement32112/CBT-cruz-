/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class student_score_model {
    public student_score_model (String testName, String matNumber, String numberOfQuestions, String score, String selectedOptions) {
        this.testName = new SimpleStringProperty(testName);
        this.matNumber = new SimpleStringProperty(matNumber);
        this.numberOfQuestions = new SimpleStringProperty(numberOfQuestions);
        this.score = new SimpleStringProperty(score);
        this.selectedOptions = new SimpleStringProperty(selectedOptions);
    }

    public String getTestName() {
        return testName.get();
    }

    public void setTestName(SimpleStringProperty testName) {
        this.testName = testName;
    }
    
    public StringProperty testNameProperty() {
        return testName;
    }

    public String getMatNumber() {
        return matNumber.get();
    }

    public void setMatNumber(SimpleStringProperty matNumber) {
        this.matNumber = matNumber;
    }
    
     public StringProperty matNumberProperty() {
        return matNumber;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions.get();
    }

    public void setNumberOfQuestions(SimpleStringProperty numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
     public StringProperty numberOfQuestionsProperty() {
        return numberOfQuestions;
    }

    public String getScore() {
        return score.get();
    }

    public void setScore(SimpleStringProperty score) {
        this.score = score;
    }
     public StringProperty scoreProperty() {
        return score;
    }

    public String getSelectedOptions() {
        return selectedOptions.get();
    }

    public void setSelectedOptions(SimpleStringProperty selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
    public StringProperty selectedOptionsProperty() {
        return selectedOptions;
    }
    
    @Override
    public String toString() {
      return testName.get() + "," + matNumber.get() + "," + numberOfQuestions.get() + "," + score.get() + "," + selectedOptions.get();
    }


    private SimpleStringProperty testName;
    private SimpleStringProperty matNumber;
    private SimpleStringProperty numberOfQuestions;
    private SimpleStringProperty score;
    private SimpleStringProperty selectedOptions;
}
