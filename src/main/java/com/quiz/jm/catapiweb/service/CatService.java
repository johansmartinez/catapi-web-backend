package com.quiz.jm.catapiweb.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quiz.jm.catapiweb.dto.CatBreedDto;

import java.util.List;
import java.util.Optional;
@Service
public class CatService {

    @Value("${thecatapi.base-url}")
    private String baseUrl;

    @Value("${thecatapi.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        return headers;
    }

    public List<CatBreedDto> getAllBreeds() {
        String url = baseUrl + "/breeds";
        ResponseEntity<List<CatBreedDto>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<CatBreedDto>>() {},
            createHeaders()
        );
        return response.getBody();
    }

    public Optional<CatBreedDto> getBreedById(String breedId) {
        return getAllBreeds().stream()
                   .filter(breed -> breed.getId().equalsIgnoreCase(breedId))
                   .findFirst();
    }

    public List<CatBreedDto> searchBreeds(String query) {
        String url = baseUrl + "/breeds/search?q=" + query;
        ResponseEntity<List<CatBreedDto>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<CatBreedDto>>() {},
            createHeaders()
        );
        return response.getBody();
    }
}