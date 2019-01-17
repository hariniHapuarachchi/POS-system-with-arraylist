package lk.ijse.fx.dep.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class mainPageController {
    public ImageView mCus;
    public ImageView mInven;
    public AnchorPane manageCus;
    public ImageView mOrder;
    public ImageView mViewO;
    public ImageView mSettings;

    public void clickCus(MouseEvent mouseEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/manageCus.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) mCus.getScene().getWindow();
        primaryStage.setScene(scene);

    }

    public void clickInven(MouseEvent mouseEvent) throws IOException {

            Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/manageInventory.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) mInven.getScene().getWindow();
            primaryStage.setScene(scene);


    }

    public void clickOrder(MouseEvent mouseEvent) throws IOException {

            Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/PlaceOrder.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) mOrder.getScene().getWindow();
            primaryStage.setScene(scene);
//        }else{
//            new Alert(Alert.AlertType.ERROR, "User Cannot Manage Place Order", ButtonType.OK).show();
//            return;
//        }


    }

    public void clickViewOrder(MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/viewOrders.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) mViewO.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void clickServices(MouseEvent mouseEvent) throws IOException {

        if (loginController.regi.get(0).getName().equals(loginController.system.get(0).getName()) && loginController.regi.get(0).getPassword().equals(loginController.system.get(0).getPassword())) {
            Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/systemPage.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) mSettings.getScene().getWindow();
            primaryStage.setScene(scene);
        }

        for (int i = 0;i < loginController.admin.size();i++){
            if (loginController.regi.get(0).getName().equals(loginController.admin.get(i).getName()) && loginController.regi.get(0).getPassword().equals(loginController.admin.get(i).getPassword())) {
                Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/adminPage.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) mSettings.getScene().getWindow();
                primaryStage.setScene(scene);
            }
        }

        for (int i = 0;i < loginController.user.size();i++){
            if (loginController.regi.get(0).getName().equals(loginController.user.get(i).getName()) && loginController.regi.get(0).getPassword().equals(loginController.user.get(i).getPassword())) {
                Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/userPage.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) mSettings.getScene().getWindow();
                primaryStage.setScene(scene);
            }
        }
    }
}
