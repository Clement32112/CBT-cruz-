/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class imageUpload {
    public boolean uploadFile(StackPane userImage, Circle profile_photo, String tableType, String emailAddress) throws ClassNotFoundException, SQLException, IOException{
        Stage currentStage = (Stage) userImage.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload new profile photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JPEG Images", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG Images", "*.png"),
            new FileChooser.ExtensionFilter("All images", "*.jpg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(currentStage);

        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            Image image = new Image("file:" + imagePath, 150, 150, true, true);
            profile_photo.setFill(new ImagePattern(image));
            databaseConnection currentConnection = new databaseConnection();
            currentConnection.uploadImage(image, tableType, emailAddress);
        } else {
            System.out.println("No selected file");
        }
        return false;
    }
}
