package com.example.unigate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Door;
import models.Log;
import models.PackageData;

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
        btn_add.setOnAction(event -> add_door());
    }

    private void add_door() {
        String name = doorname_textfield.getText();
        String location =doorLocation_textfield.getText();
        String ipv4 = ip_textfield.getText();


        Door door=new Door(name,location,ipv4);


        boolean cheker=check(door);
        if(!cheker){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please check the information you provided!");
            alert.showAndWait();
        }
        else {
            PackageData pd=new PackageData("ADD_DOOR",door);
            Main.connect(pd);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Door Registration completed!");
            alert.showAndWait();
            try {
                Parent root1 = FXMLLoader.load(getClass().getResource("PageDoors.fxml"));
                Main.setscene(root1);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }
    private boolean check(Door door){
        boolean res=false;
        if(!door.getIpv4().isEmpty() && !door.getLocation().isEmpty() && !door.getName().isEmpty() && isValidIpAddress(door.getIpv4())){
            res=true;
        }
        return res;
    }
    public static boolean isValidIpAddress(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return false;
        }

        String[] octets = ipAddress.split("\\.");

        if (octets.length != 4) {
            return false;
        }

        try {
            for (String octet : octets) {
                int octetValue = Integer.parseInt(octet);
                if (octetValue < 0 || octetValue > 255) {
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }


}
