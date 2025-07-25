package com.quiz.jm.catapiweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.quiz.jm.catapiweb.dto.ImageDto;
import java.util.List;

@Service
public class ImageService {

    @Value("${thecatapi.base-url}")
    private String baseUrl;

    @Value("${thecatapi.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        return headers;
    }

    public List<ImageDto> getImagesByBreedId(String breedId) {
        String url = baseUrl + "/images/search?breed_id=" + breedId;
        ResponseEntity<List<ImageDto>> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<ImageDto>>() {},
            createHeaders()
        );
        return response.getBody();
    }
}