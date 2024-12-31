package com.example.javafxtutorial.auxiliary;// Import necessary libraries
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

public class PrintWindowTest {

    private PrintWindow printWindow;
    private Order mockOrder;

    @BeforeEach
    public void setUp() {
        mockOrder = Mockito.mock(Order.class);
        printWindow = new PrintWindow(mockOrder);
    }

    @Test
    public void testWriteInformationToFileWithValidData() throws IOException {
        VBox vBox = new VBox(new Label("Order ID: 1234"), new Label("Total Price: $100.00"));
        String testFilePath = "test_output.txt";

        printWindow.writeInformationToFile(testFilePath, vBox);

        // Verify file content
        String content = Files.readString(Paths.get(testFilePath));
        assertTrue(content.contains("Order ID: 1234"));
        assertTrue(content.contains("Total Price: $100.00"));

        Files.deleteIfExists(Paths.get(testFilePath)); // Cleanup
    }

    @Test
    public void testWriteInformationToFileWithEmptyVBox() throws IOException {
        VBox emptyVBox = new VBox();
        String testFilePath = "test_empty_output.txt";

        printWindow.writeInformationToFile(testFilePath, emptyVBox);

        // Verify file is created and empty
        String content = Files.readString(Paths.get(testFilePath));
        assertTrue(content.isEmpty());

        Files.deleteIfExists(Paths.get(testFilePath)); // Cleanup
    }

    @Test
    public void testWriteInformationToFileWithNestedVBox() throws IOException {
        VBox nestedVBox = new VBox(new Label("Header"), new VBox(new Label("Nested Label")));
        String testFilePath = "test_nested_output.txt";

        printWindow.writeInformationToFile(testFilePath, nestedVBox);

        // Verify file content
        String content = Files.readString(Paths.get(testFilePath));
        assertTrue(content.contains("Header"));
        assertTrue(content.contains("Nested Label"));

        Files.deleteIfExists(Paths.get(testFilePath)); // Cleanup
    }
}
