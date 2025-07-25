package com.quiz.jm.catapiweb.dto;


import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty; // Para mapear nombres de campo de la API externa

@Data
public class CatBreedDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("temperament")
    private String temperament;
    @JsonProperty("origin")
    private String origin;
    @JsonProperty("description")
    private String description;
}

