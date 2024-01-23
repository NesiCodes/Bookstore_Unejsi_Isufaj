package com.example.javafxtutorial.controller;

import com.example.javafxtutorial.model.CartItem;
import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartController {

    private ArrayList<CartItem> cartItems;

    private int totalQuantity;

    private double totalPrice;

    public CartController(){
        cartItems = new ArrayList<>();
    }

    public CartController(ArrayList<CartItem> cartItems, int totalQuantity, double totalPrice) {
        this.cartItems = cartItems;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(totalPrice));
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addCartItem(CartItem cartItem){
        //check if cart item already exists
        for(CartItem c: cartItems){
            if(c.getBook().getImagePath().equals(cartItem.getBook().getImagePath())){
                //item exists so we just increment quantity
                if(c.getBook().getQuantity()> c.getQuantity()){
                c.setQuantity(c.getQuantity() + 1);
                c.setSubtotal(c.getQuantity() * c.getBook().getPrice());
                calculateCartTotalPrice();
                calculateCartTotalQuantity();
                Alert alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("The quantity increased");
                alert.setHeaderText(null);
                alert.setContentText(c.getBook().getName()+" quantity has been increased");
                alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();}
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No More Books");
                    alert.setHeaderText(null);
                    alert.setContentText("You cannot buy more than "+ c.getBook().getQuantity()+" for " + c.getBook().getName() );

                    alert.show();
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> alert.close());
                    pause.play();
                }
                return;
            }}

        //item doesnt exist so we add it to cart
        this.cartItems.add(cartItem);
        calculateCartTotalPrice();
        calculateCartTotalQuantity();
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("The quantity increased");
        alert.setHeaderText(null);
        alert.setContentText(cartItem.getBook().getName()+" has been added");
        alert.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

    public void removeCartItem(CartItem cartItem){
        this.cartItems.remove(cartItem);
        calculateCartTotalPrice();
        calculateCartTotalQuantity();
    }

    public void calculateCartTotalQuantity(){
        totalQuantity = 0;
        for (CartItem c: cartItems){
            totalQuantity += c.getQuantity();
        }
    }

    public void calculateCartTotalPrice(){
        totalPrice = 0;
        for(CartItem c: cartItems){
            totalPrice += c.getSubtotal();
        }
    }

    @Override
    public String toString() {
        return "CartController{" +
                "cartItems=" + cartItems +
                ", totalQuantity=" + totalQuantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
