package com.project.managementsystem.erp.factory;

import com.project.managementsystem.erp.ui.controllers.OptionsMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FxmlLoaderUtil {

    public static void openOptionsMenuWindow(Class<?> clazz, String fxmlPath, String title, String invoiceType) {
        try {
            FXMLLoader loader = new FXMLLoader(clazz.getResource("/" + fxmlPath));
            Parent root = loader.load();

            OptionsMenuController controller = loader.getController();
            controller.initialize(invoiceType);

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
            System.out.println("Loading FXML: /" + fxmlPath); // 👈 DEBUG LOG
            FXMLLoader loader = new FXMLLoader(clazz.getResource("/" + fxmlPath));

            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + fxmlPath);
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Load Error");
            alert.setHeaderText("Failed to load view");
            alert.setContentText("Could not load FXML file:\n" + fxmlPath);
            alert.showAndWait();
        }
    }
}
