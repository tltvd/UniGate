package com.example.unigate;
import java.io.IOException;

import DataBase.Const;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.PackageData;
import models.User;

import java.io.File;
import java.security.NoSuchAlgorithmException;


public class AddUserPage {

    public Boolean photo_check;



    @FXML
    void onPhotoButtonClicked() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter for images only
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files (*.jpg, *.jpeg, *.png)", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(new Stage());

        // If file is selected, set the path to the selected file in the text field
        if (file != null) {
            String path = "C:\\xampp\\htdocs\\dashboard\\unigate\\" + iin_textfield.getText() + "." + getFileExtension(file.getName());
            saveFile(file, path);
            photo_check=true;
        }
    }

    private void saveFile(File file, String path) {
        try {
            java.nio.file.Files.copy(file.toPath(), new java.io.File(path).toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(String name) {
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // Empty extension
        }
        return name.substring(lastIndexOf + 1);
    }



    @FXML
    private ToggleGroup Gender;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_register;

    @FXML
    private TextField email_textfield;

    @FXML
    private RadioButton female_rad_button;

    @FXML
    private TextField firstname_textfield;

    @FXML
    private TextField iin_textfield;

    @FXML
    private TextField lastname_textfield;

    @FXML
    private RadioButton male_rad_button;

    @FXML
    private TextField number_textfield;

    @FXML
    private TextField username_textfield;

    @FXML
    private PasswordField password_textfield;

    @FXML
    private ChoiceBox<String> role_choiceBox;

    @FXML
    private Button btn_photo;

    @FXML
    void initialize() {
        role_choiceBox.getItems().addAll("admin", "teacher", "user", "student");
        btn_cancel.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageUsers.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_register.setOnAction(event -> {
            try {
                signUpNewUser();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
        btn_photo.setOnAction(event -> onPhotoButtonClicked());

    }

    private void signUpNewUser() throws NoSuchAlgorithmException {
        Security security =new Security();
        String password = Security.hashPassword(password_textfield.getText(), Const.SALT);
        String username=username_textfield.getText();
        String firstname=firstname_textfield.getText();
        String lastname=lastname_textfield.getText();
        String id_user=iin_textfield.getText();
        String gender;
        String email=email_textfield.getText();
        String phone=number_textfield.getText();
        if(male_rad_button.isSelected())
            gender="Male";
        else
            gender="Female";

        String role=role_choiceBox.getValue();

        User user=new User( id_user,username,password,firstname,lastname,phone,email,gender,role);

        boolean cheker=check(user);

        PackageData packageData=new PackageData("USERNAME_CHECK",user);
        Main.connect(packageData);
        /*
        else if(checkUsername= false){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("imya zanyato!");
            alert.showAndWait();
        }
        */

        if(!cheker){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please check the information you provided!");
            alert.showAndWait();
        }
        else {
            PackageData pd=new PackageData("SIGN_UP",user);
            Main.connect(pd);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Registration completed!");
            alert.showAndWait();

            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageUsers.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }





    }


    private boolean check(User user){
        boolean res=false;
        if(!user.getUsername().isEmpty() && !user.getPassword().isEmpty() && !user.getFirst_name().isEmpty()
                && !user.getLast_name().isEmpty()  && !user.getId_user().isEmpty() && !user.getPhone().isEmpty()
                && !user.getEmail().isEmpty() && !user.getGender().isEmpty() && !user.getRole().isEmpty() && user.getEmail().contains("@") && photo_check){
            res=true;
        }
        return res;
    }

}
