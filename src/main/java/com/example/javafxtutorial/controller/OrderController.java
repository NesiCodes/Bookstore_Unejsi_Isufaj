package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private ArrayList<Order> orders;
    private final String filePath = "src/main/resources/com/example/javafxtutorial/database/orders.dat";

    public OrderController() {
        orders = new ArrayList<>();
        readOrdersFromFile();
    }

    public void readOrdersFromFile() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            orders = (ArrayList<Order>) ois.readObject();
            System.out.println("Orders read from file: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void writeOrdersToFile() {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/orders.dat"))) {
            oos.writeObject(orders);
            System.out.println("Orders written to file: " + "src/main/resources/com/example/javafxtutorial/database/orders.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeOrdersToFile(List<Order> orders) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("src/main/resources/com/example/javafxtutorial/database/orders.dat"))) {
            oos.writeObject(orders);
            System.out.println("Orders written to file: " + "src/main/resources/com/example/javafxtutorial/database/orders.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getFilePath() {
        return filePath;
    }

    public void addOrder(Order o){
        this.orders.add(o);
    }

    public void printOrders(){
        for (Order o: orders){
            System.out.println(o);
        }
    }

    //testing
    public static void main(String[] args) {
        OrderController orderController = new OrderController();

        orderController.printOrders();


        ArrayList<CartItem> cartItems = new ArrayList<>();

        cartItems.add(new CartItem(new Book("book1.png", "Book1", "Adventure", "Botimet Pegi","Author1", 5, 20), 3, 60));


        ArrayList<Order> orders1 = new ArrayList<>();
//        orders1.add(new Order(cartItems, 4, 50, new Employee("Unejsi Isufaj", "6 Jan 2003", "02203051", "unejsi@gmail.com", 4000, 3, "1", "1")));

        orderController.writeOrdersToFile(orders1);
    }

}
