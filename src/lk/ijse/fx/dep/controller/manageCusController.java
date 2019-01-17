package lk.ijse.fx.dep.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.fx.dep.dynaminARR.DynamicArray;
import lk.ijse.fx.dep.subController.methodsOfManageCusController;
import lk.ijse.fx.dep.view.util.Customer;
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

public class manageCusController {
    @FXML
    private TextField cId;
    @FXML
    private TextField cName;
    @FXML
    private TextField cAddress;
    @FXML
    private AnchorPane frmCus;


    public static ArrayList<Customer> customer = new ArrayList<>();

    @FXML
    private TableView<Customer> cTable;
    @FXML
    private Button bttnNewCus;
    @FXML
    private Button buttonSave;

    static{
        customer.add(new Customer("C001","Harini","Maharagama"));
        customer.add(new Customer("C002","Kavee","Piliyanadala"));
    }

    public void initialize() {

//        String id = cId.getText();
//        String name = cName.getText();
//        String address = cAddress.getText();

        cId.setEditable(false);
        cName.setEditable(false);
        cAddress.setEditable(false);

            cTable.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<Customer>() {
                        @Override

                        public void changed(ObservableValue<? extends Customer> ov, Customer old_val, Customer new_val) {
                            cId.setEditable(false);
                            cId.setText(new_val.getId());
                            cName.setText(new_val.getName());
                            cAddress.setText(new_val.getAddress());
                        }
                    });
            cTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
            cTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
            cTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));

            ObservableList<Customer> items = FXCollections.observableArrayList(customer);
            cTable.setItems(items);


    }


    public void cNewCus(ActionEvent actionEvent) {

        cId.setEditable(true);
        cName.setEditable(true);
        cAddress.setEditable(true);
        cId.clear();
        cName.clear();
        cAddress.clear();

    }

    public void cSave(ActionEvent actionEvent) {

            String id = cId.getText();
            String name = cName.getText();
            String address = cAddress.getText();
            if (id.trim().isEmpty() || name.trim().isEmpty() || address.trim().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Can't have blank fields", ButtonType.OK).show();
                return;
            }

            if (customer.size() != 0){
                for (int k = 0;k < customer.size();k++) {
                    if (id.equals(customer.get(k).getId()) && name.equals(customer.get(k).getName()) && address.equals(customer.get(k).getAddress())) {
                        new Alert(Alert.AlertType.ERROR, "Customer is already in", ButtonType.OK).show();
                        return;
                    }
                }
            }
            boolean result = ManageUsers.registerCustomer(id, name, address);
            if (result) {
                cTable.getSelectionModel().clearSelection();
                new Alert(Alert.AlertType.INFORMATION, "Successfully Saved", ButtonType.OK).showAndWait();
                customer.add(new Customer(id, name, address));
                ObservableList<Customer> items = FXCollections.observableArrayList(customer);

                cTable.setItems(items);
                cId.setEditable(false);
                cName.setEditable(false);
                cAddress.setEditable(false);

                int index = cTable.getSelectionModel().getSelectedIndex();
                cName.setEditable(true);
                cAddress.setEditable(true);

            } else {

                for (int k = 0;k < loginController.user.size();k++) {
                    if(loginController.regi.get(0).getName().equals(loginController.user.get(k).getName())) {
                        new Alert(Alert.AlertType.ERROR, "User Cannot Manage Inventory", ButtonType.OK).show();
                        return;
                    }
                }

                int index = cTable.getSelectionModel().getSelectedIndex();
                cName.setEditable(true);
                cAddress.setEditable(true);

                String oId = cId.getText();
                String newName = cName.getText();
                String newAddress = cAddress.getText();

                if (oId.equals(customer.get(index).getId()) && !newName.equals(customer.get(index).getName()) || !newAddress.equals(customer.get(index).getAddress())) {
                    customer.get(index).setName(newName);
                    customer.get(index).setAddress(newAddress);
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Updated", ButtonType.OK).showAndWait();
                    cTable.refresh();
                } else if (oId.equals(customer.get(index).getId()) && newName.equals(customer.get(index).getName()) && newAddress.equals(customer.get(index).getAddress())) {
                    new Alert(Alert.AlertType.ERROR, "Customer is already in", ButtonType.OK).show();
                }
                cTable.getSelectionModel().clearSelection();
            }


    }

    public void cDelete(ActionEvent actionEvent) {

        for (int k = 0;k < loginController.user.size();k++) {
            if(loginController.regi.get(0).getName().equals(loginController.user.get(k).getName())) {
                new Alert(Alert.AlertType.ERROR, "User Cannot Manage Inventory", ButtonType.OK).show();
                return;
            }
        }

        int index = cTable.getSelectionModel().getSelectedIndex();
        if (placeOrderController.perOrders.size() == 0){
            customer.remove(index);
            ObservableList<Customer> items = FXCollections.observableArrayList(customer);
            cTable.setItems(items);
            cId.setEditable(false);
            cName.setEditable(false);
            cAddress.setEditable(false);
            new Alert(Alert.AlertType.ERROR, "Customer Deleted successfully", ButtonType.OK).showAndWait();
            cTable.getSelectionModel().clearSelection();
            return;

        }

        for (int i = 0;i < placeOrderController.perOrders.size();){
            if (!customer.get(index).getId().equals(placeOrderController.perOrders.get(i).getCuID())){
                i++;
                if (i == placeOrderController.perOrders.size()){
                    customer.remove(index);
                    ObservableList<Customer> items = FXCollections.observableArrayList(customer);
                    cTable.setItems(items);
                    cTable.getSelectionModel().clearSelection();
//                    cId.setEditable(false);
//                    cName.setEditable(false);
//                    cAddress.setEditable(false);
                    new Alert(Alert.AlertType.ERROR, "Customer Deleted successfully", ButtonType.OK).showAndWait();
                    return;

                }

            }else
                break;

        }
        new Alert(Alert.AlertType.ERROR, "Customer is in Orders,Cannot delete the customer", ButtonType.OK).show();

    }

    public void cHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmCus.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void genReport(ActionEvent actionEvent) throws JRException {

        File file = new File("Reports/customer.jasper");

        JasperReport compileReport = (JasperReport) JRLoader.loadObject(file);

        DefaultTableModel dtm = new DefaultTableModel(new Object[]{"Id","name","address"},0);

        ObservableList<Customer> customers = cTable.getItems();

        for (Customer customer: customers) {
            Object[] rowData = {customer.getId(),customer.getName(),customer.getAddress()};
            dtm.addRow(rowData);
        }

        JasperPrint filledReport = JasperFillManager.fillReport(compileReport,new HashMap<>(),new JRTableModelDataSource(dtm));

        JasperViewer.viewReport(filledReport,false);
    }
}
