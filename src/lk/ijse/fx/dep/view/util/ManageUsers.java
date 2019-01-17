package lk.ijse.fx.dep.view.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lk.ijse.fx.dep.dynaminARR.DynamicArray;
import lk.ijse.fx.dep.dynaminARR.SinglyLinkedList;
import lk.ijse.fx.dep.view.util.Customer;

public class ManageUsers {

    private static SinglyLinkedList<Customer> usersList = new SinglyLinkedList<>();
    private static Customer currentUser = null;
    private static int currentUserIndex = -1;

    public static boolean registerCustomer(String id, String name,String address){

        for (int i = 0; i < usersList.getSize() ; i++) {
            Customer customer = usersList.get(i);
            if (customer.getId().equals(id)){
                new Alert(Alert.AlertType.ERROR, "Can't have duplicate users", ButtonType.OK).show();
                return false;
            }
        }

        Customer customer = new Customer(id, name,address);
        usersList.add(customer);
        return true;
    }

    public static boolean authenticate(String id,String name, String address){

        for (int i = 0; i < usersList.getSize() ; i++) {
            Customer customer = usersList.get(i);
            if (customer.getId().equals(id) &&
                customer.getName().equals(name)&& customer.getAddress().equals(address)){
                currentUser = customer;
                currentUserIndex = i;
                return true;
            }
        }

        return false;

    }

}
