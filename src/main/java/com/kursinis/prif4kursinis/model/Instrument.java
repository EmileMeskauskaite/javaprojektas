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


    public Instrument(String title, String description, String manufacturer, Warehouse warehouse, String type, String model,String price) {
        super(title, description, manufacturer, warehouse);
        this.type = type;
        this.model = model;
    }


}
