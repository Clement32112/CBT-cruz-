/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cbt_ca.student_dashboard;

import cbt_ca.components.upcoming_test.upcomingTestsFunctions;
import cbt_ca.reusable_functions.centerScreen;
import cbt_ca.reusable_functions.databaseConnection;
import cbt_ca.reusable_functions.imageUpload;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.sql.Time;
import java.sql.Timestamp;
import javafx.scene.control.Button;

import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author HP PROBOOK 430 G3
 */
public class Student_pageController implements Initializable {
    
    private String firstName;
    private String lastName;
    private String matNumber;
    private String level;
    private String department;
    private String emailAddressValue;
    
    private String IDForTest;
    private String nameOfTest;
    private Timestamp dateOfTest;
    private Integer durationOfTest;
    private Integer testNumberOfQuestions;
    private String courseName;
    private String courseCode;
    private String courseID;
    private final databaseConnection currentConnection;
    private final uniquesStudentMethods uniquestudentmethods;
    imageUpload imageChooser = new imageUpload();
    
    private final Map<HBox, BorderPane> buttonPaneMap = new HashMap<>();

    @FXML
    private BorderPane studentFormContainerBorderPane;
    @FXML
    private StackPane userImage;
    @FXML
    private Circle profile_photo;
    @FXML
    private Label fullName;
    @FXML
    private Label emailAddress;
    @FXML
    private HBox test_button;
    @FXML
    private VBox logOutButton;
    @FXML
    private VBox errorDialogBox;
    @FXML
    private Label errorMessage;
    @FXML
    private VBox successDialogBox;
    @FXML
    private Label succcessMessage;
    private Label userGreetings;
    @FXML
    private BorderPane testBorderPane;
    private final upcomingTestsFunctions currentTestFunction;
    @FXML
    private ScrollPane upcomingTestsScrollPane;
    @FXML
    private VBox upcomingTestsContainer;
    @FXML
    private Label noTestNotification;
    @FXML
    private VBox startTest;
    @FXML
    private Label errorMessage1;

    /**
     * Initializes the controller class.
     */
    
    public Student_pageController() throws ClassNotFoundException, SQLException {
        this.currentConnection = new databaseConnection();
        this.uniquestudentmethods = new uniquesStudentMethods();
        this.currentTestFunction = new upcomingTestsFunctions();
    }
    
    public void setData(HashMap<String, String> data) throws SQLException, IOException{
        firstName = data.get("firstName");
        lastName = data.get("lastName");
        matNumber = data.get("matNumber");
        level = data.get("level");
        department = data.get("department");
        emailAddressValue = data.get("email");
        fullName.setText(firstName + " " + lastName);
        emailAddress.setText(emailAddressValue);
        currentTestFunction.initializeUpcomingTests(department, level, noTestNotification, upcomingTestsScrollPane, upcomingTestsContainer, this);
        try {
            Image profileImage = currentConnection.imageDownload("student_details", emailAddressValue);
            if(profileImage == null){ 
                profileImage = new Image(getClass().getResource("../images/userIcon.png").toExternalForm(), 150, 150, true, true);
            }
            profile_photo.setFill(new ImagePattern(profileImage));
        } catch (SQLException ex) {
            Logger.getLogger(Student_pageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openSetTestPage(String testID, String testName, Timestamp testDate, Integer testDuration, Integer numberOfQuestions, String departmentForTest, String levelForTest, String courseNameForTest, String courseCodeForTest, Integer state, String courseIDForTest) throws SQLException{
        IDForTest = testID;
        nameOfTest = testName;
        dateOfTest = testDate;
        durationOfTest = testDuration;
        testNumberOfQuestions = numberOfQuestions;
        department = departmentForTest;
        level = levelForTest;
        courseName = courseNameForTest;
        courseCode = courseCodeForTest;
        courseID = courseIDForTest;
        
        if (null == state){
            startTest.setVisible(true);
        }else switch (state) {
            case 1 -> {
                errorMessage.setText("Too late to take test");
                errorDialogBox.setVisible(true);
                studentFormContainerBorderPane.setDisable(true);
            }

            case -1 -> {
                errorMessage.setText("Too early to take test");
                errorDialogBox.setVisible(true);
                studentFormContainerBorderPane.setDisable(true);
            }
            default -> {
                ResultSet rs = currentConnection.checkIfTestHasBeenTakenExists(IDForTest, matNumber);
                if (rs.next()){
                    errorMessage.setText("You have taken this test before");
                    errorDialogBox.setVisible(true);
                    studentFormContainerBorderPane.setDisable(true);
                }else{
                    startTest.setVisible(true);
                    studentFormContainerBorderPane.setDisable(true);
                }
                
            }
        }
                
                

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uniquestudentmethods.initializeButtonPaneMap(buttonPaneMap, test_button, testBorderPane);
    }    

    @FXML
    private void setProfilePicture(MouseEvent event) throws ClassNotFoundException, SQLException {
        try {
            imageChooser.uploadFile(userImage, profile_photo, "student_details", emailAddress.getText());
        } catch (IOException ex) {
            Logger.getLogger(Student_pageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void togglePage(MouseEvent event) {
        for(HBox button : buttonPaneMap.keySet()){
            button.getStyleClass().remove("selected");
            buttonPaneMap.get(button).setVisible(false);
            if(event.getSource() == button){
                button.getStyleClass().add("selected");
                buttonPaneMap.get(button).setVisible(true);
            }
            
        }
    }

    @FXML
    private void logOutUser(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) logOutButton.getScene().getWindow();
        currentStage.close();
        centerScreen currentScreen = new centerScreen();
        currentScreen.centerFrame(currentStage, "/cbt_ca/login/login.fxml");
    }

    @FXML
    private void closeErrorDialogBox(ActionEvent event) {
        successDialogBox.setVisible(false);
        errorDialogBox.setVisible(false);
        studentFormContainerBorderPane.setDisable(false);
    }

    @FXML
    private void confirmTestSelection(ActionEvent event) throws IOException, SQLException {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();
        System.out.println(buttonText);
        if("no".equals(buttonText.toLowerCase())){
            startTest.setVisible(false);
            studentFormContainerBorderPane.setDisable(false);
        }else{
            Stage currentStage = (Stage) clickedButton.getScene().getWindow();
            centerScreen currentScreen = new centerScreen();
            currentStage.close();
            currentScreen.centerFrame(currentStage, "/cbt_ca/testPage/actualTestPage.fxml", firstName, lastName, matNumber, level,department, emailAddressValue, IDForTest, nameOfTest, dateOfTest, durationOfTest, testNumberOfQuestions, courseName, courseCode, courseID);
        }
    }
    
}
