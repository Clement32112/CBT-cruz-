/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import java.util.prefs.Preferences;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class userPreferences {

    private final String EMAIL_KEY = "user_email";
    private final String REMEMBER_KEY = "remember_state";

    public void setEmail(String email) {
        Preferences prefs = Preferences.userNodeForPackage(userPreferences.class);
        prefs.put(EMAIL_KEY, email);
    }

    public String getSavedEmail() {
        Preferences prefs = Preferences.userNodeForPackage(userPreferences.class);
        return prefs.get(EMAIL_KEY, null);
    }

    public void removeSavedEmail() {
        Preferences prefs = Preferences.userNodeForPackage(userPreferences.class);
        prefs.remove(EMAIL_KEY);
    }
    
    public void setStatus(boolean state) {
        Preferences prefs = Preferences.userNodeForPackage(userPreferences.class);
        prefs.putBoolean(REMEMBER_KEY, state);
    }
    
    public Boolean getStatus() {
        Preferences prefs = Preferences.userNodeForPackage(userPreferences.class);
        return prefs.getBoolean(REMEMBER_KEY, false);
    }
}