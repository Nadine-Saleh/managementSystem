module com.project.managementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.managementsystem.erp.ui.controllers to javafx.fxml;
    exports com.project.managementsystem.erp.ui;
}