package com.example.unigate;

import com.example.unigate.DataBase.Const;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.unigate.models.PackageData;
import com.example.unigate.models.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static com.example.unigate.UsersPage.user_selected;


public class UserDetailPage {


    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_delete;

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
        Security security =new Security();


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
                try {
                    if (Security.hashPassword(field_oldPassword.getText(), Const.SALT).equals(user_selected.getPassword())) {
                        userUpdate.setId_user(user_selected.getId_user());
                        userUpdate.setPassword(Security.hashPassword(field_oldPassword.getText(), Const.SALT).trim());
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
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error occurred while updating your password.");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Please fill in both password fields.");
                alert.showAndWait();
            }
        });


        btn_delete.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure you want to delete this element?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                PackageData pd=new PackageData("DELETE_USER",user_selected);
                Main.connect(pd);
                try {
                    Parent root2 = FXMLLoader.load(getClass().getResource("PageUsers.fxml"));
                    Main.setscene(root2);
                    Main.window.centerOnScreen();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Information Dialog");
                    alert1.setHeaderText("Deleting completed!");
                    alert1.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {}



        });

    }

}
