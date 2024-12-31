package com.example.javafxtutorial.view;// Import necessary libraries
import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Order;
import com.example.javafxtutorial.view.AddBookPageView;
import com.example.javafxtutorial.auxiliary.PrintWindow;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddBookPageViewTest extends ApplicationTest {

    private AddBookPageView addBookPageView;
    private ArrayList<Book> books;

    @BeforeEach
    public void setUp() {
        addBookPageView = new AddBookPageView();
        books = new ArrayList<>();
    }

    @Test
    public void testAddBookWithValidData() {
        // Mock user inputs
        Book mockBook = new Book("image.png", "Valid Book", "Fiction", "Pegi", "John Doe", 10, 19.99);
        books.add(mockBook);

        assertEquals(1, books.size());
        assertEquals("Valid Book", books.get(0).getName());
        assertEquals("Fiction", books.get(0).getCategory());
        assertEquals(19.99, books.get(0).getPrice());
    }

    @Test
    public void testAddBookWithEmptyFields() {
        // Mock Alert
        Alert alert = mock(Alert.class);
        // Simulate empty fields
        books.clear();
        addBookPageView.createAddBookPage(books);
        assertEquals(0, books.size());
        verify(alert).showAndWait();
    }

    @Test
    public void testAddBookWithDuplicateName() {
        books.add(new Book("image.png", "Duplicate Book", "Fiction", "Pegi", "John Doe", 10, 19.99));
        addBookPageView.createAddBookPage(books);

        assertEquals(1, books.size()); // Should not add duplicate
    }

    @Test
    public void testAddBookWithInvalidQuantity() {
        books.add(new Book("image.png", "Invalid Quantity", "Fiction", "Pegi", "John Doe", -1, 19.99));
        assertTrue(books.stream().noneMatch(book -> book.getQuantity() < 0));
    }

    @Test
    public void testAddBookWithInvalidPrice() {
        books.add(new Book("image.png", "Invalid Price", "Fiction", "Pegi", "John Doe", 10, -1.0));
        assertTrue(books.stream().noneMatch(book -> book.getPrice() < 0));
    }

}