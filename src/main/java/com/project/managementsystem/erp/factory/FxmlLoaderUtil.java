package com.project.managementsystem.erp.factory;

import com.project.managementsystem.erp.ui.controllers.OptionsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlLoaderUtil {

    // فتح نافذة خيارات بناءً على نوع الفاتورة
    public static void openOptionsMenuWindow(Class<?> clazz, String fxmlPath, String title, String invoiceType) {
        try {
            FXMLLoader loader = new FXMLLoader(clazz.getResource("/" + fxmlPath));
            Parent root = loader.load();

            // تمرير النوع للكنترولر
            OptionsMenuController controller = loader.getController();
            controller.initialize(invoiceType);

            // إعداد النافذة
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading " + fxmlPath);
            e.printStackTrace();
        }
    }

    // فتح نافذة الفاتورة فقط بدون تمرير نوع
    public static void openInvoiceWindow(Class<?> clazz, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(clazz.getResource("/" + fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading " + fxmlPath);
            e.printStackTrace();
        }
    }
}
