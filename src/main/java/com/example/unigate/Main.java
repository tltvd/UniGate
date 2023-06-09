package com.example.unigate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.example.unigate.models.PackageData;

import javax.net.ssl.*;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Main extends Application {
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;
    public static Stage window;

    public static void connect(PackageData pd) {
        try {
            // Загрузка доверенного сертификата
            InputStream certInputStream = Main.class.getResourceAsStream("/certificate.crt");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(certInputStream);
            certInputStream.close();

            // Создание хранилища ключей и добавление доверенного сертификата с паролем
            char[] password = "iitu2019".toCharArray();
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, password);
            trustStore.setCertificateEntry("server", cert);

            // Создание менеджера доверия и инициализация SSL контекста
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Создание SSL сокета
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket("127.0.0.1", 3489);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());


            if (pd.getOperationType().equals("SIGN_UP")) {
                outputStream.writeObject(pd);
            } else if (pd.getOperationType().equals("ADD_SCHEDULE")) {
                outputStream.writeObject(pd);
            } else if (pd.getOperationType().equals("ADD_DOOR")) {
                outputStream.writeObject(pd);
            } else if (pd.getOperationType().equals("ADD_LOG")) {
                outputStream.writeObject(pd);
            } else if (pd.getOperationType().equals("DELETE_DOOR")) {
                outputStream.writeObject(pd);
            } else if (pd.getOperationType().equals("LIST_SCHEDULE")) {
                outputStream.writeObject(pd);
                PackageData infoFromServer = (PackageData) inputStream.readObject();
                DoorSchedulePage.schedules = infoFromServer.getScheduleArray();
            } else if (pd.getOperationType().equals("SIGN_IN")) {
                outputStream.writeObject(pd);
                PackageData infoFromServer = (PackageData) inputStream.readObject();
                StartPage.user = infoFromServer.getUser();
            } else if (pd.getOperationType().equals("LIST_USERS")) {
                outputStream.writeObject(pd);
                PackageData infoFromServer = (PackageData) inputStream.readObject();
                UsersPage.users = infoFromServer.getUsersArray();
            } else if (pd.getOperationType().equals("LIST_LOGS")) {
                outputStream.writeObject(pd);
                PackageData infoFromServer = (PackageData) inputStream.readObject();
                AccessHistoryPage.logs = infoFromServer.getLogsArray();
            } else if (pd.getOperationType().equals("LIST_DOORS")) {
                outputStream.writeObject(pd);
                PackageData infoFromServer = (PackageData) inputStream.readObject();
                DoorsPage.doors = infoFromServer.getDoorsArray();
            } else if (pd.getOperationType().equals("UPDATE")) {
                outputStream.writeObject(pd);
                UsersPage.user_selected.setPassword(pd.getUser().getPassword());
            } else if (pd.getOperationType().equals("UPDATE_DOOR")) {
                outputStream.writeObject(pd);
            } else if (pd.getOperationType().equals("DELETE_USER")) {
                outputStream.writeObject(pd);
            }
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error!");
            alert.setHeaderText("Problem connecting to the server!");
            alert.showAndWait();
        }
    }

    @Override
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
        // Загрузка доверенного сертификата и закрытого ключа в систему
        try {
            InputStream certInputStream = Main.class.getResourceAsStream("/certificate.crt");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(certInputStream);
            certInputStream.close();

            InputStream keyInputStream = Main.class.getResourceAsStream("/keystore.p12");
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            char[] keyPassword = "iitu2019".toCharArray(); // Пароль для доступа к закрытому ключу
            keyStore.load(keyInputStream, keyPassword);
            keyInputStream.close();

            // Создание менеджера ключей и добавление сертификата
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, keyPassword);

            // Создание менеджера доверия и установка его в системные свойства
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch();
    }
}