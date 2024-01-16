/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cbt_ca.testPage;

import cbt_ca.reusable_functions.centerScreen;
import java.io.IOException;
import static java.lang.System.err;
import cbt_ca.reusable_functions.databaseConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP PROBOOK 430 G3
 */
public class ActualTestPageController implements Initializable {

    @FXML
    private Label timeTextLabel;
    @FXML
    private Label actualTimeValue;
    @FXML
    private Label matNumber;
    @FXML
    private Label coutseTitle;
    @FXML
    private Label courseCode;
    @FXML
    private Label date;
    @FXML
    private Label duration;
    @FXML
    private VBox questionBox;
    @FXML
    private Label questionNumber;
    @FXML
    private Text actualQuestion;
    @FXML
    private RadioButton optionA;
    @FXML
    private ToggleGroup options;
    @FXML
    private RadioButton optionB;
    @FXML
    private RadioButton optionC;
    @FXML
    private RadioButton optionD;
    
    boolean started = false;
    boolean submitted = false;
    @FXML
    private Button statButton;
    
    private final databaseConnection currentConnection;
    private Timestamp startTime;
    private Integer durationTime;
    private Integer testID;
    private Timeline timeline;
    private Integer numberOfQuestions;
    private String course_id;
    private Integer i = 0;
    private Integer score = 0;
    private String firstName;
    private String lastName;
    private String matNumberValue;
    private String level;
    private String department;
    private String email;
    
    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> correctOptionsList = new ArrayList<>();
    ArrayList<String> optionAList = new ArrayList<>();
    ArrayList<String> optionBList = new ArrayList<>();
    ArrayList<String> optionCList = new ArrayList<>();
    ArrayList<String> optionDList = new ArrayList<>();
    String[] yourAnswers;

    // Fill the array with empty strings
    
    @FXML
    private Label closeQuizLabel;
    @FXML
    private VBox instructionBox;
    @FXML
    private Button previousQuestion;
    @FXML
    private Button nextQuestion;
    @FXML
    private VBox testScorePart;
    @FXML
    private Label testScoreText;
    @FXML
    private VBox closeQuizButton;
    
    public ActualTestPageController() throws ClassNotFoundException, SQLException {
        this.currentConnection = new databaseConnection();
    }
    Time timer;
    
    public void setData(HashMap<String, Object> data) throws SQLException {
        matNumber.setText("Matriculation number: " + (String) data.get("matNumber"));
        matNumberValue = (String) data.get("matNumber");
        coutseTitle.setText("Course title: " + (String) data.get("courseName"));
        courseCode.setText("Course code: " + (String) data.get("courseCode"));
        numberOfQuestions  = (Integer) data.get("testNumberOfQuestions");
        yourAnswers = new String[numberOfQuestions];
        firstName = (String) data.get("firstName");
        lastName = (String) data.get("lastName");
        email = (String) data.get("emailAddressValue");
        testID = Integer.valueOf((String) data.get("IDForTest"));
        Arrays.fill(yourAnswers, "");
        startTime = (Timestamp) data.get("dateOfTest");
        Date dateValue = new Date(startTime.getTime());
        course_id = (String) data.get("courseID");
        level = (String) data.get("level");
        department = (String) data.get("department");
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        date.setText("Date: " + dateFormat.format(dateValue));
        durationTime = (Integer) data.get("durationOfTest");
        Integer hours = durationTime / 60;
        Integer minutesleft = durationTime % 60;
        duration.setText("Test duration: " + hours.toString() + " hours " + minutesleft.toString() + " minutes");
        timer = new Time(statButton, actualTimeValue);
        Thread t1 = new Thread(timer);
        t1.start();
        
        currentConnection.getTestQuestionsFromDataBase(
            "SELECT * FROM test_questions WHERE course_id = ? ORDER BY RAND() LIMIT " + numberOfQuestions, 
            course_id, 
            numberOfQuestions,
            questionsList,
            correctOptionsList,
            optionAList,
            optionBList,
            optionCList,
            optionDList
        );

        setQuestion();
        checkButton();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }       

    @FXML
    private void closeTextPage(MouseEvent event) throws IOException, ClassNotFoundException, SQLException {
        if(started){
            instructionBox.setVisible(false);
            statButton.setVisible(false);
            Toggle selectedToggle = options.getSelectedToggle();
            if (selectedToggle != null) {
                String selectedOptionText = ((RadioButton) selectedToggle).getText().toLowerCase().substring(0, 1);
                yourAnswers[i] = selectedOptionText;
            }
            calculateTestScore();
        }else{
            Stage currentStage = (Stage) closeQuizButton.getScene().getWindow();
                centerScreen currentScreen = new centerScreen();
                currentStage.close();
                currentScreen.centerFrame(currentStage, "/cbt_ca/student_dashboard/student_page.fxml", firstName, lastName,  matNumber.getText(), level, department,  email);

        }
    }

    @FXML
    private void startTheTest(ActionEvent event) {
        instructionBox.setVisible(false);
        questionBox.setVisible(true);
        statButton.setVisible(false);
    }

    @FXML
    private void getSelected(ActionEvent event) {
    }

    @FXML
    private void changeQuestion(ActionEvent event) {
        Button button = (Button) event.getSource();
        Toggle selectedToggle = options.getSelectedToggle();
        switch (button.getText()) {
            case "Next" -> {
                String selectedOptionText = "";
                if (selectedToggle != null) {
                    selectedOptionText = ((RadioButton) selectedToggle).getText().toLowerCase().substring(0, 1);
                }
                yourAnswers[i] = selectedOptionText;
                if (i + 1 < numberOfQuestions) {
                    i += 1;
                    setQuestion();
                    if (submitted == false && yourAnswers[i].equals("")) {
                        options.selectToggle(null);
                    } else {
                        setOption(i);
                    }
                }
                checkButton();
            }
            case "Submit" -> {
                if (selectedToggle != null) {
                    String selectedOptionText = ((RadioButton) selectedToggle).getText().toLowerCase().substring(0, 1);
                    yourAnswers[i] = selectedOptionText;
                }
                
                calculateTestScore();
            }
                
            default -> {
                if (i - 1 >= 0) {
                    if (selectedToggle != null) {
                        String selectedOptionText = ((RadioButton) selectedToggle).getText().toLowerCase().substring(0, 1);
                         
                        yourAnswers[i] = selectedOptionText;
                        System.out.println(selectedOptionText);
                    }
                    i -= 1;
                    setQuestion();
                    options.selectToggle(null);
                    setOption(i);
                    nextQuestion.setText("Next");
                }   checkButton();
            }
        }
    }
    
    void setOption(int i) {
        System.out.println(yourAnswers[i]);
        if(optionA.getText().toLowerCase().substring(0, 1).equals(yourAnswers[i])){
                optionA.setSelected(true);
        }else if(optionB.getText().toLowerCase().substring(0, 1).equals(yourAnswers[i])){
            optionB.setSelected(true);
        }else if(optionC.getText().toLowerCase().substring(0, 1).equals(yourAnswers[i])){
            optionC.setSelected(true);
        }else if(optionD.getText().toLowerCase().substring(0, 1).equals(yourAnswers[i])){
            optionD.setSelected(true);
        }
    }
    
    
    public static void setButtonGroupEnabled(ToggleGroup toggleGroup, boolean enabled) {
        toggleGroup.getToggles().forEach(toggle -> {
            Node node = (Node) toggle;
            node.setDisable(!enabled);
        });
    }
    
    private void setQuestion(){
        questionNumber.setText("Question " + (i + 1));
        actualQuestion.setText(questionsList.get(i));
        optionA.setText("A. " + optionAList.get(i));
        optionB.setText("B. " + optionBList.get(i));
        optionC.setText("C. " + optionCList.get(i));
        optionD.setText("D. " + optionDList.get(i));
    }
    
    void checkButton(){
        if(i - 1 < 0){
            previousQuestion.setDisable(true);
        }
        else if(i + 1 > (numberOfQuestions - 1)){
            nextQuestion.setText("Submit");
        }else{
            previousQuestion.setDisable(false);
            nextQuestion.setDisable(false);
        }
    }

    @FXML
    private void closeTestContainer(ActionEvent event) throws ClassNotFoundException, IOException, SQLException {
        Stage currentStage = (Stage) closeQuizButton.getScene().getWindow();
        centerScreen currentScreen = new centerScreen();
        currentStage.close();
        currentScreen.centerFrame(currentStage, "/cbt_ca/student_dashboard/student_page.fxml", firstName, lastName,  matNumber.getText(), level, department,  email);

    }

    @FXML
    private void viewTestScript(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        Stage currentStage = (Stage) closeQuizButton.getScene().getWindow();
        centerScreen currentScreen = new centerScreen();
        currentStage.close();
        currentScreen.centerFrame(currentStage, "/cbt_ca/testScript/testScript.fxml", firstName, lastName,  matNumber, coutseTitle, courseCode, date, questionsList, correctOptionsList, optionAList, optionBList, optionCList, optionDList, yourAnswers);
    }
    
    private void calculateTestScore(){
        if (submitted == false) {
            System.out.println(Arrays.toString(yourAnswers));
            for (int j = 0; j < yourAnswers.length; j++) {
                if (correctOptionsList.get(j).toLowerCase().equals(yourAnswers[j])) {
                    score += 1;
                }
            }
            submitted = true;
            testScoreText.setText(testScoreText.getText() + score);
            setButtonGroupEnabled(options, false);
            try {
                currentConnection.insertTestScriptIntoDatabase(yourAnswers, numberOfQuestions, score, testID, matNumberValue);
            } catch (SQLException ex) {
                Logger.getLogger(ActualTestPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("You've already submitted Niggalicious");
        }
        closeQuizButton.setDisable(true);
        questionBox.setVisible(false);
        testScorePart.setVisible(true);
        timer.stopTimer();
    }

    @FXML
    private void closeTestQuiz(KeyEvent event) throws IOException, ClassNotFoundException, SQLException {
        
    }
    
    
    public class Time implements Runnable {
        private volatile boolean stopTimer = false;
        private Button statButton;
        private Label actualTimeValue;

        public Time(Button statButton, Label actualTimeValue) {
            this.statButton = statButton;
            this.actualTimeValue = actualTimeValue;
        }
        
        @Override
        public void run(){
            long durationTimeMillis = durationTime * 60 * 1000;
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            long testTimestampMillis = startTime.getTime();
            long currentTimestampMillis = currentTimestamp.getTime();
            if(currentTimestampMillis > testTimestampMillis){
                durationTimeMillis = durationTimeMillis - (currentTimestampMillis - testTimestampMillis);
            }
            try{
                while(!stopTimer){
                    currentTimestamp = new Timestamp(System.currentTimeMillis());
                    testTimestampMillis = startTime.getTime();
                    currentTimestampMillis = currentTimestamp.getTime();

                    if (testTimestampMillis > currentTimestampMillis) {
                        LocalTime currentTime = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

                        Platform.runLater(() -> {
                            statButton.setDisable(true);
                            actualTimeValue.setText(currentTime.format(formatter));
                        });
                    } else {
                        if (durationTimeMillis > 0) {
                            started = true;
                            durationTimeMillis -= 1000;
                            long remainingSeconds = durationTimeMillis / 1000;
                            long minutes = remainingSeconds / 60;
                            long seconds = remainingSeconds % 60;

                            Platform.runLater(() -> {
                                statButton.setDisable(false);
                                timeTextLabel.setText("Time Left");
                                closeQuizLabel.setText("Quit Quiz");
                                actualTimeValue.setText(String.format("%02d:%02d", minutes, seconds));
                            });
                        } else {
                            stopTimer = true;
                            Platform.runLater(() -> {
                                calculateTestScore();
                            });
                        }
                    }
                    TimeUnit.SECONDS.sleep(1);
                
                }
                   
            }catch(InterruptedException err){
                System.out.println(err);
            }
        };
        
        public void stopTimer() {
            stopTimer = true;
        }
    }

}