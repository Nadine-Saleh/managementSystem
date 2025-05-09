module com.project.managementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.project.managementsystem.erp.ui.controllers to javafx.fxml;
    exports com.project.managementsystem.erp.ui;
}