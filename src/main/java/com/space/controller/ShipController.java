package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import java.util.Optional;


@RestController
@RequestMapping("/rest/ships")
public class ShipController {
    private ShipService shipService;

    @Autowired
    public ShipController(ShipService shipService) {
        this.shipService = shipService;
    }

    @GetMapping
    public ResponseEntity<List<Ship>> findByCriteria(@RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String planet,
                                                     @RequestParam(required = false) ShipType shipType,
                                                     @RequestParam(required = false) Long after,
                                                     @RequestParam(required = false) Long before,
                                                     @RequestParam(required = false) Boolean isUsed,
                                                     @RequestParam(required = false) Double minSpeed,
                                                     @RequestParam(required = false) Double maxSpeed,
                                                     @RequestParam(required = false) Integer minCrewSize,
                                                     @RequestParam(required = false) Integer maxCrewSize,
                                                     @RequestParam(required = false) Double minRating,
                                                     @RequestParam(required = false) Double maxRating,
                                                     @RequestParam(required = false) ShipOrder order,
                                                     @RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer pageSize) {

        return new ResponseEntity<>(shipService.findByCriteria(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating, order, pageNumber, pageSize).getContent(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getShipCount(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) String planet,
                                             @RequestParam(required = false) ShipType shipType,
                                             @RequestParam(required = false) Long after,
                                             @RequestParam(required = false) Long before,
                                             @RequestParam(required = false) Boolean isUsed,
                                             @RequestParam(required = false) Double minSpeed,
                                             @RequestParam(required = false) Double maxSpeed,
                                             @RequestParam(required = false) Integer minCrewSize,
                                             @RequestParam(required = false) Integer maxCrewSize,
                                             @RequestParam(required = false) Double minRating,
                                             @RequestParam(required = false) Double maxRating) {

        Long count = shipService.getShipsCount(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
                minCrewSize, maxCrewSize, minRating, maxRating);

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        try {
            return new ResponseEntity<>(shipService.createShip(ship), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        if (id < 1) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return shipService.getShip(id).map(ship -> new ResponseEntity<>(ship, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id, @RequestBody Ship ship) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Ship updateShip;
        try {
            updateShip = shipService.updateShip(id, ship);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updateShip, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ship> deleteShip(@PathVariable("id")  Long id) {
        if (id < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            shipService.deleteShip(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
