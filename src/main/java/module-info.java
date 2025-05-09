module com.supermarket {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.supermarket to javafx.fxml;
    opens com.supermarket.controller to javafx.fxml;
    opens com.supermarket.controller.admin to javafx.fxml;
    opens com.supermarket.controller.employee to javafx.fxml;
    exports com.supermarket;
}