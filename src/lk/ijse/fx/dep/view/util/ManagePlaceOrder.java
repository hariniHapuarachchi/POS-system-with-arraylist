package lk.ijse.fx.dep.view.util;

import javafx.collections.ObservableList;
import lk.ijse.fx.dep.dynaminARR.SinglyLinkedList;

public class ManagePlaceOrder {
    private static SinglyLinkedList<Order> usersList = new SinglyLinkedList<>();
    private static Customer currentUser = null;
    private static int currentUserIndex = -1;

    public static boolean addItems(String orderID, String day, String cuID, String cName, ObservableList<Order> items, String subTotal) {

        for (int i = 0; i < usersList.getSize(); i++) {
            Order order = usersList.get(i);
            if (order.getOrderID().equals(orderID)) {
                return false;
            }
        }

        Order perOrders = new Order(orderID, day, cuID, cName,items,subTotal);
        usersList.add(perOrders);
        return true;
    }

}
