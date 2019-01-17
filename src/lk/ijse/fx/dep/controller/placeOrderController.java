package lk.ijse.fx.dep.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.fx.dep.view.util.*;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static lk.ijse.fx.dep.controller.manageCusController.customer;
import static lk.ijse.fx.dep.controller.manageInventoryController.inventory;

public class placeOrderController {
    @FXML
    private Button pAdd;
    @FXML
    private Button pRemove;
    @FXML
    private AnchorPane frmPlaceOrder;
    @FXML
    private TextField oID;
    @FXML
    private TextField cID;
    @FXML
    private TextField itemCode;
    @FXML
    private TextField currentDate;
    @FXML
    private TextField cName;
    @FXML
    private TextField placeDes;
    @FXML
    private TextField placeQty;
    @FXML
    private TextField price;
    @FXML
    private TextField avQty;
    @FXML
    private TableView<Order> placeTable;
    @FXML
    private TextField subTotal;
    static int count = 1;
     ArrayList<Order> orders = new ArrayList<>();
     static ArrayList<Order> perOrders = new ArrayList<>();
    static double tot =0;
    static int temp;
    static double nTot = 0;
    static int decQty = 0;
    private static int num = 1;
    public void initialize(){
        setOrder();
        oID.setEditable(false);
        initClock();
        currentDate.setEditable(false);

        placeTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("ICode"));
        placeTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("des"));
        placeTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        placeTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));
        placeTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("netPrice"));


        ObservableList<Order> items = FXCollections.observableArrayList(orders);
        placeTable.setItems(items);

        //oID.clear();
        placeDes.clear();
        avQty.clear();
        price.clear();
        placeQty.clear();

        placeTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Order>() {
                    @Override

                    public void changed(ObservableValue<? extends Order> ov, Order old_val, Order new_val) {
                        oID.setEditable(false);
                        cID.setEditable(false);
                        currentDate.setEditable(false);
                        cName.setEditable(false);
                        avQty.setEditable(false);
                        price.setEditable(false);
                        placeDes.setEditable(false);

                        oID.setText(String.valueOf(new_val.getOrderID()));
                        currentDate.setText(new_val.getDay());
                        itemCode.setText(new_val.getICode());
                        placeDes.setText(new_val.getDes());
                        avQty.setText(String.valueOf(new_val.getAvaQty()));
                        price.setText(String.valueOf(new_val.getUnitPrice()));
                        placeQty.setText(String.valueOf(new_val.getQty()));

                    }
                });

    }

    private void initClock() {

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            currentDate.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    public void placeADD(ActionEvent actionEvent) {

        String cId = cID.getText();
        String code = itemCode.getText();
        String des = placeDes.getText();
        int qty = Integer.parseInt(placeQty.getText());
        double unit = Double.parseDouble(price.getText());
        int realQty = Integer.parseInt(avQty.getText());
        double netTot = unit * qty;
        tot = tot + netTot;



        if (cId.trim().isEmpty() || code.trim().isEmpty() || String.valueOf(qty).trim().isEmpty()) {
            pAdd.disableProperty();
            pRemove.disableProperty();
            new Alert(Alert.AlertType.ERROR, "Can't have blank fields for customer Id , Item code and Quantity", ButtonType.OK).show();

        } else {

            if (realQty >= qty) {
                for (int j = 0; j < orders.size(); j++) {

                    if (orders.get(j) != null && code.equals(orders.get(j).getICode())) {
                        //orders.get(j).setAvaQty(temp);
                        nTot = orders.get(j).getNetPrice() + netTot;
                        decQty = orders.get(j).getQty() + qty;
                        orders.get(j).setNetPrice(nTot);
                        orders.get(j).setQty(decQty);
                        placeTable.refresh();
                        subTotal.setText(String.valueOf(tot));
                        cID.setEditable(false);
                        temp = orders.get(j).getAvaQty() - qty;
                        if (temp >= 0) {
                            realQty = temp;
                            orders.get(j).setAvaQty(realQty);
                            avQty.setText(String.valueOf(realQty));
                        }
                        itemCode.clear();
                        placeDes.clear();
                        avQty.clear();
                        price.clear();
                        placeQty.clear();
                        return;
                    }

                }


                    orders.add(new Order(code, des, unit, qty, netTot,realQty));
                    ObservableList<Order> items = FXCollections.observableArrayList(orders);
                    placeTable.setItems(items);
                    placeTable.refresh();
                    System.out.println(qty);
                    temp = realQty - qty;
                    realQty = temp;

                    for (int j = 0; j < orders.size(); j++) {
                        if (orders.get(j) != null && code.equals(orders.get(j).getICode())) {
                            orders.get(j).setAvaQty(realQty);
                        }

                    }
                    avQty.setText(String.valueOf(realQty));
                    subTotal.setText(String.valueOf(tot));
                    itemCode.clear();
                placeDes.clear();
                avQty.clear();
                price.clear();
                placeQty.clear();

            }
        }

    }

    public void placeRemove(ActionEvent actionEvent) {


        oID.setEditable(false);
        cID.setEditable(false);
        currentDate.setEditable(false);
        cName.setEditable(false);
        avQty.setEditable(false);
        price.setEditable(false);
        placeDes.setEditable(false);
        itemCode.setEditable(false);
        placeQty.setEditable(true);

        int no = Integer.parseInt(placeQty.getText());

        int index = placeTable.getSelectionModel().getSelectedIndex();

        if (no < orders.get(index).getQty()){
            int newQty = orders.get(index).getQty() - no;
            double newPrice = orders.get(index).getNetPrice() - (no * Double.parseDouble(price.getText()));
            double tot = Double.parseDouble(subTotal.getText()) - (no * Double.parseDouble(price.getText()));
            int availableQty = no + orders.get(index).getAvaQty();
            System.out.println("Available");
            System.out.println(availableQty);
            orders.get(index).setQty(newQty);
            avQty.setText(String.valueOf(availableQty));
            orders.get(index).setAvaQty(availableQty);
            orders.get(index).setNetPrice(newPrice);
            subTotal.setText(String.valueOf(tot));

            placeTable.refresh();
            itemCode.clear();
            placeDes.clear();
            avQty.clear();
            price.clear();
            placeQty.clear();

            avQty.setEditable(false);
            price.setEditable(false);
            placeDes.setEditable(false);
            itemCode.setEditable(true);
            placeQty.setEditable(true);

        }else if (no == orders.get(index).getQty()){
            orders.remove(index);

            double tot = Double.parseDouble(subTotal.getText()) - (no * Double.parseDouble(price.getText()));
            subTotal.setText(String.valueOf(tot));

            itemCode.clear();
            placeDes.clear();
            avQty.clear();
            price.clear();
            placeQty.clear();

            avQty.setEditable(false);
            price.setEditable(false);
            placeDes.setEditable(false);
            itemCode.setEditable(true);
            placeQty.setEditable(true);

            ObservableList<Order> items = FXCollections.observableArrayList(orders);
            placeTable.setItems(items);

        }else{
            new Alert(Alert.AlertType.ERROR, "Quantity should less than Added quantity", ButtonType.OK).show();
        }

    }

    public void placeHolder(ActionEvent actionEvent) {
        String orderId = oID.getText();
        String day = currentDate.getText();
        String Name = cName.getText();
        System.out.println("Check Name");
        System.out.println(Name);
        String cId = cID.getText();
        String total =subTotal.getText();

            boolean result = ManagePlaceOrder.addItems(orderId,day,cId,Name,placeTable.getItems(),total);
        if (result) {
            new Alert(Alert.AlertType.INFORMATION, "Successfully Saved", ButtonType.OK).showAndWait();
            perOrders.add(new Order(orderId, day, cId, Name, placeTable.getItems(), total));
            ObservableList<Order> oItems = FXCollections.observableArrayList(perOrders);
            System.out.println("Order");
            System.out.println(perOrders.get(0).getcName());
            System.out.println("Hiii");
                for (int i = 0;i<orders.size();i++){
                    for (int j = 0; j< inventory.size(); j++){
                    if (orders.get(i).getICode().equals(inventory.get(j).getCode())){
                        inventory.get(j).setQty(orders.get(i).getAvaQty());
                        ObservableList<Inventory> item = FXCollections.observableArrayList(inventory);
                        System.out.println(orders.get(i).getAvaQty());
                        System.out.println(inventory.get(j).getQty());
                    }
                }
            }
            System.out.println("Har");
            setOrder();

            orders.clear();
            ObservableList<Order> items = FXCollections.observableArrayList(orders);
            placeTable.setItems(items);

            tot = 0;
            cID.clear();
            cName.clear();
        }
    }

    public void placeHome(ActionEvent actionEvent) throws IOException {
        tot = 0;
        temp = 0;
        Parent parent = FXMLLoader.load(this.getClass().getResource("/lk/ijse/fx/dep/view/mainPage.fxml"));
        Scene scene = new Scene(parent);
        Stage primaryStage = (Stage) frmPlaceOrder.getScene().getWindow();
        primaryStage.setScene(scene);
    }

    public void setOnKeyEnter(KeyEvent keyEvent) {
        cID.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {

                    String cid = cID.getText();
                    for (int j = 0; j< customer.size(); j++){
                        if (customer.get(j) != null && cid.equals(customer.get(j).getId())) {
                            String Name = customer.get(j).getName();
                            cName.setText(Name);
                            cName.setEditable(false);
                            return;
                        }

                    }

                        new Alert(Alert.AlertType.ERROR, "customer not in", ButtonType.OK).show();



                }
            }
        });
        //cName.setEditable(false);

    }

    public void PressEnterForItem(KeyEvent keyEvent) {
        itemCode.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    String item = itemCode.getText();
                    for (int i = 0; i < orders.size(); i++) {

                        if (orders.get(i) != null && item.equals(orders.get(i).getICode())) {
                            String description = orders.get(i).getDes();
                            int qty = orders.get(i).getAvaQty();
                            double uPrice = orders.get(i).getUnitPrice();
                            avQty.setText(String.valueOf(qty));
                            price.setText(String.valueOf(uPrice));
                            placeDes.setText(description);
                            return;
                        }
                    }
                        for (int j = 0; j< inventory.size(); j++){
                            if (inventory.get(j) != null && item.equals(inventory.get(j).getCode())) {
                                String description = inventory.get(j).getDescription();
                                int qty = inventory.get(j).getQty();
                                double uPrice = inventory.get(j).getPrice();
                                System.out.println(qty);
                                placeDes.setText(description);
                                avQty.setText(String.valueOf(qty));
                                price.setText(String.valueOf(uPrice));

                                placeDes.setEditable(false);
                                avQty.setEditable(false);
                                price.setEditable(false);

                                return;
                            }

                        }

                       new Alert(Alert.AlertType.ERROR, "Item not in", ButtonType.OK).show();


                }
            }

        });

    }

    public void setOrder(){

        if (perOrders.size()==0){
            count = 1;
        }
        for (int j = 0;j < perOrders.size();j++){
            if (perOrders.get(j) != null && perOrders.get(j).getOrderID().equals(count)){
                count=count+1;
            }
            break;
        }
        oID.setText(String.valueOf(count));
        count = count + 1;
    }


}
