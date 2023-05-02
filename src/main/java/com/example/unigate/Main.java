package com.example.unigate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.PackageData;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main extends Application {

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static Stage window;

    public static void connect(PackageData pd){
        try{
            Socket socket=new Socket("127.0.0.1",3489);
            ObjectOutputStream outputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream=new ObjectInputStream(socket.getInputStream());


            if(pd.getOperationType().equals("SIGN_UP")){
                outputStream.writeObject(pd);
            }
            else if(pd.getOperationType().equals("ADD_DOOR")){
                outputStream.writeObject(pd);
            }
            else if(pd.getOperationType().equals("ADD_LOG")){
                outputStream.writeObject(pd);
            }
            else if(pd.getOperationType().equals("DELETE_DOOR")){
                outputStream.writeObject(pd);
            }
            /*
            else if(pd.getOperationType().equals("LIST_GARAGE")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                Page_garage_controller.orders= infoFromServer.getOrders();
            }
            else if(pd.getOperationType().equals("SHOW_CAR")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                Page_car_controller.car_catalog= infoFromServer.getCar();
            }
            else if(pd.getOperationType().equals("LIST_ORDER")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                Page_orders_controller.orders= infoFromServer.getOrders();
            }
            */
            else if(pd.getOperationType().equals("SIGN_IN")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                StartPage.user= infoFromServer.getUser();
            }
            else if(pd.getOperationType().equals("LIST_USERS")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                UsersPage.users= infoFromServer.getUsersArray();
            }
            else if(pd.getOperationType().equals("LIST_LOGS")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                AccessHistoryPage.logs= infoFromServer.getLogsArray();
            }
            else if(pd.getOperationType().equals("LIST_DOORS")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                DoorsPage.doors= infoFromServer.getDoorsArray();
            }
            else if(pd.getOperationType().equals("UPDATE")){
                outputStream.writeObject(pd);
                UsersPage.user_selected.setPassword(pd.getUser().getPassword());
            }
            else if(pd.getOperationType().equals("DELETE_USER")){
            outputStream.writeObject(pd);
            }

            /*

            else if(pd.getOperationType().equals("BUY")){
                outputStream.writeObject(pd);
            }
            else if(pd.getOperationType().equals("LIST_ORDER_ADMIN")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();
                Page_admin_orders_controller.orders= infoFromServer.getOrders();
            }
            else if(pd.getOperationType().equals("CHANGE_STATUS")){
                outputStream.writeObject(pd);
            }
            else if(pd.getOperationType().equals("DELETE_ORDER")){
                outputStream.writeObject(pd);





            else if(pd.getOperationType().equals("USERNAME_CHECK")){
                outputStream.writeObject(pd);
                PackageData infoFromServer=(PackageData)inputStream.readObject();

                if(infoFromServer.getUser().getUsername().equals("isempty")){
                    Page_signUp_Controller.checkUsername=true;
                }
                else {
                    Page_signUp_Controller.checkUsername=false;
                }
            }
            */




            inputStream.close();
            outputStream.close();
            socket.close();


        }catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Problem connecting to the server!");
            alert.showAndWait();
        }

    }

    @Override
    /*
    public void start(Stage stage) throws IOException {
        window=primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PageStart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("UniGate");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("UniGate");
        InputStream iconStream = getClass().getResourceAsStream("data/logo.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.show();
    }
*/
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("PageStart.fxml"));
        window.setTitle("UniGate");
        InputStream iconStream = getClass().getResourceAsStream("logo.png");
        Image image = new Image(iconStream);
        window.getIcons().add(image);
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);

        window.setScene(scene);
        window.initStyle(StageStyle.DECORATED);

        window.setResizable(false);
        window.show();

    }

    public static void setscene(Parent root) {
        window.setScene(new Scene(root, WIDTH, HEIGHT));

    }

    public static void main(String[] args) {
        launch();

    }
}