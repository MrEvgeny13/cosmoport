package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    //TODO
    @GetMapping
    public ResponseEntity<List<Ship>> getShipList() {
        List<Ship> ships = shipService.getShipList();
        return new ResponseEntity<>(ships, HttpStatus.OK);
    }

    /**
     * complete
     */
    @GetMapping("/count")
    public ResponseEntity<Integer> getShipCount() {
        Integer count = shipService.getShipsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //TODO
    @PostMapping()
    public ResponseEntity<Ship> createShip(@RequestBody Ship ship) {
        return new ResponseEntity<>(shipService.createShip(ship), HttpStatus.OK);
    }

    //TODO
    @GetMapping("{id}")
    public ResponseEntity<Ship> getShip(@PathVariable("id") Long id) {
        return shipService.getShip(id).map(ship -> new ResponseEntity<>(ship, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //TODO
    @PostMapping("{id}")
    public ResponseEntity<Ship> updateShip(@PathVariable("id") Long id, @RequestBody Ship ship) {
        ship.setId(id);
        return new ResponseEntity<>(shipService.updateShip(ship), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteShip(@PathVariable("id")  Long id) {
        shipService.deleteShip(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
