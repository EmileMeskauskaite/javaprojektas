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




    public Amplifier(String title, String text, String text1, Warehouse warehouse, String text2, String text3, String text4) {
        super(title, text, text1, warehouse);
        this.type = text2;
        this.model = text3;
    }
}
