package com.kursinis.prif4kursinis.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Entity
public class Instrument extends Product {
    private String type;
    private String brand;
    private String model;


    public Instrument(String title, String description, Warehouse warehouse, String manufacturer, String type, String model, String price, String brand) {
        super(title, description, warehouse, manufacturer, price);
        this.type = type;
        this.brand = brand;
        this.model = model;
    }
}
