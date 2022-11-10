module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires htmlunit;
    requires sib.api.v3.sdk;
    requires mysql.connector.java;

    requires java.sql;
    requires junit;

    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}