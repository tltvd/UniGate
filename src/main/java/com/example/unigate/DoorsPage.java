package com.example.unigate;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import models.Door;
import models.Log;
import models.PackageData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;

public class DoorsPage {

    public static Door door_selected;

    public static ArrayList<Door> doors;
    @FXML
    private TableColumn<Door, Void> tableColumn_status;

    @FXML
    private TableColumn<Door, Void> tableColumn_qr;

    @FXML
    private TableColumn<Door, Void> tableColumn_action;

    @FXML
    private Button btn_addDoor;

    @FXML
    private Button btn_back;

    @FXML
    private TableColumn<Door, String> tableColumn_doorID;

    @FXML
    private TableColumn<Door, String> tableColumn_doorIpaddr;

    @FXML
    private TableColumn<Door, String> tableColumn_doorLocation;

    @FXML
    private TableColumn<Door, String> tableColumn_doorName;


    @FXML
    private TableView<Door> table_doors;
    private ObservableList<Door> list;

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
        btn_addDoor.setOnAction(event -> {
            try {
                Parent root2 = FXMLLoader.load(getClass().getResource("PageAddDoor.fxml"));
                Main.setscene(root2);
                Main.window.centerOnScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        PackageData dp = new PackageData("LIST_DOORS");
        Main.connect(dp);
        list = FXCollections.observableArrayList(doors);
        populateTableview();

        // Add Details button to Action column
        tableColumn_action.setCellFactory(param -> new TableCell<>() {
            private final Button detailsButton = new Button("OPEN");

            {
                detailsButton.setOnAction(event -> {
                    door_selected = getTableView().getItems().get(getIndex());
                    try {
                        String str = door_selected.getIpv4();
                        sendGetRequest("http://" + str + "/cgi-bin/command", "z5rweb", "C59C8BEE");
                        accessLogsAdd(door_selected);
                        // сообщение об успешном выполнении
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Успешно");
                        alert.setContentText("Команда успешно выполнена");
                        alert.showAndWait();
                    } catch (Exception e) {
                        // сообщение об ошибке
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Ошибка");
                        alert.setContentText("Произошла ошибка при выполнении команды");
                        alert.showAndWait();
                        throw new RuntimeException(e);
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
        tableColumn_qr.setCellFactory(param -> new TableCell<>() {
            private final Button qrButton = new Button("QR Generate");

            {
                qrButton.setOnAction(event -> {
                    door_selected = getTableView().getItems().get(getIndex());
                    try {
                        QRCodeWriter qrCodeWriter = new QRCodeWriter();
                        String qrCodeText = door_selected.getLocation();
                        int width = 800;
                        int height = 800;
                        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText,BarcodeFormat.QR_CODE,width,height);
                        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save QR Code Image");
                        fileChooser.setInitialFileName(door_selected.getLocation().trim() + "_door_qr_code.png");
                        File selectedFile = fileChooser.showSaveDialog(Main.window);

                        if (selectedFile != null) {
                            ImageIO.write(qrCodeImage, "png", selectedFile);
                        }

                    } catch (IOException | WriterException e) {
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
                    setGraphic(qrButton);
                }
            }
        });

        /*
        tableColumn_status.setCellFactory(param -> new TableCell<>() {

            private final Label statusLabel = new Label();
            {
                //door_selected = getTableView().getItems().get(getIndex());
                //String ip =door_selected.getIpv4();
                String ip="192.168.88.95";
                try {
                    Process p = Runtime.getRuntime().exec("ping " + ip);
                    int result = p.waitFor();
                    if (result == 0) {
                        statusLabel.setText("Available");
                        statusLabel.setStyle("-fx-background-color: green");
                    } else {
                        statusLabel.setText("Error");
                        statusLabel.setStyle("-fx-background-color: red");
                    }
                } catch (IOException | InterruptedException e) {
                    statusLabel.setText("Error");
                    statusLabel.setStyle("-fx-background-color: red");
                }

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(statusLabel);
                }
            }
        });
*/

    }

    private void populateTableview() {
        tableColumn_doorID.setCellValueFactory(new PropertyValueFactory<>("id_room"));
        tableColumn_doorIpaddr.setCellValueFactory(new PropertyValueFactory<>("ipv4"));
        tableColumn_doorLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        tableColumn_doorName.setCellValueFactory(new PropertyValueFactory<>("name"));
        table_doors.setItems(list);
    }

    public static void sendGetRequest(String url, String username, String password) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // добавление заголовка Authorization
        String userCredentials = username + ":" + password;
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(userCredentials.getBytes());
        con.setRequestProperty("Authorization", basicAuth);

        // установка типа запроса
        con.setRequestMethod("POST");

        // получение ответа
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
    }

    private void accessLogsAdd(Door door) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String str = String.valueOf(currentDateTime);
        Log log = new Log(door.getId_room(), StartPage.user.getId_user(), str, "YES");
        PackageData packageData = new PackageData("ADD_LOG", log);
        Main.connect(packageData);
    }
}
