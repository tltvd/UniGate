package com.example.unigate;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import models.Door;
import models.PackageData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        btn_schedule.setOnAction(event -> {
            PackageData dp = new PackageData("LIST_DOORS");
            Main.connect(dp);

            List<Door> doors = DoorsPage.doors;
            List<String> doorStrings = new ArrayList<>();
            for (Door door : doors) {
                String doorString = "ID:"+door.getId_room() + " Location:" + door.getLocation() + " Name:" + door.getName();
                doorStrings.add(doorString);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>(doorStrings.get(0), doorStrings);
            dialog.setTitle("Door Selection");
            dialog.setHeaderText("Please select a door");
            dialog.setContentText("Door:");

// Получение панели диалога и установка стиля
            dialog.getDialogPane().getStylesheets().add(getClass().getResource("data/Style.css").toExternalForm());

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String selectedDoorString = result.get();
                for (Door door : doors) {
                    String doorString = "ID:"+door.getId_room() + " Location:" + door.getLocation() + " Name:" + door.getName();
                    if (doorString.equals(selectedDoorString)) {
                        DoorsPage.door_selected = door;
                        break;
                    }
                }
                try {
                    Parent root2 = FXMLLoader.load(getClass().getResource("PageSchedule.fxml"));
                    Main.setscene(root2);
                    Main.window.centerOnScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });



    }

}

