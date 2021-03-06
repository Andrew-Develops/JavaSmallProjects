package frontEnd.controller;

import business.dto.CityDTO;
import business.service.CityService;
import business.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CityController {
    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;

    //inseram un oras
    @PostMapping(path = "/insertCity")
    public ResponseEntity insertCity(@RequestBody @Valid CityDTO cityDTO) {
        if (cityService.countCity(cityDTO.getName()) != 0) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("This city is already in the database.");
        }
        cityService.insertCity(cityDTO);
        return ResponseEntity.ok(cityDTO.getName() + " added.");
    }

    //cautam un oras dupa nume
    @GetMapping(path = "/findCity")
    public ResponseEntity findCity(@RequestParam String name) {
        CityDTO cityDTO = cityService.findCity(name);
        if (cityDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(name + " no city with that name in the database.");
        }
        return ResponseEntity.ok(cityDTO);
    }

    //schimbam numele unui oras
    @PutMapping(path = "/changeCityName")
    public ResponseEntity changeCityName(@RequestParam String name, String newName) {
        if (cityService.countCity(newName) != 0) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(newName + " there is already a city with that name in database");
        }
        int result = cityService.changeCityName(name, newName);
        if (result == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(name + " was not found in database");
        } else {
            return ResponseEntity.ok("City: " + name + " changed into " + newName);
        }
    }

    //stergem un oras
    @DeleteMapping(path = "/deleteCity")
    public ResponseEntity deleteCityByName(@RequestParam String name) {
        if (cityService.countCity(name) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(name + " can't found that city in database");
        }
        cityService.deleteCity(name);
        return ResponseEntity.ok("City: " + name + " deleted from database");
    }

    //cautam un oras dintr-o tara
    @GetMapping(path = "/findCitiesInCountry")
    public ResponseEntity findCitiesInCountry(@RequestParam String countryName) {
        if (countryService.countCountry(countryName) == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(countryName + " no city with that name in database");
        }
        List<CityDTO> cityDTOList = cityService.findCitiesInCountry(countryName);
        if (cityDTOList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for country '" + countryName + "'.");
        }
        return ResponseEntity.ok(cityDTOList);
    }
}
