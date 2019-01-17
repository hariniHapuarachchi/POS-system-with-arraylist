package lk.ijse.fx.dep.view.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import lk.ijse.fx.dep.controller.systemPageController;

public class registerToSystem {

    private String name;
    private String password;
    private Button button;

    public registerToSystem(String name,String password){
        this.name=name;
        this.password=password;
        this.button = new Button("Delete");
        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                systemPageController.delete();
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }
}
