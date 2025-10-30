module com.exemplo.demo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.exemplo.demo3 to javafx.fxml;
    exports com.exemplo.demo3;
}