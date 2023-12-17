package com.kursinis.prif4kursinis.fxControllers;

import com.kursinis.prif4kursinis.StartGui;
import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.Product;
import com.kursinis.prif4kursinis.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerTabController {

    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<Product> currentOrder;
    @FXML
    private Label productInfo; // This is the TextArea where product info will be displayed

    private User currentUser;
    private CustomHib customHib;

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setCustomHib(CustomHib customHib) {
        this.customHib = customHib;
        productList.getItems().clear();
        productList.getItems().addAll(customHib.getAllRecords(Product.class));
    }

    public void addToCart() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        currentOrder.getItems().add(selectedProduct);
        productList.getItems().remove(selectedProduct);
    }

    public void removeFromCart() {
        Product productToRemove = currentOrder.getSelectionModel().getSelectedItem();
        productList.getItems().add(productToRemove);
        currentOrder.getItems().remove(productToRemove);
    }

    public void createCart() {
        customHib.addToCart(currentUser.getId(), currentOrder.getItems());
        currentOrder.getItems().clear();
        productList.getItems().addAll(customHib.getAvailableProducts());
    }

    public void leaveComment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("commentTree.fxml"));
        Parent parent = fxmlLoader.load();
        CommentTree commentTree = fxmlLoader.getController();
        commentTree.setData(customHib, currentUser);
        Stage stage = (Stage) productList.getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setTitle("Shop");
        stage.setScene(scene);
        stage.show();
    }


    public void initialize() {
        if (productInfo == null) {
            System.out.println("productInfo is not injected correctly.");
        } else {
            productList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    displayProductInfo(newValue);
                }
            });
        }
    }


    private void displayProductInfo(Product product) {
        double price;
        try {
            // Try to parse the price to double
            price = Double.parseDouble(product.getPrice());
        } catch (NumberFormatException e) {
            // If the price cannot be parsed to double, set it to a default value or display an error message
            price = 0.0; // default value
            System.out.println("Error: Price is not a valid number."); // error message
        }

        // Format the product information as a String
        String productInfoStr = String.format("Name: %s\nPrice: %.2f\nDescription: %s\nManufacturer: %s",
                product.getTitle(), price, product.getDescription(), product.getManufacturer());

        // Display the product information
        productInfo.setText(productInfoStr);
    }
}
