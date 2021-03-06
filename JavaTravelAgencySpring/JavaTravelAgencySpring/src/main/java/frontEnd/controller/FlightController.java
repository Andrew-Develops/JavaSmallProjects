package frontEnd.controller;

import business.dto.FlightDTO;
import business.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class FlightController {

    @Autowired
    FlightService flightService;

    //inseram un flight
    @PostMapping(path = "/insertFlight")
    public ResponseEntity insertFlight(@RequestBody @Valid FlightDTO flightDTO) {
        if (flightService.countFlightNumber(flightDTO.getFlightNumber()) != 0) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(flightDTO.getFlightNumber() + " Flight already in database.");
        }
        flightService.insertFlight(flightDTO);
        return ResponseEntity.ok("Flight number: " + flightDTO.getFlightNumber() + " added into data base");
    }

    //cautam un zbor dupa flightNumber
    @GetMapping(path = "/findFlightByFlightNumber")
    public ResponseEntity findFlightByFlightNumber(@RequestParam String flightNumber) {
        FlightDTO flightDTO = flightService.findFlightByTheFlightNumber(flightNumber);
        if (flightDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No flight found by flight number:" + flightNumber + ".");
        } else {
            return ResponseEntity.ok(flightDTO);
        }
    }


}
