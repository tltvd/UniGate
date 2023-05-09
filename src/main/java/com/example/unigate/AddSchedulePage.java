package com.example.unigate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import com.example.unigate.models.PackageData;
import com.example.unigate.models.Schedule;
import com.example.unigate.models.User;

import java.io.IOException;
import java.sql.Time;

import static com.example.unigate.DoorsPage.door_selected;

public class AddSchedulePage {

    public static String selectedUserId;
    @FXML
    private TextField description_textfield;

    @FXML
    private ChoiceBox<String> box_day;

    @FXML
    private ChoiceBox<String> box_room;

    @FXML
    private Label label_room;

    @FXML
    private ChoiceBox<String> box_user;

    @FXML
    private Button btn_add;

    @FXML
    private Button btn_cancel;

    @FXML
    private Spinner<Integer> end_h;

    @FXML
    private Spinner<Integer> end_m;

    @FXML
    private Spinner<Integer> start_h;

    @FXML
    private Spinner<Integer> start_m;

    @FXML
    void initialize() {
        box_day.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        label_room.setText(door_selected.getLocation());
        PackageData dp = new PackageData("LIST_USERS");
        Main.connect(dp);
        ObservableList<String> userNames = FXCollections.observableArrayList();
        for (User user : UsersPage.users) {
            userNames.add(user.getId_user() + " " + user.getFirst_name() + " " + user.getLast_name());

        }
        box_user.setItems(userNames);

        btn_cancel.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageSchedule.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        btn_add.setOnAction(event -> {
            add_schedule();
        });
    }


    private void add_schedule() {
        String day = box_day.getValue();
        String id_room = door_selected.getId_room();
        String id_user = box_user.getValue().split(" ")[0];

        String access_description = description_textfield.getText();

        int startHour = start_h.getValue();
        int startMinute = start_m.getValue();
        Time start_time = Time.valueOf(String.format("%02d:%02d:00", startHour, startMinute));

        int endHour = end_h.getValue();
        int endMinute = end_m.getValue();
        Time end_time = Time.valueOf(String.format("%02d:%02d:00", endHour, endMinute));


        Schedule schedule = new Schedule(day, id_room, id_user, start_time, end_time, access_description);


        PackageData pd = new PackageData("ADD_SCHEDULE", schedule);
        Main.connect(pd);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Door Registration completed!");
        alert.showAndWait();


    }
}


