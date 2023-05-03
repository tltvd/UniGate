package com.example.unigate;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.PackageData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

import static com.example.unigate.DoorsPage.door_selected;

public class DoorDetailPage {


    @FXML
    private Button btn_schedule;

    @FXML
    private Button btn_cancel;

    @FXML
    private Button btn_delete;

    @FXML
    private Button btn_update;

    @FXML
    private Label id_label;

    @FXML
    private TextField ip_textfield;

    @FXML
    private TextField location_textfield;

    @FXML
    private TextField name_textfield;

    @FXML
    private Label status;

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
        ip_textfield.setText(door_selected.getIpv4());
        name_textfield.setText(door_selected.getName());
        location_textfield.setText(door_selected.getLocation());
        id_label.setText(door_selected.getId_room());

        btn_delete.setOnAction(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure you want to delete this element?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                PackageData pd=new PackageData("DELETE_DOOR",door_selected);
                Main.connect(pd);
                try {
                    Parent root2 = FXMLLoader.load(getClass().getResource("PageDoors.fxml"));
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

        String ip =door_selected.getIpv4();

        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, 80), 1000);
            Platform.runLater(() -> {
                status.setText("Available");
                status.setStyle("-fx-background-color: green");
            });
        } catch (IOException e) {
            Platform.runLater(() -> {
                status.setText("Error");
                status.setStyle("-fx-background-color: red");
            });
        }
        btn_cancel.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageDoors.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_schedule.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageSchedule.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });






    }

}
