package com.example.unigate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.PackageData;
import models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static com.example.unigate.UsersPage.user_selected;


public class UserDetailPage {


    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_change;

    @FXML
    private PasswordField field_newPassword;

    @FXML
    private PasswordField field_oldPassword;

    @FXML
    private Label label_Gender;

    @FXML
    private Label label_birthdate;

    @FXML
    private Label label_email;

    @FXML
    private Label label_firstName;

    @FXML
    private Label label_iin;

    @FXML
    private Label label_lastName;

    @FXML
    private Label label_phoneNumber;

    @FXML
    private Label label_role;

    @FXML
    private Label label_username;

    @FXML
    private ImageView image_avatar;

    @FXML
    public void initialize() throws SQLException, FileNotFoundException {

        String id_user = user_selected.getId_user();
        String imagePath = "C:\\xampp\\htdocs\\dashboard\\unigate\\" + id_user;

// проверяем наличие файла
        File imageFile = new File(imagePath + ".png");
        if (!imageFile.exists()) {
            imageFile = new File(imagePath + ".jpg");
            if (!imageFile.exists()) {
                imageFile = new File(imagePath + ".jpeg");
                if (!imageFile.exists()) {
                    // файл не найден
                    System.out.println("Image not found");
                    return;
                }
            }
        }

// загружаем картинку
        Image image = new Image(new FileInputStream(imageFile));
        image_avatar.setImage(image);


        label_lastName.setText(user_selected.getLast_name());
        label_firstName.setText(user_selected.getFirst_name());
        label_iin.setText(user_selected.getId_user());
        label_email.setText(user_selected.getEmail());
        label_phoneNumber.setText(user_selected.getPhone());
        label_username.setText(user_selected.getUsername());
        label_birthdate.setText(user_selected.getBirthdate());
        label_Gender.setText(user_selected.getGender());
        label_role.setText(user_selected.getRole());


        btn_cancel.setOnAction(event -> {
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("PageUsers.fxml"));
                Main.setscene(root1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_change.setOnAction(event -> {
            if ((!field_newPassword.getText().equals("") && !field_oldPassword.getText().equals(""))) {
                User userUpdate = new User();
                if (field_oldPassword.getText().equals(user_selected.getPassword())) {
                    userUpdate.setId_user(user_selected.getId_user());
                    userUpdate.setPassword(field_newPassword.getText().trim());
                    PackageData pd = new PackageData(userUpdate);
                    pd.setOperationType("UPDATE");
                    Main.connect(pd);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successfully!");
                    alert.setHeaderText("Your password has been successfully changed.");
                    alert.showAndWait();
                    field_oldPassword.setText("");
                    field_newPassword.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("ERROR!");
                    alert.showAndWait();
                }

            }
        });

    }

}
