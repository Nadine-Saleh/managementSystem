package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.util.NotificationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class NotificationController {

    @FXML
    private VBox notificationBox;

    @FXML
    public void initialize() {
        for (String message : NotificationManager.getInstance().getNotifications()) {
            Label label = new Label("🔔 " + message);
            label.setWrapText(true);
            label.setStyle("-fx-background-color: #fff3cd; -fx-padding: 10; -fx-border-color: #ffeeba; -fx-border-radius: 5; -fx-background-radius: 5;");
            notificationBox.getChildren().add(label);
        }
    }
}
