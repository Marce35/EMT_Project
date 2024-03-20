package com.example.eshop.labs_project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String continent;

    // Getters and setters
    public Country(String name, String continent){
        this.name = name;
        this.continent = continent;
    }

    public Country(){

    }
}
