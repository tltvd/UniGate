package com.example.unigate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddDoorPage {

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_cancel;

    @FXML
    private TextField doorLocation_textfield;

    @FXML
    private TextField doorname_textfield;

    @FXML
    private TextField ip_textfield;

    @FXML
    void initialize() {
        btn_cancel.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageDoors.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
