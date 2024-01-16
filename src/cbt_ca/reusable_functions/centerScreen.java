/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import cbt_ca.admin_dashboard.Admin_pageController;
import cbt_ca.student_dashboard.Student_pageController;
import cbt_ca.testPage.ActualTestPageController;
import cbt_ca.testScript.TestScriptController;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class centerScreen {
    public void centerFrame(Stage stage, String route) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        Parent root = loader.load();
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    };
    
    public void centerFrame(
            Stage stage, 
            String route, 
            String firstName, 
            String lastName, 
            String matNumber,
            String level,
            String department, 
            String emailAddressValue, 
            String IDForTest, 
            String nameOfTest, 
            Timestamp dateOfTest, 
            Integer durationOfTest, 
            Integer testNumberOfQuestions, 
            String courseName, 
            String courseCode,
            String courseID
    ) throws IOException, SQLException{
        
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("matNumber", matNumber);
        parameters.put("level", level);
        parameters.put("department", department);
        parameters.put("emailAddressValue", emailAddressValue);
        parameters.put("IDForTest", IDForTest);
        parameters.put("nameOfTest", nameOfTest);
        parameters.put("dateOfTest", dateOfTest);
        parameters.put("durationOfTest", durationOfTest);
        parameters.put("testNumberOfQuestions", testNumberOfQuestions);
        parameters.put("courseName", courseName);
        parameters.put("courseCode", courseCode);
        parameters.put("courseID", courseID);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        Parent root = loader.load();
        ActualTestPageController nextController = loader.getController();
        nextController.setData(parameters);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    };
    
    public void centerFrame(Stage stage, String route, String staffID, String firstName, String lastName, String emailAddress) throws IOException, ClassNotFoundException, SQLException{
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("staffID", staffID);
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("emailAddress", emailAddress);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        Parent root = loader.load();
        Admin_pageController nextController = loader.getController();
        nextController.setData(parameters);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
   
    public void centerFrame(Stage stage, String route, String firstName, String lastName, String matNumber, String level, String department, String email) throws IOException, ClassNotFoundException, SQLException{
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("matNumber", matNumber);
        parameters.put("department", department);
        parameters.put("level", level);
        parameters.put("email", email);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        Parent root = loader.load();
        Student_pageController nextController = loader.getController();
        nextController.setData(parameters);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
    
        public void centerFrame(Stage stage, String route, String firstName, String lastName, Label matNumber, Label courseTitle, Label courseCode, Label date, ArrayList<String> questionsList, ArrayList<String> correctOptionsList, ArrayList<String> optionAList, ArrayList<String> optionBList, ArrayList<String> optionCList, ArrayList<String> optionDList, String[] yourAnswers) throws IOException, ClassNotFoundException, SQLException{
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", firstName);
        parameters.put("lastName", lastName);
        parameters.put("matNumber", matNumber.getText());
        parameters.put("courseTitle", courseTitle.getText());
        parameters.put("courseCode", courseCode.getText());
        parameters.put("date", date.getText());
        parameters.put("questionList", questionsList);
        parameters.put("correctOptionsList", correctOptionsList);
        parameters.put("optionAList", optionAList);
        parameters.put("optionBList", optionBList);
        parameters.put("optionCList", optionCList);
        parameters.put("optionDList", optionDList);
        parameters.put("yourAnswers", yourAnswers);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
        Parent root = loader.load();
        TestScriptController nextController = loader.getController();
        nextController.setData(parameters);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(false);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
}
