package com.example.javafxtutorial.view;

import com.example.javafxtutorial.model.Employee;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.util.ArrayList;

public class AddEmployeePageView {

    public VBox createAddEmployeePage(ArrayList<Employee> employees, String roleName) {
        Text titleText = new Text("Add a Employee");
        titleText.setFont(new Font("Berlin Sans FB",24));
        // Left side
        Label empNameLabel = new Label("Name: ");
        empNameLabel.setFont(new Font("Berlin Sans FB",16));
        TextField empNameField = new TextField();
        empNameField.setFont(Font.font("Berlin Sans FB", 16));
        empNameField.setPromptText("Name");

        Label birthdayLabel = new Label("Birthday:");
        birthdayLabel.setFont(new Font("Berlin Sans FB",16));
        TextField birthdayField = new TextField();
        birthdayField.setFont(Font.font("Berlin Sans FB", 16));
        birthdayField.setPromptText("Birthday");

        Label phoneNumLabel = new Label("Phone Number:");
        phoneNumLabel.setFont(new Font("Berlin Sans FB",16));
        TextField phoneNumField = new TextField();
        phoneNumField.setFont(Font.font("Berlin Sans FB", 16));
        phoneNumField.setPromptText("Phone");

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(new Font("Berlin Sans FB",16));
        TextField emailField = new TextField();
        emailField.setFont(Font.font("Berlin Sans FB", 16));
        emailField.setPromptText("Email");


        GridPane leftSection = new GridPane();
        leftSection.setVgap(10);
        leftSection.setHgap(10);
        leftSection.addRow(0, empNameLabel, empNameField);
        leftSection.addRow(1, birthdayLabel, birthdayField);
        leftSection.addRow(2, phoneNumLabel, phoneNumField);
        leftSection.addRow(3, emailLabel, emailField);
        leftSection.setAlignment(Pos.CENTER);


        // Details section
        Label salaryLabel = new Label("Salary:");
        salaryLabel.setFont(new Font("Berlin Sans FB",16));
        TextField salaryField = new TextField();
        salaryField.setFont(Font.font("Berlin Sans FB", 16));
        salaryField.setPromptText("Salary");


        Label roleLabel = new Label("Role:");
        roleLabel.setFont(new Font("Berlin Sans FB",16));
        ComboBox<String> roleComboBox = new ComboBox<>();
        // Add options to the ComboBox
        if (roleName.equals("Manager")){
            roleComboBox.getItems().addAll("Librarian");
        }else{ //its admin
            roleComboBox.getItems().addAll("Librarian", "Manager", "Administrator");
        }
        // default val
        roleComboBox.setValue("Librarian");

        Label userNameLabel = new Label("User Name:");
        userNameLabel.setFont(new Font("Berlin Sans FB",16));
        TextField userNameField = new TextField();
        userNameField.setFont(Font.font("Berlin Sans FB", 16));
        userNameField.setPromptText("Username");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(new Font("Berlin Sans FB",16));
        TextField passwordField = new TextField();
        passwordField.setFont(Font.font("Berlin Sans FB", 16));
        passwordField.setPromptText("Password");

        Button saveButton = new Button("Add Employee");
        saveButton.setBackground(Background.fill(Color.rgb(9,121,121)));
        saveButton.setTextFill(Color.WHITE);
        saveButton.setFont(Font.font("Berlin Sans FB", 16));

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int accessLevel = -1;

                if (areFieldsFilled()) {
                    if (validateInput()){
                        if (roleComboBox.getValue().equals("Librarian")) {
                            accessLevel = 1;
                        } else if (roleComboBox.getValue().equals("Manager")) {
                            accessLevel = 2;
                        } else if (roleComboBox.getValue().equals("Administrator")) {
                            accessLevel = 3;
                        }

                        Employee employee = new Employee(empNameField.getText(), birthdayField.getText(),
                                phoneNumField.getText(), emailField.getText(),
                                Integer.parseInt(salaryField.getText()), accessLevel,
                                userNameField.getText(), passwordField.getText());

                        boolean usernameFound = false;

                        for (Employee existingEmployee : employees) {
                            if (existingEmployee.getUserName().equals(userNameField.getText())){
                                showAlert("Invalid Username", "This username already exists");
                                usernameFound=true;
                                break;
                            }
                        }

                        if (!usernameFound) {
                            employees.add(employee);
                            showAlert("Success", "Employee added successfully");
                        }

                    }
                } else {
                    showAlert("Incomplete Information", "Please fill in all fields about the employee.");
                }

            }
            private boolean areFieldsFilled() {
                return !empNameField.getText().isEmpty() &&
                        !birthdayField.getText().isEmpty() &&
                        !phoneNumField.getText().isEmpty() &&
                        !emailField.getText().isEmpty() &&
                        !salaryField.getText().isEmpty() &&
                        !userNameField.getText().isEmpty() &&
                        !passwordField.getText().isEmpty();
            }

            private boolean validateInput(){
                System.out.println(emailField.getText());
                String emailRegex = "[a-zA-Z]{2,}\\d{2}@epoka\\.edu\\.al";
                if (!emailField.getText().matches(emailRegex)){
                    showAlert("Invalid Email Address", "Please enter a valid email address.");
                    return false;
                }

                String phoneRegex = "\\+355\\s6[7-9]\\s\\d{2}\\s\\d{2}\\s\\d{3}";
                if (!phoneNumField.getText().matches(phoneRegex)){
                    showAlert("Invalid Phone Number", "Please enter a valid phone number.");
                    return false;
                }

                try {
                    Integer.parseInt(salaryField.getText());
                }catch (NumberFormatException e){
                    showAlert("Invalid Salary", "Please enter a valid numeric salary.");
                    return false;
                }

                String passowrdRegex = ".*\\d.*";
                if (!passwordField.getText().matches(passowrdRegex)){
                    showAlert("Invalid Password", "Password must contain at least one number.");
                    return false;
                }
                return true;
            }

        });


        GridPane rightSection = new GridPane();
        rightSection.setVgap(10);
        rightSection.setHgap(10);
        rightSection.addRow(0, salaryLabel, salaryField);
        rightSection.addRow(1, roleLabel, roleComboBox);
        rightSection.addRow(2, userNameLabel, userNameField);
        rightSection.addRow(3, passwordLabel, passwordField);
//        rightSection.add(saveButton, 0, 4);
        rightSection.setAlignment(Pos.CENTER);

        // Main layout
        HBox innerLayout = new HBox(20);
        innerLayout.setAlignment(Pos.CENTER);
        innerLayout.getChildren().addAll(leftSection, rightSection);

        VBox mainLayout = new VBox(20);
        mainLayout.setSpacing(60);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(titleText, innerLayout);

        VBox finalLayout = new VBox(10);
        finalLayout.setSpacing(15);
        finalLayout.setAlignment(Pos.CENTER);
        finalLayout.setPadding(new Insets(20,10,100,10));
        finalLayout.getChildren().addAll(mainLayout,saveButton);


        return finalLayout;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
