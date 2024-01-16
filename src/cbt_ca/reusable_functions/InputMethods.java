/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class InputMethods {
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    
    public boolean checkStatus(String userEmail, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(userEmail);
        boolean matchFound = matcher.find();
        return matchFound;
    }
    
    public void delayShow(Label label) {
         label.setVisible(true);
        executorService.schedule(() -> {
            label.setVisible(false);
        }, 3, TimeUnit.SECONDS);
    }
    
    public String trimEmail(String email) {
        if (email.endsWith("@pau.edu.ng")) {
            email = email.substring(0, email.length() - "@pau.edu.ng".length());
            return email;
        }else{
            return email;
        }
    }
    public String complete(String email) {
        return email.concat("@pau.edu.ng");
    }
    
    public boolean checkForEmptyInputFields(Map<TextField, Label> buttonPaneMap) {
        boolean meetsRequirements = true;
        for(TextField input : buttonPaneMap.keySet()){
            if(input.getText().isEmpty()){
                meetsRequirements = false;
                delayShow(buttonPaneMap.get(input));
            }
        }
        return meetsRequirements;
    }
    
    public void resetInputFields(Map<TextField, Label> buttonPaneMap) {
        for(TextField input : buttonPaneMap.keySet()){
            input.setText("");
        }
    }
}
