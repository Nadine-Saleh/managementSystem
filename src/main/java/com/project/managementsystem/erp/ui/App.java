package com.project.managementsystem.erp.ui;

import com.project.managementsystem.erp.models.Product;
import com.project.managementsystem.erp.observer.StockNotification;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX Application class.
 * Initializes the GUI and launches the ERP dashboard.
 */
public class App extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * This method is called automatically when the app starts.
     *
     * @param primaryStage - the main window of the application
     * @throws IOException if FXML file fails to load
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the main view from FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/Css/signup_styles.css").toExternalForm());


        // Configure the primary stage
        primaryStage.setTitle("ERP Admin Dashboard");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(true);

        // Show the stage
        primaryStage.show();

        System.out.println("ERP Application started successfully.");
        Product p = new Product(1, "Laptop", 12000, 5);
        StockNotification sn = new StockNotification();
        p.attach(sn);
        p.setStock(3);
    }

    /**
     * Optional init() method.
     * Called before start(), useful for initializing resources.
     */
    @Override
    public void init() {
        // You can initialize services, database connections, etc.
        System.out.println("Initializing ERP application...");
    }

    /**
     * Optional stop() method.
     * Called when the application is closing.
     */
    @Override
    public void stop() {
        // Clean up resources (e.g., close DB connection)
        System.out.println("ERP application is shutting down...");
    }

    /**
     * Public method to access the launch functionality outside IDE.
     * Used by Main.java
     */
    public static void launchApp(String[] args) {
        launch(args);
    }
}