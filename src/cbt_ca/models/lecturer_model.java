/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class lecturer_model {
  private SimpleStringProperty firstName;
  private SimpleStringProperty lastName;
  private SimpleStringProperty phoneNumber;
  private SimpleStringProperty department;
  private SimpleStringProperty email;

  public lecturer_model (String firstName, String lastName, String phoneNumber, String department, String email) {
      this.firstName = new SimpleStringProperty(firstName);
      this.lastName = new SimpleStringProperty(lastName);
      this.phoneNumber = new SimpleStringProperty(phoneNumber);
      this.department = new SimpleStringProperty(department);
      this.email = new SimpleStringProperty(email);
  }

  // Getters and setters for each property
  public String getFirstName() {
      return firstName.get();
  }

  public void setFirstName(String firstName) {
      this.firstName.set(firstName);
  }

  public StringProperty firstNameProperty() {
      return firstName;
  }

  public String getLastName() {
      return lastName.get();
  }

  public void setLastName(String lastName) {
      this.lastName.set(lastName);
  }

  public StringProperty lastNameProperty() {
      return lastName;
  }

  public String getPhoneNumber() {
      return phoneNumber.get();
  }

  public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber.set(phoneNumber);
  }

  public StringProperty phoneNumberProperty() {
      return phoneNumber;
  }

  public String getDepartment() {
      return department.get();
  }

  public void setDepartment(String department) {
      this.department.set(department);
  }

  public StringProperty departmentProperty() {
      return department;
  }

  public String getEmail() {
      return email.get();
  }

  public void setEmail(String email) {
      this.email.set(email);
  }

  public StringProperty emailProperty() {
      return email;
  }
}
