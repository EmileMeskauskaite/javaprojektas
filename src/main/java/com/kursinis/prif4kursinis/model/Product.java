package com.kursinis.prif4kursinis.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String description;
    String manufacturer;
    double price;
    @ManyToOne
    Warehouse warehouse;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    List<Review> reviews;

    @ManyToOne
    Cart cart;

    public Product(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Product(String title, String description, String manufacturer) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
    }

    public Product(String title, String description, String manufacturer, Warehouse warehouse) {
        this.title = title;
        this.description = description;
        this.manufacturer = manufacturer;
        this.warehouse = warehouse;
    }

    public Product(String title, String description, Warehouse warehouse, String manufacturer, String price) {
        this.title = title;
        this.description = description;
        this.warehouse = warehouse;
        this.manufacturer = manufacturer;
        this.price = Double.parseDouble(price);
    }

    @Override
    public String toString() {
        return title;
    }

    @Enumerated(EnumType.STRING)
    private ProductType productType;
    public ProductType getProductType() {
        return this.productType;
    }


    public String getPrice() {
        return String.valueOf(price);
    }
}