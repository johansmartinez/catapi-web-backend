package com.quiz.jm.catapiweb.dto;


import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class ImageDto {
    @JsonProperty("id")
    private String id;
    @JsonProperty("url")
    private String url;
    // Otros campos relevantes si los hay en la respuesta de la API
}