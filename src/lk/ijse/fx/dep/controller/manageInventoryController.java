package lk.ijse.fx.dep.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fx.dep.view.util.Customer;
import lk.ijse.fx.dep.view.util.Inventory;
import lk.ijse.fx.dep.view.util.ManageItems;
import lk.ijse.fx.dep.view.util.ManageUsers;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.UnaryOperator;

import static lk.ijse.fx.dep.controller.manageCusController.customer;
//import static lk.ijse.fx.dep.subController.methodsOfManageCusController.customer;
public class manageInventoryController {
    @FXML
    public AnchorPane itemId;
    @FXML
    public TextField itemCode;
    @FXML
    public TextField itemDes;
    @FXML
    public TextField itemPrice;
    @FXML
    public TextField ItemQty;

    static ArrayList<Inventory> inventory = new ArrayList<>();

    static{
        inventory.add(new Inventory("1","hj",10.00,50));
        inventory.add(new Inventory("2","bnm",20.00,50));
    }

    @FXML
    private TableView<Inventory> itemTable;

    public void clickNewItem(ActionEvent actionEvent) {
        itemCode.setEditable(true);
        itemDes.setEditable(true);
        itemPrice.setEditable(true);
        ItemQty.setEditable(true);
        itemCode.clear();
        itemDes.clear();
        itemPrice.clear();
        ItemQty.clear();
    }

    public void ItemHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) itemId.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void itemSave(ActionEvent actionEvent) {

        for (int k = 0;k < loginController.user.size();k++) {
            if(loginController.regi.get(0).getName().equals(loginController.user.get(k).getName())) {
                new Alert(Alert.AlertType.ERROR, "User Cannot Manage Inventory", ButtonType.OK).show();
                return;
            }
        }

            String code = itemCode.getText();
            String des = itemDes.getText();
            double price = Double.parseDouble(itemPrice.getText());
            int qty = Integer.parseInt(ItemQty.getText());
            if (code.trim().isEmpty() || des.trim().isEmpty() || String.valueOf(price).trim().isEmpty() || String.valueOf(qty).trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Can't have blank fields", ButtonType.OK).show();
                return;
            }

        if (inventory.size() != 0){
            for (int k = 0;k < inventory.size();k++) {
                if (code.equals(inventory.get(k).getCode()) && des.equals(inventory.get(k).getDescription()) && String.valueOf(price).equals(inventory.get(k).getPrice()) && String.valueOf(qty).equals(inventory.get(k).getQty())) {
                    new Alert(Alert.AlertType.ERROR, "Item is already in", ButtonType.OK).show();
                    return;
                }
            }
        }

            boolean result = ManageItems.addItems(code, des, price, qty);
            if (result) {
                new Alert(Alert.AlertType.INFORMATION, "Successfully Saved", ButtonType.OK).showAndWait();
                inventory.add(new Inventory(code, des, price, qty));
                ObservableList<Inventory> items = FXCollections.observableArrayList(inventory);
                itemTable.setItems(items);

                itemCode.setEditable(false);
                itemDes.setEditable(false);
                itemPrice.setEditable(false);
                ItemQty.setEditable(false);

                int index = itemTable.getSelectionModel().getSelectedIndex();
                itemDes.setEditable(true);
                itemPrice.setEditable(true);
                ItemQty.setEditable(true);

            } else {

                int index = itemTable.getSelectionModel().getSelectedIndex();
                itemDes.setEditable(true);
                itemPrice.setEditable(true);
                ItemQty.setEditable(true);
                String oId = itemCode.getText();
                String newDes = itemDes.getText();
                double newPrice = Double.parseDouble(itemPrice.getText());
                int newQty = Integer.parseInt(ItemQty.getText());
                if (oId.equals(inventory.get(index).getCode()) && !newDes.equals(inventory.get(index).getDescription()) || !String.valueOf(newPrice).equals(inventory.get(index).getPrice()) || !String.valueOf(newQty).equals(inventory.get(index).getQty())) {

                    new Alert(Alert.AlertType.ERROR, "Item is Updated", ButtonType.OK).showAndWait();

                    inventory.get(index).setDescription(newDes);
                    inventory.get(index).setPrice(newPrice);
                    inventory.get(index).setQty(newQty);
                    itemTable.refresh();
                } else if (oId.equals(inventory.get(index).getCode()) && newDes.equals(inventory.get(index).getDescription()) && String.valueOf(newPrice).equals(inventory.get(index).getPrice()) && String.valueOf(newQty).equals(inventory.get(index).getQty())) {
                    new Alert(Alert.AlertType.ERROR, "Item is already in", ButtonType.OK).show();
                }


//            ObservableList<Inventory> items = FXCollections.observableArrayList(inventory);
//            itemTable.setItems(items);
            }
    }

    public void initialize() {

        itemCode.setEditable(false);
        itemDes.setEditable(false);
        itemPrice.setEditable(false);
        ItemQty.setEditable(false);

        //if (loginController.regi.get(0).getName().equals(loginController.system.get(0).getName()) || loginController.regi.get(0).getName().equals(loginController.admin.get(0).getName()) || loginController.regi.get(0).getName().equals(loginController.admin.get(1).getName())) {
            itemTable.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Inventory>() {
                        @Override

                        public void changed(ObservableValue<? extends Inventory> ov, Inventory old_val, Inventory new_val) {
                            itemCode.setEditable(false);
                            itemCode.setText(new_val.getCode());
                            itemDes.setText(new_val.getDescription());
                            itemPrice.setText(String.valueOf(new_val.getPrice()));
                            ItemQty.setText(String.valueOf(new_val.getQty()));


                        }
                    });
        //}

        itemTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        itemTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        itemTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        itemTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        ObservableList<Inventory> items = FXCollections.observableArrayList(inventory);

        itemTable.setItems(items);
        itemCode.setEditable(false);
        itemDes.setEditable(true);
        itemPrice.setEditable(true);
        ItemQty.setEditable(true);

    }
    public void itemDelete(ActionEvent actionEvent) {

        //if (loginController.regi.get(0).getName().equals(loginController.system.get(0).getName()) || loginController.regi.get(0).getName().equals(loginController.admin.get(0).getName()) || loginController.regi.get(0).getName().equals(loginController.admin.get(1).getName())) {
        for (int k = 0; k < loginController.user.size(); k++) {
            if (loginController.regi.get(0).getName().equals(loginController.user.get(k).getName())) {
                new Alert(Alert.AlertType.ERROR, "User Cannot Manage Inventory", ButtonType.OK).show();
                return;
            }
        }

        int index = itemTable.getSelectionModel().getSelectedIndex();
        if (placeOrderController.perOrders.size() == 0) {
            inventory.remove(index);
            ObservableList<Inventory> items = FXCollections.observableArrayList(inventory);
            itemTable.setItems(items);

            itemCode.setEditable(false);
            itemDes.setEditable(false);
            itemPrice.setEditable(false);
            ItemQty.setEditable(false);

            return;
        }
        for (int i = 0; i < placeOrderController.perOrders.size(); ) {
            for (int j = 0; j < placeOrderController.perOrders.get(i).getItems().size(); ) {
                String c = placeOrderController.perOrders.get(i).getItems().get(j).getICode();

                if (inventory.get(index).getCode().equals(c)) {
                    new Alert(Alert.AlertType.ERROR, "Item is in Orders,Cannot delete the item", ButtonType.OK).show();
                    return;
                } else {

                    j++;
                    if (j == placeOrderController.perOrders.get(i).getItems().size()) {
                        i++;
                        //inventory.remove(index);
                    }
                    if (i == placeOrderController.perOrders.size()) {

                        inventory.remove(index);

                        ObservableList<Inventory> items = FXCollections.observableArrayList(inventory);
                        itemTable.setItems(items);

                        itemCode.setEditable(false);
                        itemDes.setEditable(false);
                        itemPrice.setEditable(false);
                        //ItemQty.setEditable(false);

                        return;
                    }

                }
            }

        }
        //new Alert(Alert.AlertType.ERROR, "Item is in Orders,Cannot delete the item", ButtonType.OK).show();

    }


    public void showAlerts(){

    }

    public void genInentoryRep(ActionEvent actionEvent) throws JRException {

        File file = new File("Reports/inRep.jasper");

        JasperReport compReport = (JasperReport) JRLoader.loadObject(file);

        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"code","des","price","qty"},0);

        ObservableList<Inventory> inventories = itemTable.getItems();

        for (Inventory inventory: inventories) {
            Object[] rowData = {inventory.getCode(),inventory.getDescription(),inventory.getPrice(),inventory.getQty()};
            dtm.addRow(rowData);
        }

        JasperPrint fillReport = JasperFillManager.fillReport(compReport,new HashMap<>(),new JRTableModelDataSource(dtm));

        JasperViewer.viewReport(fillReport,false);
    }
}
