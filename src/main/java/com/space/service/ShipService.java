package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    List<Ship> getShipList();

    Integer getShipsCount();

    Ship createShip(Ship ship);

    Optional<Ship> getShip(Long id);

    Ship updateShip(Ship ship);

    void deleteShip(Long id);
}
