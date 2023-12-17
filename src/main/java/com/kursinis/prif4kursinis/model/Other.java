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


    public Other(String title, String text, String text1, Warehouse warehouse, double v, String text2) {
        super(title, text, text1, warehouse);
        this.weight = v;
    }
}
