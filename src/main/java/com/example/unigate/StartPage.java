package com.example.unigate;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import models.PackageData;
import models.User;

public class StartPage {
    public static User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_textfield;

    @FXML
    private Button sign_in_button;

    @FXML
    private TextField username_textfield;

    @FXML
    public void initialize() throws SQLException {

        sign_in_button.setOnAction(event -> {

            String username, password;
            username = username_textfield.getText().trim();
            password = password_textfield.getText().trim();
            loginUser(username, password);


        });
        password_textfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String username, password;
                username = username_textfield.getText().trim();
                password = password_textfield.getText().trim();
                loginUser(username, password);
            }
        });
        username_textfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                String username, password;
                username = username_textfield.getText().trim();
                password = password_textfield.getText().trim();
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        if (username.equals("") && password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please enter your username and password");
            alert.showAndWait();
        }
        if (!username.equals("") && password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please enter your password!");
            alert.showAndWait();
        }
        if (username.equals("") && !password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Please enter your username!");
            alert.showAndWait();
        }
        if (!username.equals("") && !password.equals("")) {
            User user_check = new User();
            user_check.setUsername(username);
            user_check.setPassword(password);
            PackageData pd = new PackageData("SIGN_IN", user_check);
            Main.connect(pd);

            if (user_check.getUsername().equals(user.getUsername()) && user_check.getPassword().equals(user.getPassword())) {
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("PageHome.fxml"));
                    Main.setscene(root1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Incorrect login or password!");
                alert.showAndWait();
            }
        }

    }

}

