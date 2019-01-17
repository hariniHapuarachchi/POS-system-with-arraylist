package lk.ijse.fx.dep.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fx.dep.view.util.registerToSystem;

import java.io.IOException;
import java.util.ArrayList;

public class loginController {
    @FXML
    private TextField loginName;
    @FXML
    private TextField loginPasswrd;
    @FXML
    private AnchorPane frmLogin;

    static ArrayList<registerToSystem> system = new ArrayList<>();
    static ArrayList<registerToSystem> admin = new ArrayList<>();
    static ArrayList<registerToSystem> user = new ArrayList<>();
    static ArrayList<registerToSystem> regi = new ArrayList<>();

    static {
        system.add(new registerToSystem("HHH","k1"));
    }

    

    public void loginButton(ActionEvent actionEvent) throws IOException {
        String name = loginName.getText();
        String passwrd = loginPasswrd.getText();
        System.out.println(system.get(0).getName());
        System.out.println(system.get(0).getPassword());


        regi.add(new registerToSystem(name,passwrd));

        if (name.equals(system.get(0).getName()) && passwrd.equals(system.get(0).getPassword())){
            regi.get(0).setName(system.get(0).getName());
            regi.get(0).setPassword(system.get(0).getPassword());
            Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
            Scene scene = new Scene(parent);
            Stage primaryStage = (Stage) frmLogin.getScene().getWindow();
            primaryStage.setScene(scene);
            return;
        }

        for (int i=0;i<admin.size();i++){
            //System.out.println("Name " +admin.get(0).getPassword());
            if (name.equals(admin.get(i).getName()) && passwrd.equals(admin.get(i).getPassword())){
                regi.get(0).setName(admin.get(i).getName());
                regi.get(0).setPassword(admin.get(i).getPassword());
                Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
                Scene scene = new Scene(parent);
                Stage primaryStage = (Stage) frmLogin.getScene().getWindow();
                primaryStage.setScene(scene);
                return;
            }
        }

            for (int j = 0;j < user.size();j++){
                if (name.equals(user.get(j).getName()) && passwrd.equals(user.get(j).getPassword())){
                    System.out.println("Hiiiii " +user.get(j).getPassword());
                    regi.get(0).setName(user.get(j).getName());
                    regi.get(0).setPassword(user.get(j).getPassword());
                    Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
                    Scene scene = new Scene(parent);
                    Stage primaryStage = (Stage) frmLogin.getScene().getWindow();
                    primaryStage.setScene(scene);
                    return;
                }

        }

    }
}
