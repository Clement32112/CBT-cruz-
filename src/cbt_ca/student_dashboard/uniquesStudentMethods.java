/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.student_dashboard;

import java.util.Map;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class uniquesStudentMethods {
    public void initializeButtonPaneMap(
        Map<HBox, BorderPane> buttonPaneMap,
        HBox test_button,
        BorderPane testBorderPane
        
    ) {
        
        
        buttonPaneMap.put(test_button, testBorderPane);
    }
}
