/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package cbt_ca;

import javafx.application.Application;
import javafx.stage.Stage;
import cbt_ca.reusable_functions.centerScreen;
/**
 *
 * @author HP PROBOOK 430 G3
 */
public class CBT_CA extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        centerScreen screenInstance = new centerScreen();
        screenInstance.centerFrame(stage, "/cbt_ca/login/login.fxml");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
