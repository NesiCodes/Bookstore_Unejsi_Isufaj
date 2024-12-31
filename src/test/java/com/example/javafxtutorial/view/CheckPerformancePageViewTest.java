package com.example.javafxtutorial.view;

import com.example.javafxtutorial.controller.EmployeeController;
import com.example.javafxtutorial.controller.OrderController;
import com.example.javafxtutorial.model.Employee;
import com.example.javafxtutorial.model.Order;
import com.example.javafxtutorial.model.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckPerformancePageViewTest {

    private OrderController mockOrderController;
    private EmployeeController mockEmployeeController;
    private Stage mockStage;
    private Employee mockEmployee;
    private CheckPerformancePageView view;

    @BeforeEach
    void setUp() {
        mockOrderController = mock(OrderController.class);
        mockEmployeeController = mock(EmployeeController.class);
        mockStage = mock(Stage.class);

        // Correct constructor for Employee
        mockEmployee = new Employee("John Doe", "1990-05-15", "123-456-7890", "johndoe@example.com", 50000, 1, "johndoe", "password123");

        // Mock current logged-in user
        when(mockEmployee.getRole()).thenReturn(new Role("Manager"));
        when(mockEmployee.getUserId()).thenReturn("EMP123");

        view = new CheckPerformancePageView(mockOrderController, mockEmployeeController, mockEmployee, mockStage);
    }

    @Test
    void testGetNumberOfBillsForEmployee_boundaryValues() {
        Employee employee = new Employee("Jane Smith", "1985-10-25", "987-654-3210", "janesmith@example.com", 60000, 2, "janesmith", "password456");
        ArrayList<Order> orders = new ArrayList<>();

        // Create boundary value orders
        orders.add(createOrderWithDate("2024-01-01", employee));
        orders.add(createOrderWithDate("2024-12-31", employee));

        // Mock getOrders() to return the list of orders
        when(mockOrderController.getOrders()).thenReturn(orders);

        // Test with boundary dates
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);

        int result = view.getNumberOfBillsForEmployee(employee, fromDate, toDate);
        assertEquals(2, result, "Number of bills should match boundary values.");
    }

    void testGetMoneyMadeForEmployee_boundaryValues() {
        Employee employee = new Employee("Jane Smith", "1985-10-25", "987-654-3210", "janesmith@example.com", 60000, 2, "janesmith", "password456");
        ArrayList<Order> orders = new ArrayList<>();

        // Create boundary value orders with price
        orders.add(createOrderWithDateAndPrice("2024-01-01", employee, 100.0));
        orders.add(createOrderWithDateAndPrice("2024-12-31", employee, 200.0));

        // Mock getOrders() to return the list of orders
        when(mockOrderController.getOrders()).thenReturn(orders);

        // Test with boundary dates
        LocalDate fromDate = LocalDate.of(2024, 1, 1);
        LocalDate toDate = LocalDate.of(2024, 12, 31);

        double result = view.getMoneyMadeForEmployee(employee, fromDate, toDate);
        assertEquals(300.0, result, "Total money made should match boundary values.");
    }

    @Test
    void testIsBetweenInclusive_classEvaluation() {
        // Equivalence Class Evaluation
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        assertTrue(view.isBetweenInclusive(date, startDate, endDate), "Date should fall within the inclusive range.");
        assertFalse(view.isBetweenInclusive(date, LocalDate.of(2025, 1, 1), endDate), "Date should fall outside the range.");
    }

    @Test
    void testRemoveAllEmployees_codeCoverage() {
        // Mocking the VBox and Nodes
        VBox mockVBox = Mockito.mock(VBox.class);

        // Create a mock ObservableList for children
        ObservableList<Node> mockChildren = FXCollections.observableArrayList();
        HBox mockHBox = new HBox();
        mockHBox.getChildren().add(new ImageView());  // Add an ImageView to the HBox
        mockChildren.add(mockHBox);

        // Mock the getChildren method to return the mock list of children
        when(mockVBox.getChildren()).thenReturn(mockChildren);

        view.removeAllEmployees(mockVBox);

        // Verify that the getChildren method was called
        verify(mockVBox, times(1)).getChildren();
        // Ensure the children list is empty after removal
        assertEquals(0, mockChildren.size(), "All children should be removed from VBox.");
    }

    @Test
    void testCreateEmpPerformanceItem_codeCoverage() {
        // Correct creation of Employee object
        Employee employee = new Employee("Michael Scott", "1970-03-15", "555-123-4567", "michael@dundermifflin.com", 70000, 3, "michaelscott", "password789");
        when(employee.getUserId()).thenReturn("EMP001");
        when(employee.getAccessLevel()).thenReturn(1);

        HBox result = view.createEmpPerformanceItem(employee);

        assertNotNull(result, "Performance item HBox should not be null.");
        assertTrue(result.getChildren().size() > 0, "Performance item should contain child elements.");
    }

    private Order createOrderWithDate(String date, Employee employee) {
        Order order = mock(Order.class);
        when(order.getDateCreated()).thenReturn(java.util.Date.from(LocalDate.parse(date).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        when(order.getEmployee()).thenReturn(employee);
        return order;
    }

    private Order createOrderWithDateAndPrice(String date, Employee employee, double price) {
        Order order = createOrderWithDate(date, employee);
        when(order.getTotalPrice()).thenReturn(price);
        return order;
    }
}
