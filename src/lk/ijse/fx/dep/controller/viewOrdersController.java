package lk.ijse.fx.dep.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.fx.dep.view.util.Order;

import java.io.IOException;
import java.util.ArrayList;

public class viewOrdersController {
    @FXML
    private AnchorPane frmViewOrder;
    @FXML
    private TextField searchText;

    private static ArrayList<Order> newOrderList = new ArrayList<>();

    ArrayList<Order> newSearchOrder = new ArrayList<>();
    @FXML
    private TableView<Order> viewOrderTable;

    public void initialize(){

        for (int j = 0; j< placeOrderController.perOrders.size(); j++){
            if (placeOrderController.perOrders.get(j) != null){
                String code = placeOrderController.perOrders.get(j).getOrderID();
                String day = placeOrderController.perOrders.get(j).getDay();
                String cid = placeOrderController.perOrders.get(j).getCuID();
                String name = placeOrderController.perOrders.get(j).getcName();
                System.out.println("Place Order");
                System.out.println(day);
                System.out.println(cid);
                System.out.println(name);

                newOrderList.add(new Order(code,day,cid,name));


                ObservableList<Order> items = FXCollections.observableArrayList(newOrderList);
                System.out.println("Customer Name : " + items.get(0).getcName());
                viewOrderTable.setItems(items);

            }
        }


        viewOrderTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("orderID"));
        viewOrderTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("day"));
        viewOrderTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("cuID"));
        viewOrderTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("customerName"));


    }

    public void setOnKeyEnterForOrder(KeyEvent keyEvent) {
        searchText.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {

                    String oid = searchText.getText();
                    for (int j = 0; j< newOrderList.size(); j++){
                        if (newOrderList.get(j) != null && oid.equals(newOrderList.get(j).getOrderID())) {
                            String CODE = placeOrderController.perOrders.get(j).getOrderID();
                            String DAY = placeOrderController.perOrders.get(j).getDay();
                            String CID = placeOrderController.perOrders.get(j).getCuID();
                            String NAME = placeOrderController.perOrders.get(j).getcName();

                            newSearchOrder.add(new Order(CODE,DAY,CID,NAME));

                            ObservableList<Order> items = FXCollections.observableArrayList(newSearchOrder);
                            System.out.println("Customer Name : " + items.get(0).getcName());
                            viewOrderTable.setItems(items);

                        }
                    }

                }
            }
        });


    }

    public void viewOrderHome(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmViewOrder.getScene().getWindow();
        primaryStage.setScene(scene);
    }


}
