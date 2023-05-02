package com.example.unigate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Door;
import models.Log;
import models.PackageData;
import models.User;

import java.io.IOException;
import java.util.ArrayList;

public class AccessHistoryPage {

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<Log,String> tableColumn_accessId;

    @FXML
    private TableColumn<Log,String> tableColumn_doorid;

    @FXML
    private TableColumn<Log,String> tableColumn_granted;

    @FXML
    private TableColumn<Log,String> tableColumn_time;

    @FXML
    private TableColumn<Log,String> tableColumn_userId;

    @FXML
    private TableView<Log> table_logs;

    public static ArrayList<Log> logs;
    private ObservableList<Log> list ;

    @FXML
    void initialize() {
        btn_back.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageHome.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        PackageData dp = new PackageData("LIST_LOGS");
        Main.connect(dp);
        list = FXCollections.observableArrayList(logs);
        populateTableview();
    }

    private void populateTableview() {
        tableColumn_accessId.setCellValueFactory(new PropertyValueFactory<>("id_access"));
        tableColumn_doorid.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        tableColumn_granted.setCellValueFactory(new PropertyValueFactory<>("granted"));
        tableColumn_time.setCellValueFactory(new PropertyValueFactory<>("access_time"));
        tableColumn_userId.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        table_logs.setItems(list);
    }

}

