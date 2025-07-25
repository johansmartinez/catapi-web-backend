package com.quiz.jm.catapiweb.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.quiz.jm.catapiweb.dto.CatBreedDto;
import com.quiz.jm.catapiweb.service.CatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/cat/breeds")
@Tag(name = "Cat Breeds", description = "Operations related to cat breeds.")
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @Operation(summary = "Get all cat breeds",
               description = "Retrieves a list of all cat breeds.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "List of cat breeds retrieved successfully",
                                content = @Content(schema = @Schema(implementation = CatBreedDto.class))),
                   @ApiResponse(responseCode = "404", description = "No cat breeds found")
               })
    @GetMapping("/")
    public ResponseEntity<List<CatBreedDto>> getAllBreeds() {
        List<CatBreedDto> breeds = catService.getAllBreeds();
        return ResponseEntity.ok(breeds);
    }

    @Operation(summary = "Get cat breed by ID",
               description = "Retrieves a specific cat breed by its ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Cat breed retrieved successfully",
                                content = @Content(schema = @Schema(implementation = CatBreedDto.class))),
                   @ApiResponse(responseCode = "404", description = "Cat breed not found")
               })
    @GetMapping("/{breed_id}")
    public ResponseEntity<CatBreedDto> getBreedById(@PathVariable("breed_id") String breedId) {
        return catService.getBreedById(breedId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Search cat breeds",
               description = "Searches for cat breeds by name.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Cat breeds found",
                                content = @Content(schema = @Schema(implementation = CatBreedDto.class))),
                   @ApiResponse(responseCode = "404", description = "No cat breeds found")
               })
    @GetMapping("/search")
    public ResponseEntity<List<CatBreedDto>> searchBreeds(@RequestParam String q) {
        List<CatBreedDto> breeds = catService.searchBreeds(q);
        return ResponseEntity.ok(breeds);
    }
}