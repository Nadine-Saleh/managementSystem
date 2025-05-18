// File: src/main/java/com/project/managementsystem/Main.java
package com.project.managementsystem;

import com.project.managementsystem.erp.config.DBConnection;
import com.project.managementsystem.erp.ui.App;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        Application.launch(App.class, args);
//        DBConnection.testConnection();

    }
}