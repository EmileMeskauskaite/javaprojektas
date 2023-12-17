package com.kursinis.prif4kursinis.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Other extends Product {
    private double weight;

    public Other(String title, String description, String manufacturer, Warehouse warehouse, double weight) {
        super(title, description, manufacturer, warehouse);
        this.weight = weight;
    }

    public Other(String title, String description, Warehouse warehouse, String manufacturer, double weight, double price) {
        super(title, description, warehouse,manufacturer, price);
        this.weight = weight;
    }
}

