/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cbt_ca.testScript;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.transform.Scale;

/**
 * FXML Controller class
 *
 * @author HP PROBOOK 430 G3
 */
public class TestScriptController implements Initializable {

    @FXML
    private Label fullName;
    @FXML
    private Label matNumber;
    @FXML
    private Label courseTitle;
    @FXML
    private Label courseCode;
    @FXML
    private Label currentDate;
    @FXML
    private VBox questionAndAnswerBox;
    @FXML
    private ImageView logo;
    
    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> correctOptionsList = new ArrayList<>();
    ArrayList<String> optionAList = new ArrayList<>();
    ArrayList<String> optionBList = new ArrayList<>();
    ArrayList<String> optionCList = new ArrayList<>();
    ArrayList<String> optionDList = new ArrayList<>();
    String[] yourAnswers;
    @FXML
    private VBox script;
    private NodePrinter printer = new NodePrinter();

    /**
     * Initializes the controller class.
     * @param data
     */
    
    
    public void setData(HashMap<String, Object> data) {
        fullName.setText("Name: " + (String) data.get("firstName") + " " + (String) data.get("lastName"));
        matNumber.setText((String) data.get("matNumber"));
        courseTitle.setText((String) data.get("courseTitle"));
        courseCode.setText((String) data.get("courseCode"));
        currentDate.setText((String) data.get("date"));
        questionsList = (ArrayList<String>) data.get("questionList");
        correctOptionsList = (ArrayList<String>) data.get("correctOptionsList");
        optionAList = (ArrayList<String>) data.get("optionAList");
        optionBList = (ArrayList<String>) data.get("optionBList");
        optionCList = (ArrayList<String>) data.get("optionCList");
        optionDList = (ArrayList<String>) data.get("optionDList");
        yourAnswers = (String[]) data.get("yourAnswers");
        StringBuilder scriptContent = new StringBuilder();
        
        for (int i = 0; i < questionsList.size(); i++) {
            Text questionText = new Text("Question " + (i + 1) + ": " + questionsList.get(i));
            questionAndAnswerBox.getChildren().add(questionText);

            String optionA = "A. " + optionAList.get(i);
            String optionB = "B. " + optionBList.get(i);
            String optionC = "C. " + optionCList.get(i);
            String optionD = "D. " + optionDList.get(i);

            questionAndAnswerBox.getChildren().add(styleAnswer(optionA, yourAnswers[i].equalsIgnoreCase("a"), correctOptionsList.get(i).equalsIgnoreCase("a")));
            questionAndAnswerBox.getChildren().add(styleAnswer(optionB, yourAnswers[i].equalsIgnoreCase("b"), correctOptionsList.get(i).equalsIgnoreCase("b")));
            questionAndAnswerBox.getChildren().add(styleAnswer(optionC, yourAnswers[i].equalsIgnoreCase("c"), correctOptionsList.get(i).equalsIgnoreCase("c")));
            questionAndAnswerBox.getChildren().add(styleAnswer(optionD, yourAnswers[i].equalsIgnoreCase("d"), correctOptionsList.get(i).equalsIgnoreCase("d")));

            // Add some spacing between questions
            questionAndAnswerBox.getChildren().add(new Text("\n"));
        }
    }
    
    private Text styleAnswer(String text, boolean isSelected, boolean isCorrect) {
        Text styledText = new Text(text);

         if (isSelected) {
            if (isCorrect) {
                styledText.setFill(Color.GREEN);
            } else {
                styledText.setFill(Color.RED);
            }
        } else if (isCorrect) {
            // Highlight the correct answer in green even if not selected
            styledText.setFill(Color.GREEN);
        } else {
            styledText.setFill(Color.BLACK); // Set the default color if not selected
        }

        return styledText;
}

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1); // -1 means fully desaturated (grayscale)
        logo.setEffect(grayscale);
    }    

    @FXML
    private void printUserScript(MouseEvent event) {
        Rectangle printBounds = new Rectangle(script.getWidth(), script.getHeight());
        printer.setPrintRectangle(printBounds);

        // Create and configure a PrinterJob
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Print the script VBox using the NodePrinter class
            boolean success = printer.print(job, true, script);
            if (success) {
                job.endJob(); // Only call this if printing is successful
            }
        }
    }
    
}
