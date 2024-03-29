package com.betrybe.museumfinder.controller;

import com.betrybe.museumfinder.dto.MuseumCreationDto;
import com.betrybe.museumfinder.dto.MuseumDto;
import com.betrybe.museumfinder.model.Coordinate;
import com.betrybe.museumfinder.model.Museum;
import com.betrybe.museumfinder.service.MuseumServiceInterface;
import com.betrybe.museumfinder.util.ModelDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Class representing a museumController.
 */
@RestController
@RequestMapping("/museums")
public class MuseumController {
  private final MuseumServiceInterface museumService;

  public MuseumController(MuseumServiceInterface musuemService) {
    this.museumService = musuemService;
  }

  @PostMapping
  public ResponseEntity<Museum>  createMuseum(@RequestBody MuseumCreationDto museumCreationDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(museumService
      .createMuseum(ModelDtoConverter.dtoToModel(museumCreationDto)));
  }

  /**
   * Get closest museum.
   */
  @GetMapping("/closest")
  public ResponseEntity<MuseumDto> getClosestMuseum(
      @RequestParam("lat") Double latitude,
      @RequestParam("lng") Double longitude,
      @RequestParam("max_dist_km") Double maxDistance
  ) {
    Coordinate coordinate = new Coordinate(latitude, longitude);
    // Museum converter = museumService.getClosestMuseum(coordinate, maxDistance);
    // MuseumDto modelConverter = ModelDtoConverter.modelToDto(converter);
    return ResponseEntity.status(HttpStatus.OK)
      .body(ModelDtoConverter.modelToDto(museumService.getClosestMuseum(coordinate, maxDistance)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MuseumDto> getMuseumById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
      .body(ModelDtoConverter.modelToDto(museumService.getMuseum(id)));
  }
}
