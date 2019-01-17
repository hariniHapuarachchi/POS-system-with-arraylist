package lk.ijse.fx.dep.view.util;

import lk.ijse.fx.dep.dynaminARR.SinglyLinkedList;

public class ManageItems {
    private static SinglyLinkedList<Inventory> usersList = new SinglyLinkedList<>();

    public static boolean addItems(String code, String des, double price, int qty) {

        for (int i = 0; i < usersList.getSize(); i++) {
            Inventory inventory = usersList.get(i);
            if (inventory.getCode().equals(code)) {
                return false;
            }
        }

        Inventory inventory = new Inventory(code, des, price, qty);
        usersList.add(inventory);
        return true;
    }

    public static boolean confirm(String code, String des, double price, int qty) {

        for (int i = 0; i < usersList.getSize(); i++) {
            Inventory inventory = usersList.get(i);
            if (inventory.getCode().equals(code) &&
                    inventory.getDescription().equals(des) && inventory.getPrice().equals(price) && inventory.getQty() == qty) {

                return true;
            }
        }

        return false;

    }

}
