package com.quiz.jm.catapiweb.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quiz.jm.catapiweb.dto.ImageDto;
import com.quiz.jm.catapiweb.service.ImageService;

import java.util.List;

@RestController
@RequestMapping("/image") 
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/imagesbybreedid/{breed_id}") 
    public ResponseEntity<List<ImageDto>> getImagesByBreedId(@PathVariable("breed_id") String breedId) {
        List<ImageDto> images = imageService.getImagesByBreedId(breedId);
        return ResponseEntity.ok(images);
    }
}