package lk.ijse.fx.dep.view.util;

import javafx.collections.ObservableList;

public class Order {

    private String orderID;
    private String cuID;
    //private final ObservableList<placeTable> items;
    private String ICode;
    //    int available;
    private double unitPrice;
    private double netPrice;
    private double Total;
    private String day;
    private String cName;
    private String des;
    private int qty;
    private double subTotal;
    private int AvaQty;
    private ObservableList<Order> items;
    private String customerName;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Order(String ICode, String des, double unitPrice, int qty, double netPrice, int AvaQty) {
        this.ICode = ICode;
        this.des = des;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.netPrice = netPrice;
        this.AvaQty = AvaQty;
    }

    public Order(String orderID, String day, String cuID, String cName, ObservableList<Order> items, String subTotal) {

        this.orderID = orderID;
        this.day = day;
        this.cuID = cuID;
        this.cName = cName;
        this.items = items;
        this.subTotal = Double.parseDouble(subTotal);

    }

    public Order(String orderID, String day, String cuID, String cName) {
        this.orderID = orderID;
        this.day = day;
        this.cuID = cuID;
        this.cName = cName;
        this.customerName = this.cName;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCuID() {
        return cuID;
    }

    public void setCuID(String cuID) {
        this.cuID = cuID;
    }

    public String getICode() {
        return ICode;
    }

    public void setICode(String ICode) {
        this.ICode = ICode;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getAvaQty() {
        return AvaQty;
    }

    public void setAvaQty(int AvaQty) {
        this.AvaQty = AvaQty;
    }

    public ObservableList<Order> getItems() {
        return items;
    }

    public void setItems(ObservableList<Order> items) {
        this.items = items;
    }
}
