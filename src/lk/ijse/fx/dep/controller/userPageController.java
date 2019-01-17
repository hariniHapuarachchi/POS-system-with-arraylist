package lk.ijse.fx.dep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fx.dep.view.util.registerToSystem;

import java.io.IOException;

public class userPageController {
    @FXML
    private TextField userName;
    @FXML
    private TextField userPassword;
    @FXML
    private TextField userNewPsd;
    @FXML
    private TextField userConPsd;
    @FXML
    private AnchorPane frmUser;

    public void initialize(){
        userName.setText(loginController.regi.get(0).getName());
        userPassword.setText(loginController.regi.get(0).getPassword());
        userName.setEditable(false);
        userPassword.setEditable(false);

    }
    public void changeUser(ActionEvent actionEvent) throws IOException {
        String uName = userName.getText();
        String newpsd = userNewPsd.getText();
        String cnfrmpsd = userConPsd.getText();

        if (newpsd.trim().isEmpty() || cnfrmpsd.trim().isEmpty() || !newpsd.equals(cnfrmpsd)) {
            new Alert(Alert.AlertType.ERROR, "Can't have blank fields or Re-check your password", ButtonType.OK).show();
            return;
        }else{
            for (int i =0;i < loginController.user.size();i++){
                if (uName.equals(loginController.user.get(i).getName())){
                    loginController.user.get(i).setPassword(newpsd);
                    System.out.println(loginController.user.get(i).getPassword());
                    Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) frmUser.getScene().getWindow();
                    primaryStage.setScene(scene);
                }
            }

        }
    }

    public void userLogout(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/loginToSystem.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmUser.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void userHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmUser.getScene().getWindow();
        primaryStage.setScene(scene);
    }
}
