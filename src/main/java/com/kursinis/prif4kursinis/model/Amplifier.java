package com.kursinis.prif4kursinis.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Amplifier extends Product {
    private String type;
    private String brand;


    public Amplifier(String title, String description, Warehouse warehouse, String manufacturer, String type, String price, String brand) {
        super(title, description, warehouse, manufacturer, price);
        this.type = type;
        this.brand = brand;
    }
    public String getType() {
        System.out.println("Type: " + type);
        return null;
    }
}
