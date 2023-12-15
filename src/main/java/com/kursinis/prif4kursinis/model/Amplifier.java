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
    private String model;



    public Amplifier(String title, String description, String manufacturer, Warehouse warehouse, String type, String model, double price) {

        this.type = type;
        this.model = model;
    }
}
