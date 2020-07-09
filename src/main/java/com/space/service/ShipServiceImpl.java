package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {

    private ShipRepository shipRepository;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public List<Ship> getShipList() {
        
        return shipRepository.findAll();
    }

    @Override
    public Integer getShipsCount() {
        return (int) shipRepository.count();
    }

    @Override
    public Ship createShip(Ship ship) {
        return shipRepository.save(ship);
    }

    @Override
    public Optional<Ship> getShip(Long id) {
        return shipRepository.findById(id);
    }

    @Override
    public Ship updateShip(Ship ship) {
        return null;
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }
}
