package com.project.managementsystem.erp.observer;

import com.project.managementsystem.erp.models.Product;
import com.project.managementsystem.erp.observer.Observer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class StockNotification implements Observer {
    @Override
    public void update(Product product) {
        Platform.runLater(() -> {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("⚠️ Stock Alert");
            alert.setHeaderText("Low Stock Warning");
            alert.setContentText("The product '" + product.getName() +
                    "' has low stock: only " + product.getStock() + " left!");
            alert.showAndWait();
        });
    }
}

