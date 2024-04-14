package com.example.eshop.labs_project.model.dto;

import com.example.eshop.labs_project.model.Author;
import com.example.eshop.labs_project.model.enumerations.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class BookDTO {
    private String name;
    @Enumerated(value = EnumType.STRING)
    private String category;
    @ManyToOne
    private Long authorId;
    private Integer availableCopies;

    public BookDTO(){

    }

    public BookDTO(String name, String category, Long authorId, Integer availableCopies) {
        this.name = name;
        this.category = category;
        this.authorId = authorId;
        this.availableCopies = availableCopies;
    }
}
