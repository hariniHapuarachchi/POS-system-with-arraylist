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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fx.dep.view.util.Customer;
import lk.ijse.fx.dep.view.util.registerToSystem;

import java.io.IOException;

import lk.ijse.fx.dep.controller.loginController;

public class systemPageController {
    @FXML
    private AnchorPane frmSystem;
    @FXML
    private TextField spsswrd;
    @FXML
    private TextField sNewPsswrd;
    @FXML
    private TextField sCnfrmPsswrd;
    @FXML
    private TextField sAddUser;
    @FXML
    private TextField sAddPsswrd;
    @FXML
    private TableView<registerToSystem> systemAdminTable;
    private static TableView<registerToSystem> staticTable;

    public static void delete(){

        System.out.println(staticTable.getSelectionModel().getSelectedIndex());
        staticTable.getItems().remove(staticTable.getSelectionModel().getSelectedItem());

    }

    public void initialize() {
        spsswrd.setText(loginController.system.get(0).getPassword());

        systemAdminTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<registerToSystem>() {
                    @Override

                    public void changed(ObservableValue<? extends registerToSystem> ov, registerToSystem old_val, registerToSystem new_val) {
                        sAddUser.setEditable(false);
                        sAddUser.setText(new_val.getName());
                        sAddPsswrd.setText(new_val.getPassword());
                    }
                });
        systemAdminTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        systemAdminTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("password"));
        systemAdminTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("button"));


        ObservableList<registerToSystem> items = FXCollections.observableArrayList(loginController.admin);
        systemAdminTable.setItems(items);

        staticTable= systemAdminTable;



    }

    public void sChangeSystem(ActionEvent actionEvent) throws IOException {
        String newpsswrd = sNewPsswrd.getText();
        String cnfrmpsswrd = sCnfrmPsswrd.getText();

        if (newpsswrd.trim().isEmpty() || cnfrmpsswrd.trim().isEmpty() || !newpsswrd.equals(cnfrmpsswrd)) {
            new Alert(Alert.AlertType.ERROR, "Can't have blank fields or Re-check your password", ButtonType.OK).show();
            return;
        } else {
            loginController.system.get(0).setPassword(newpsswrd);
            Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) frmSystem.getScene().getWindow();
            primaryStage.setScene(scene);
        }

        sAddUser.setEditable(false);
        sAddPsswrd.setEditable(false);

    }

    public void sAddAdmin(ActionEvent actionEvent) {
        String userName = sAddUser.getText();
        String psswrd = sAddPsswrd.getText();

        for (int i = 0; i < loginController.user.size(); i++) {
            if (loginController.user.get(i).getName().equals(userName)) {
                new Alert(Alert.AlertType.ERROR, "This name has for a user..please use anoher name for admin", ButtonType.OK).showAndWait();
                sAddUser.clear();
                sAddPsswrd.clear();
                return;
            }
        }

        if (loginController.system.get(0).getName().equals(userName)) {
            new Alert(Alert.AlertType.ERROR, "System has this name...please use another name for admin", ButtonType.OK).showAndWait();
            sAddUser.clear();
            sAddPsswrd.clear();
            return;
        } else {
                if (loginController.admin.size() == 0) {
                loginController.admin.add(0, new registerToSystem(userName, psswrd));
            } else if (loginController.admin.size() == 1) {
                if (userName.equals(loginController.admin.get(0).getName())) {
                    new Alert(Alert.AlertType.ERROR, "Admin is already in", ButtonType.OK).show();
                    sAddUser.clear();
                    sAddPsswrd.clear();
                    return;
                }
                loginController.admin.add(1, new registerToSystem(userName, psswrd));
            }else {
                    new Alert(Alert.AlertType.ERROR, "can only add 2 admins", ButtonType.OK).show();
                }

            sAddUser.setEditable(false);
            sAddPsswrd.setEditable(false);
            sAddUser.clear();
            sAddPsswrd.clear();

            ObservableList<registerToSystem> items = FXCollections.observableArrayList(loginController.admin);
            systemAdminTable.setItems(items);
    }

}

    public void systemHome(ActionEvent actionEvent) throws IOException {
        sAddUser.setEditable(false);
        sAddPsswrd.setEditable(false);
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmSystem.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void sNewAdmin(ActionEvent actionEvent) {
        sAddPsswrd.setEditable(true);
        sAddUser.setEditable(true);
    }

    public void systemLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmSystem.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void sAddUser(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/adminPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmSystem.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void sUpdaeAdmin(ActionEvent actionEvent) {

        if (loginController.admin.size() != 0) {
            int index = systemAdminTable.getSelectionModel().getSelectedIndex();
            sAddPsswrd.setEditable(true);
            String uName = sAddUser.getText();
            String updatePsd = sAddPsswrd.getText();
            if ((uName.equals(loginController.admin.get(0).getName()) && !updatePsd.equals(loginController.admin.get(0).getPassword())) || (uName.equals(loginController.admin.get(1).getName()) && !updatePsd.equals(loginController.admin.get(1).getPassword()))) {
                loginController.admin.get(index).setPassword(updatePsd);

                new Alert(Alert.AlertType.INFORMATION, "Successfully Updated", ButtonType.OK).showAndWait();
                systemAdminTable.refresh();
            } else if (uName.equals(loginController.admin.get(index).getName()) && updatePsd.equals(loginController.admin.get(index).getPassword())) {
                new Alert(Alert.AlertType.ERROR, "Admin is already in", ButtonType.OK).show();
            }
            systemAdminTable.getSelectionModel().clearSelection();
        }
    }
}
