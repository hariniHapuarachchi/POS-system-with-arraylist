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
import lk.ijse.fx.dep.view.util.registerToSystem;

import java.io.IOException;

public class adminPageController {
    public TextField adminName;
    public TextField adminPsswrd;
    public TextField adminNewPsd;
    public TextField adminConPsd;
    @FXML
    private AnchorPane frmAdmin;
    @FXML
    private TextField adminAddUserName;
    @FXML
    private TextField adminAddPsd;
    @FXML
    private TableView<registerToSystem> adminTable;
    static int no = 1;
    public void initialize(){

        if (loginController.regi.get(0).getName().equals(loginController.system.get(0).getName())) {
            adminName.clear();
            adminPsswrd.clear();
            adminName.setEditable(false);
            adminPsswrd.setEditable(false);
            adminNewPsd.setEditable(false);
            adminConPsd.setEditable(false);
        }else {
            adminName.setText(loginController.regi.get(0).getName());
            adminPsswrd.setText(loginController.regi.get(0).getPassword());
            adminName.setEditable(false);
            adminPsswrd.setEditable(false);
        }

        adminTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<registerToSystem>() {
                    @Override

                    public void changed(ObservableValue<? extends registerToSystem> ov, registerToSystem old_val, registerToSystem new_val) {
                        adminAddUserName.setEditable(false);
                        adminAddUserName.setText(new_val.getName());
                        adminAddPsd.setText(new_val.getPassword());
                    }
                });

        adminTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        adminTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("password"));
        adminTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("button"));

        ObservableList<registerToSystem> items = FXCollections.observableArrayList(loginController.user);
        adminTable.setItems(items);

    }

    public void adminNewUser(ActionEvent actionEvent) {
        adminAddPsd.setEditable(true);
        adminAddUserName.setEditable(true);

        adminAddUserName.clear();
        adminAddPsd.clear();
    }

    public void adminSaveUser(ActionEvent actionEvent) {
        String userName = adminAddUserName.getText();
        String psswrd = adminAddPsd.getText();

        if (loginController.system.get(0).getName().equals(userName)) {
            new Alert(Alert.AlertType.ERROR, "System has this name...please use another name for user", ButtonType.OK).showAndWait();
            adminAddUserName.clear();
            adminAddPsd.clear();
            return;
        }

        if (loginController.admin.size() != 0){
            for (int j = 0;j < loginController.admin.size();j++){
                if (loginController.admin.get(j).equals(userName)) {
                    new Alert(Alert.AlertType.ERROR, "Admin has this name...please use another name for user", ButtonType.OK).showAndWait();
                    adminAddUserName.clear();
                    adminAddPsd.clear();
                    return;
                }
            }
        }
        if (loginController.user.size() == 0){
            loginController.user.add(0,new registerToSystem(userName,psswrd));
        }else if (loginController.user.size() >= 1){

            for (int i = 0;i < loginController.user.size();i++){
                if (userName.equals(loginController.user.get(i).getName())){
                    new Alert(Alert.AlertType.ERROR, "User is already in", ButtonType.OK).show();
                    return;
                }
            }

            loginController.user.add(no,new registerToSystem(userName,psswrd));
            no++;
        }


        adminAddUserName.setEditable(false);
        adminAddPsd.setEditable(false);
        adminAddUserName.clear();
        adminAddPsd.clear();

        ObservableList<registerToSystem> items = FXCollections.observableArrayList(loginController.user);
        adminTable.setItems(items);
    }

    public void adminHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmAdmin.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void adminLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmAdmin.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void adminChange(ActionEvent actionEvent) throws IOException {

            String adName = adminName.getText();
            String newpsd = adminNewPsd.getText();
            String cnfrmpsd = adminConPsd.getText();

            if (newpsd.trim().isEmpty() || cnfrmpsd.trim().isEmpty() || !newpsd.equals(cnfrmpsd)) {
                new Alert(Alert.AlertType.ERROR, "Can't have blank fields or Re-check your password", ButtonType.OK).show();
                return;
            } else {
                for (int i = 0; i < loginController.admin.size(); i++) {
                    if (adName.equals(loginController.admin.get(i).getName())) {
                        loginController.admin.get(i).setPassword(newpsd);
                        System.out.println(loginController.admin.get(i).getPassword());
                        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
                        Scene scene = new Scene(parent);
                        Stage primaryStage = (Stage) frmAdmin.getScene().getWindow();
                        primaryStage.setScene(scene);
                    }
                }

            }

            adminAddUserName.setEditable(false);
            adminAddPsd.setEditable(false);
        }

    public void adminUpdateUser(ActionEvent actionEvent) {

        if (loginController.user.size() != 0) {
            int index = adminTable.getSelectionModel().getSelectedIndex();
            adminAddPsd.setEditable(true);
            String uName = adminAddUserName.getText();
            String updatePsd = adminAddPsd.getText();

            loginController.user.get(index).setPassword(updatePsd);
            new Alert(Alert.AlertType.INFORMATION, "Successfully Updated", ButtonType.OK).showAndWait();
            adminTable.refresh();

            adminTable.getSelectionModel().clearSelection();

            adminAddUserName.clear();
            adminAddPsd.clear();
        }
    }
}
