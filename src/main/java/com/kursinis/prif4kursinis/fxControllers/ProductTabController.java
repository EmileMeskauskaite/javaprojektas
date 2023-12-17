package com.kursinis.prif4kursinis.fxControllers;

import com.kursinis.prif4kursinis.hibernateControllers.CustomHib;
import com.kursinis.prif4kursinis.model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTabController implements Initializable {
    @FXML
    public ListView<Product> productListManager;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public ComboBox<ProductType> productType;
    @FXML
    public ComboBox<Warehouse> warehouseComboBox;
    @FXML
    public TextField TypeField;
    @FXML
    public TextField modelField;
    @FXML
    public TextField priceField;

    @FXML
    public TextField weightField;

    @FXML
    public TextField productManufacturerField;
    @FXML
    public TextField typeField;

    private CustomHib customHib;

    public void setData(CustomHib customHib, User currentUser) {
        this.customHib = customHib;
        warehouseComboBox.getItems().addAll(customHib.getAllRecords(Warehouse.class));
        loadProductListManager();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productType.getItems().addAll(ProductType.values());

        productListManager.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Product selectedProduct = newValue;
                productTitleField.setText(selectedProduct.getTitle());
                productDescriptionField.setText(selectedProduct.getDescription());
                productManufacturerField.setText(selectedProduct.getManufacturer());
                warehouseComboBox.setValue(selectedProduct.getWarehouse());
                productType.setValue(selectedProduct.getProductType());
                priceField.setText(selectedProduct.getPrice());

                if (selectedProduct instanceof Instrument) {
                    Instrument instrument = (Instrument) selectedProduct;
                    typeField.setText(instrument.getType());
                    modelField.setText(instrument.getModel());

                } else if (selectedProduct instanceof Other) {
                    Other other = (Other) selectedProduct;
                    weightField.setText(String.valueOf(other.getWeight()));

                } else if (selectedProduct instanceof Amplifier) {
                    Amplifier amplifier = (Amplifier) selectedProduct;
                    typeField.setText(amplifier.getType());
                    modelField.setText(amplifier.getModel());

                }
            }

            // Call enableProductFields() to enable the priceField based on the selected product type
            enableProductFields();
        });

        // Add a listener to the productType ComboBox
        productType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableProductFields();
        });

        // Call enableProductFields() to enable the priceField by default
        enableProductFields();
    }
    public void enableProductFields() {


        if (productType.getSelectionModel().getSelectedItem() == ProductType.OTHER) {
            modelField.setDisable(true);
            typeField.setDisable(true);
            weightField.setDisable(false);

        }
        else if (productType.getSelectionModel().getSelectedItem()==ProductType.INSTRUMENT)
        {
            weightField.setDisable(true);
            typeField.setDisable(false);
            modelField.setDisable(false);

        }
        else {
            typeField.setDisable(false);
            modelField.setDisable(true);
            weightField.setDisable(false);
        }
    }
    private void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(customHib.getAllRecords(Product.class));
    }

    public void addNewProduct() {
        String title = productTitleField.getText();
        double price = Double.parseDouble(priceField.getText());
        ProductType selectedProductType = productType.getSelectionModel().getSelectedItem();

        if (title == null || title.isEmpty()) {
            System.out.println("Title is required");
            return;
        }

        if (selectedProductType == null) {
            System.out.println("Product type is required");
            return;
        }

        Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
        Warehouse warehouse = customHib.getEntityById(Warehouse.class, selectedWarehouse.getId());

        if (selectedProductType == ProductType.INSTRUMENT) {
            Instrument instrument = new Instrument(title, productDescriptionField.getText(), productManufacturerField.getText(), warehouse, typeField.getText(), modelField.getText(), price);
            instrument.setProductType(selectedProductType);
            customHib.create(instrument);
        } else if (selectedProductType == ProductType.OTHER) {
            Other other = new Other(title, productDescriptionField.getText(), warehouse, productManufacturerField.getText(), Double.parseDouble(weightField.getText()), price);
            other.setProductType(selectedProductType);
            customHib.create(other);
        } else if (selectedProductType == ProductType.AMPLIFIER) {
            Amplifier amplifier = new Amplifier(title, productDescriptionField.getText(), productManufacturerField.getText(), warehouse, typeField.getText(), modelField.getText(), price);
            amplifier.setProductType(selectedProductType);
            customHib.create(amplifier);
        }

        loadProductListManager();
    }

    public void updateProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            selectedProduct.setTitle(productTitleField.getText());
            selectedProduct.setDescription(productDescriptionField.getText());
            selectedProduct.setManufacturer(productManufacturerField.getText());
            selectedProduct.setWarehouse(warehouseComboBox.getValue());
            selectedProduct.setProductType(productType.getValue());
            selectedProduct.setPrice(Double.parseDouble(priceField.getText())); // Pass the price as a String

            if (selectedProduct instanceof Instrument) {
                Instrument instrument = (Instrument) selectedProduct;
                instrument.setType(typeField.getText());
                instrument.setModel(modelField.getText());
            } else if (selectedProduct instanceof Other) {
                Other other = (Other) selectedProduct;
                other.setWeight(Double.parseDouble(weightField.getText()));
            } else if (selectedProduct instanceof Amplifier) {
                Amplifier amplifier = (Amplifier) selectedProduct;
                amplifier.setType(typeField.getText());
                amplifier.setModel(modelField.getText());
            }

            customHib.update(selectedProduct);
        }

        loadProductListManager();
    }

    public void deleteProduct() {
Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            customHib.deleteProduct(selectedProduct.getId());
        }

        loadProductListManager();
    }
}
