package com.example.unigate;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DataBase.Const;
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
            try {
                loginUser(username, password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }


        });
        password_textfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                login();
            }
        });

        username_textfield.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                login();
            }
        });


    }
    private void login() {
        String username = username_textfield.getText().trim();
        String password = password_textfield.getText().trim();
        try {
            loginUser(username, password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private void loginUser(String username, String password) throws NoSuchAlgorithmException {
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
            Security security=new Security();

            user_check.setPassword(Security.hashPassword(password, Const.SALT));
            PackageData pd = new PackageData("SIGN_IN", user_check);
            Main.connect(pd);

            if (user_check.getUsername().equals(user.getUsername())
                    && user_check.getPassword().equals(user.getPassword())
                    && user.getRole().equals("admin")) {
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("PageHome.fxml"));
                    Main.setscene(root1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert;
                if (!user_check.getUsername().equals(user.getUsername())
                        || !user_check.getPassword().equals(user.getPassword())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Incorrect login or password!");
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("The user does not have access to the admin panel!");
                }
                alert.showAndWait();
            }

        }

    }

}

