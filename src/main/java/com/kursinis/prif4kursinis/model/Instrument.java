package com.kursinis.prif4kursinis.model;

import jakarta.persistence.Entity;
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
    private String model;

    public Instrument(String title, String text, String text1, Warehouse warehouse, String text2, String text3, double price) {
        super(title, text, warehouse, text2, price); // Pass the price as a double
        this.type = text2;
        this.model = text3;
    }
}
