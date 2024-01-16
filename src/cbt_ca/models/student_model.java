package cbt_ca.models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import java.time.LocalDate;
import javafx.beans.property.StringProperty;


public class student_model {
  private SimpleStringProperty firstName;
  private SimpleStringProperty lastName;
  private SimpleStringProperty phoneNumber;
  private SimpleStringProperty matNumber;
  private ObjectProperty<LocalDate> dateOfBirth;
  private SimpleStringProperty gender;
  private SimpleStringProperty level;
  private SimpleStringProperty department;
  private SimpleStringProperty email;

  public student_model (String firstName, String lastName, String phoneNumber, String matNumber, LocalDate birthDate, String gender, String level, String department, String email) {
      this.firstName = new SimpleStringProperty(firstName);
      this.lastName = new SimpleStringProperty(lastName);
      this.phoneNumber = new SimpleStringProperty(phoneNumber);
      this.matNumber = new SimpleStringProperty(matNumber);
      this.dateOfBirth = new SimpleObjectProperty<>(birthDate);
      this.gender = new SimpleStringProperty(gender);
      this.level = new SimpleStringProperty(level);
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

  public String getMatNumber() {
      return matNumber.get();
  }

  public void setMatNumber(String matNumber) {
      this.matNumber.set(matNumber);
  }

  public StringProperty matNumberProperty() {
      return matNumber;
  }

  public LocalDate getBirthDate() {
      return dateOfBirth.get();
  }

  public void setBirthDate(LocalDate birthDate) {
      this.dateOfBirth.set(birthDate);
  }

  public ObjectProperty<LocalDate> birthDateProperty() {
      return dateOfBirth;
  }

  public String getGender() {
      return gender.get();
  }

  public void setGender(String gender) {
      this.gender.set(gender);
  }

  public StringProperty genderProperty() {
      return gender;
  }

  public String getLevel() {
      return level.get();
  }

  public void setLevel(String level) {
      this.level.set(level);
  }

  public StringProperty levelProperty() {
      return level;
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
