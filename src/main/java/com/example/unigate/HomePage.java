package com.example.unigate;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomePage {

    @FXML
    private Button btn_doors;

    @FXML
    private Button btn_logOut;

    @FXML
    private Button btn_logs;

    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_users;

    @FXML
    void initialize() {
        btn_logOut.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageStart.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        btn_users.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageUsers.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_doors.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageDoors.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_logs.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageLogs.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }

}

