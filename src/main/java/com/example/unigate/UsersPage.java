package com.example.unigate;

import com.example.unigate.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.PackageData;
import models.User;

import java.io.IOException;
import java.util.ArrayList;

public class UsersPage {

    public static User user_selected;

    public static ArrayList<User> users;

    @FXML
    private Button btn_addUser;

    @FXML
    private Button btn_back;
    @FXML
    private TableColumn<User, Void> tableColumn_action;
    @FXML
    private TableColumn<User, String> tableColumn_email;

    @FXML
    private TableColumn<User, String> tableColumn_firstName;

    @FXML
    private TableColumn<User, String> tableColumn_lastName;
    @FXML
    private TableColumn<User, String> tableColumn_phone;

    @FXML
    private TableColumn<User, String> tableColumn_userID;

    @FXML
    private TableView<User> table_users;

    private ObservableList<User> list;

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
        btn_addUser.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageAddUser.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        PackageData dp = new PackageData("LIST_USERS");
        Main.connect(dp);
        list = FXCollections.observableArrayList(users);
        populateTableview();

        // Add Details button to Action column
        tableColumn_action.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("Details");

            {
                detailsButton.setOnAction(event -> {
                    user_selected = getTableView().getItems().get(getIndex());
                    try {
                        Parent root2 = FXMLLoader.load(getClass().getResource("PageUserDetail.fxml"));
                        Main.setscene(root2);


                        Main.window.centerOnScreen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(detailsButton);
                }
            }
        });
    }

    private void populateTableview() {
        tableColumn_firstName.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        tableColumn_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumn_lastName.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        tableColumn_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumn_userID.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        table_users.setItems(list);
    }


}


