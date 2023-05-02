module com.example.unigate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires java.desktop;
    requires com.google.zxing;


    opens models to javafx.base;
    opens com.example.unigate to javafx.fxml;
    exports com.example.unigate;
}