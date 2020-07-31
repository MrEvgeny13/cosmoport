package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ShipService {
    Page<Ship> findByCriteria(String name, String planet, ShipType shipType, Long after, Long before,
                              Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                              Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                              Integer pageNumber, Integer pageSize);

    Long getShipsCount(String name, String planet, ShipType shipType, Long after,
                       Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                       Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating);

    Ship createShip(Ship ship);

    Optional<Ship> getShip(Long id);

    Ship updateShip(Long id, Ship ship);

    void deleteShip(Long id);
}
