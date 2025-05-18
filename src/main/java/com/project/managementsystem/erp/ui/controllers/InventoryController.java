package com.project.managementsystem.erp.ui.controllers;

import com.project.managementsystem.erp.dao.InventoryDAO;
import com.project.managementsystem.erp.dao.InventoryDAOImpl;
import com.project.managementsystem.erp.dao.ProductDAO;
import com.project.managementsystem.erp.dao.ProductDAOImpl;
import com.project.managementsystem.erp.models.Inventory;
import com.project.managementsystem.erp.models.Product;
import com.project.managementsystem.erp.viewmodel.InventoryViewModel;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryController {

    @FXML private TableView<InventoryViewModel> inventoryTable;
    @FXML private TableColumn<InventoryViewModel, String> productNameCol;
    @FXML private TableColumn<InventoryViewModel, String> changeTypeCol;
    @FXML private TableColumn<InventoryViewModel, Integer> quantityChangeCol;
    @FXML private TableColumn<InventoryViewModel, Integer> currentStockCol;
    @FXML private TableColumn<InventoryViewModel, String> timestampCol;
    @FXML private TableColumn<InventoryViewModel, String> referenceTypeCol;

    private final InventoryDAO inventoryDAO = new InventoryDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @FXML
    public void initialize() {
        productNameCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getProductName()));
        changeTypeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getChangeType()));
        quantityChangeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantityChange()));
        currentStockCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCurrentStock()));
        timestampCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTimestamp()));
        referenceTypeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getReferenceType()));

        loadInventoryData();
    }

    @FXML
    private void handleRefresh() {
        loadInventoryData();
    }

    private void loadInventoryData() {
        List<Inventory> itemList = inventoryDAO.getAllInventory();
        Map<Integer, String> productNameMap = new HashMap<>();

        // تحميل كل المنتجات مره واحدة
        productDAO.getAllProducts().forEach(p -> productNameMap.put(p.getId(), p.getName()));

        ObservableList<InventoryViewModel> observableList = FXCollections.observableArrayList();
        for (Inventory inv : itemList) {
            String productName = productNameMap.getOrDefault(inv.getProductId(), "Unknown");
            observableList.add(new InventoryViewModel(
                    productName,
                    inv.getChangeType(),
                    inv.getQuantityChange(),
                    inv.getCurrentStock(),
                    inv.getTimestamp(),
                    inv.getReferenceType()
            ));
        }

        inventoryTable.setItems(observableList);
    }
}
