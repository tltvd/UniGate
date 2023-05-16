module com.example.unigate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires java.desktop;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires org.bouncycastle.provider;


    opens com.example.unigate.models to javafx.base;
    opens com.example.unigate to javafx.fxml;
    exports com.example.unigate;
    exports com.example.unigate.DataBase;
    opens com.example.unigate.DataBase to javafx.fxml;
    opens network to javafx.graphics;

}