/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package cbt_ca.login;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import cbt_ca.reusable_functions.centerScreen;
import cbt_ca.reusable_functions.userPreferences;
import cbt_ca.reusable_functions.databaseConnection;
import cbt_ca.reusable_functions.InputMethods;
import cbt_ca.reusable_functions.PasswordHashing;
import java.sql.ResultSet;
import javafx.scene.control.CheckBox;
/**
 *
 * @author HP PROBOOK 430 G3
 */
public class LoginController implements Initializable {
    
    @FXML
    private Button passwordToggler, submitButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField textPassword, userEmail;
    @FXML
    private Label passwordWarning, emailWarning;
    @FXML
    private FontAwesomeIconView passwordIcon;
    @FXML
    private ProgressIndicator progressBar;
    @FXML
    private BorderPane formContainer;
    @FXML
    private VBox errorDialogBox;
    @FXML
    private CheckBox saveState;
    
    private final userPreferences currentPreferences = new userPreferences();
    private final InputMethods currentInputMethods = new InputMethods();
    private final databaseConnection currentConnection;

    public LoginController() throws ClassNotFoundException, SQLException {
        this.currentConnection = new databaseConnection();
    }

    
    @FXML
    private void togglePassword(ActionEvent event) {
        textPassword.textProperty().bindBidirectional(passwordField.textProperty());
        passwordField.setVisible(!passwordField.isVisible());
        textPassword.setVisible(!textPassword.isVisible());
        if(!passwordField.isVisible()){
            passwordIcon.setGlyphName("EYE");
        }else{
            passwordIcon.setGlyphName("EYE_SLASH");
        }
    }
    
    @FXML
    private void handleLogin(ActionEvent event) throws SQLException, Exception {
        if(userEmail.getText().isEmpty()){
            currentInputMethods.delayShow(emailWarning);
        }else if(passwordField.getText().isEmpty()){
            currentInputMethods.delayShow(passwordWarning);
        }else{
            String email = userEmail.getText().toLowerCase();
            email = currentInputMethods.trimEmail(email);
            email = currentInputMethods.complete(email);
            if(currentInputMethods.checkStatus(email, "^[a-zA-Z-]+\\.[a-zA-Z-]+@pau\\.edu\\.ng$")){
                try{
                    progressBar.setVisible(true);
                    formContainer.setDisable(true);
                    ResultSet rs = currentConnection.checkIfUserExists(email, "SELECT * FROM student_details WHERE email = ?");
                    if(rs.next()){
                        String storedHash = rs.getString("password");
                        String storedSalt = rs.getString("salt");
                        System.out.println(storedSalt);
                        boolean isPasswordCorrect = PasswordHashing.checkPassword(passwordField.getText(), storedSalt, storedHash);

                        if (isPasswordCorrect) {
                            try {
                                if(saveState.isSelected()){
                                    currentPreferences.setEmail(currentInputMethods.trimEmail(email));
                                    currentPreferences.setStatus(true);
                                }else{
                                    currentPreferences.setEmail("");
                                    currentPreferences.setStatus(false);
                                }
                                Stage currentStage = (Stage) submitButton.getScene().getWindow();
                                centerScreen currentScreen = new centerScreen();
                                currentStage.close();
                                currentScreen.centerFrame(currentStage, "/cbt_ca/student_dashboard/student_page.fxml", rs.getString(1), rs.getString(2),  rs.getString(4), rs.getString(7), rs.getString(8),  rs.getString(10));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            errorDialogBox.setVisible(true);
                            Toolkit.getDefaultToolkit().beep();
                        }
                        
                    }else{
                        errorDialogBox.setVisible(true);
                        Toolkit.getDefaultToolkit().beep();
                    }
                }catch(HeadlessException | ClassNotFoundException | SQLException e){
                    System.out.println(e);
                }finally{
                    progressBar.setVisible(false);
                    formContainer.setDisable(false);
                }

            }else{
                try{
                    progressBar.setVisible(true);
                    formContainer.setDisable(true);
                    ResultSet rs = currentConnection.checkIfUserExists(email, "SELECT * FROM lecturer_details WHERE email = ?");
                    if(rs.next()){
                        String storedHash = rs.getString("password");
                        String storedSalt = rs.getString("salt");
                        System.out.println(storedSalt);
                        boolean isPasswordCorrect = PasswordHashing.checkPassword(passwordField.getText(), storedSalt, storedHash);
                        if(isPasswordCorrect){
                            try {
                                if(saveState.isSelected()){
                                    currentPreferences.setEmail(currentInputMethods.trimEmail(email));
                                    currentPreferences.setStatus(true);
                                }else{
                                    currentPreferences.setEmail("");
                                    currentPreferences.setStatus(false);
                                }
                                Stage currentStage = (Stage) submitButton.getScene().getWindow();
                                centerScreen currentScreen = new centerScreen();
                                currentStage.close();
                                currentScreen.centerFrame(currentStage, "/cbt_ca/admin_dashboard/admin_page.fxml", rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            errorDialogBox.setVisible(true);
                            Toolkit.getDefaultToolkit().beep();
                        }
                        
                    }else{
                        errorDialogBox.setVisible(true);
                        Toolkit.getDefaultToolkit().beep();
                    }
                }catch(HeadlessException | ClassNotFoundException | SQLException e){
                    System.out.println(e);
                }finally{
                    progressBar.setVisible(false);
                    formContainer.setDisable(false);
                }
            }
        }
    }
    
    @FXML
    public void closeErrorDialogBox(ActionEvent event) {
        errorDialogBox.setVisible(false);
        formContainer.setDisable(false);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(currentPreferences.getStatus() == false){
            saveState.setSelected(false);
        }else{
            saveState.setSelected(true);
            userEmail.setText(currentPreferences.getSavedEmail());
        }
    } 
}


