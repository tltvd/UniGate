package com.example.unigate;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.PackageData;
import models.Schedule;
import models.User;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.unigate.DoorsPage.door_selected;

public class DoorSchedulePage {
    public static ArrayList<Schedule> schedules;
    public static ArrayList<User> users;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_back;

    @FXML
    private Label name_label;

    @FXML
    private Button btn_delete;

    @FXML
    private TableColumn<Schedule, String> tableColumn_day;

    @FXML
    private TableColumn<Schedule, String> tableColumn_description;

    @FXML
    private TableColumn<Schedule, String> tableColumn_end;

    @FXML
    private TableColumn<Schedule, String> tableColumn_start;

    @FXML
    private TableColumn<Schedule, String> tableColumn_userFIO;

    @FXML
    private TableColumn<Schedule, String> tableColumn_userID;


    @FXML
    private TableView<Schedule> table_schedule;

    private ObservableList<Schedule> list;

    @FXML
    void initialize() {
        btn_delete.setOnAction(event -> {
            Schedule selectedSchedule = table_schedule.getSelectionModel().getSelectedItem();
            if (selectedSchedule != null) {
                User user = getUserById(selectedSchedule.getId_user());
                String message = String.format("Вы точно хотите удалить доступ пользователя %s к двери %s с %s по %s?",
                        user.getFirst_name() + " " + user.getLast_name(),
                        door_selected.getLocation(),
                        selectedSchedule.getStart_time(),
                        selectedSchedule.getEnd_time());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    System.out.println("DELETE COMPLETE");
                    //deleteSchedule(selectedSchedule);
                }
            }
        });

        btn_back.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageDoors.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btn_add.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageAddSchedule.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        name_label.setText(door_selected.getLocation());

        PackageData dp = new PackageData("LIST_SCHEDULE", door_selected);
        Main.connect(dp);
        list = FXCollections.observableArrayList(schedules);


        PackageData dp1 = new PackageData("LIST_USERS");
        Main.connect(dp1);
        populateTableView();
        users= UsersPage.users;


    }

    public User getUserById(String userId) {
        for (User user : users) {
            if (user.getId_user().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private void populateTableView() {
        tableColumn_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableColumn_userID.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        tableColumn_description.setCellValueFactory(new PropertyValueFactory<>("access_description"));
        tableColumn_start.setCellValueFactory(new PropertyValueFactory<>("start_time"));
        tableColumn_end.setCellValueFactory(new PropertyValueFactory<>("end_time"));
        tableColumn_userFIO.setCellValueFactory(cellData -> {
            Schedule schedule = cellData.getValue();
            for (User user : users) {
                if (user.getId_user().equals(schedule.getId_user())) {
                    return new SimpleStringProperty(user.getFirst_name() + " " + user.getLast_name());
                }
            }
            return null;
        });
        table_schedule.setItems(list);
    }


}