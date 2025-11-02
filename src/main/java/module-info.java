module com.example.bookhub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires javafx.graphics;

    opens com.example.bookhub to javafx.fxml;
    exports com.example.bookhub;
    exports com.example.bookhub.controllers;
    opens com.example.bookhub.controllers to javafx.fxml;
    exports com.example.bookhub.utils;
    opens com.example.bookhub.utils to javafx.fxml;
}