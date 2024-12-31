package com.example.javafxtutorial.model;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CartItem implements Serializable {

    private Book book;

    private int quantity;

    private double subtotal;

    public CartItem(Book book, int quantity, double subtotal) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }

        this.book = book;
        this.quantity = quantity;

        // Ensure subtotal is formatted to two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        this.subtotal = Double.parseDouble(decimalFormat.format(subtotal));
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = Math.round(subtotal * 100.0) / 100.0; // Rounds to 2 decimal places
    }


    @Override
    public String toString() {
        return "CartItem{" +
                "book=" + book +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}
