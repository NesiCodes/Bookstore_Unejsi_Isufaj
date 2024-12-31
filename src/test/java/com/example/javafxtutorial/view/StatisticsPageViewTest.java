package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Book;
import com.example.javafxtutorial.model.CartItem;
import com.example.javafxtutorial.model.Order;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.controller.OrderController;
import com.example.javafxtutorial.controller.BookController;
import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.view.StatisticsPageView;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatisticsPageViewTest {

    @Mock
    private OrderController orderController;
    @Mock
    private BookController bookController;
    @Mock
    private EmployeeController employeeController;

    @Mock
    private Book book;
    @Mock
    private Order order;
    @Mock
    private CartItem cartItem;
    @Mock
    private Employee employee;

    private StatisticsPageView statisticsPageView;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        statisticsPageView = new StatisticsPageView(orderController, bookController, employeeController);
    }

    @Test
    void testGetSoldQuantityForBook() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(book.getIsbn()).thenReturn("12345");
        when(orderController.getOrders()).thenReturn((ArrayList<Order>) List.of(order));

        when(order.getCartItems()).thenReturn((ArrayList<CartItem>) Arrays.asList(cartItem));
        when(cartItem.getBook()).thenReturn(book);
        when(cartItem.getQuantity()).thenReturn(5);

        // Act
        int soldQuantity = statisticsPageView.getSoldQuantityForBook(book, startDate, endDate);

        // Assert
        assertEquals(5, soldQuantity);
    }

    @Test
    void testGetMoneyEarnedFromBook() {
        // Arrange
        when(book.getIsbn()).thenReturn("12345");
        when(orderController.getOrders()).thenReturn((ArrayList<Order>) List.of(order));

        when(order.getCartItems()).thenReturn((ArrayList<CartItem>) List.of(cartItem));
        when(cartItem.getBook()).thenReturn(book);
        when(cartItem.getSubtotal()).thenReturn(100.0);

        // Act
        double moneyEarned = statisticsPageView.getMoneyEarnedFromBook(book);

        // Assert
        assertEquals(100.0, moneyEarned, 0.01);
    }

    @Test
    void testRemoveAllBookStats() {
        // Arrange
        VBox cartPage = new VBox();
        cartPage.getChildren().add(new ImageView());

        // Add HBox with ImageView to simulate a book stat item
        Node bookStatNode = statisticsPageView.createBookStatItem(book);
        cartPage.getChildren().add(bookStatNode);

        // Act
        statisticsPageView.removeAllBookStats(cartPage);

        // Assert
        assertTrue(cartPage.getChildren().isEmpty(), "All book stats should be removed");
    }

    @Test
    void testGetSoldQuantityForBookNoOrders() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(book.getIsbn()).thenReturn("12345");

        // Act
        int soldQuantity = statisticsPageView.getSoldQuantityForBook(book, startDate, endDate);

        // Assert
        assertEquals(0, soldQuantity);
    }

    @Test
    void testGetMoneyEarnedFromBookNoOrders() {
        // Arrange
        when(book.getIsbn()).thenReturn("12345");

        // Act
        double moneyEarned = statisticsPageView.getMoneyEarnedFromBook(book);

        // Assert
        assertEquals(0.0, moneyEarned, 0.01);
    }

    @Test
    void testGetSoldQuantityForBookWithDateFilter() {
        // Arrange
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        when(book.getIsbn()).thenReturn("12345");
        when(orderController.getOrders()).thenReturn((ArrayList<Order>) List.of(order));

        when(order.getCartItems()).thenReturn((ArrayList<CartItem>) List.of(cartItem));
        when(cartItem.getBook()).thenReturn(book);
        when(cartItem.getQuantity()).thenReturn(5);
        when(order.getDateCreated()).thenReturn(new Date()); // Ensure the order is within the date range

        // Act
        int soldQuantity = statisticsPageView.getSoldQuantityForBook(book, startDate, endDate);

        // Assert
        assertEquals(5, soldQuantity);
    }

    @Test
    void testRemoveAllBookStatsEmptyCartPage() {
        // Arrange
        VBox cartPage = new VBox();

        // Act
        statisticsPageView.removeAllBookStats(cartPage);

        // Assert
        assertTrue(cartPage.getChildren().isEmpty(), "Cart page should be empty");
    }
}