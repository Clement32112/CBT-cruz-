/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.models;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class credentials {
    private String staffID;
    private String password;

    public credentials(String staffID, String password) {
        this.staffID = staffID;
        this.password = password;
    }

    public String getStaffID() {
        return staffID;
    }

    public String getPassword() {
        return password;
    }

}
