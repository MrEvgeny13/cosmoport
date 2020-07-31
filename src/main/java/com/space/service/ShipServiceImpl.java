package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShipServiceImpl implements ShipService {
    private final ShipRepository shipRepository;

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 3;

    @Autowired
    public ShipServiceImpl(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    @Override
    public Page<Ship> findByCriteria(String name, String planet, ShipType shipType, Long after, Long before,
                                     Boolean isUsed, Double minSpeed, Double maxSpeed, Integer minCrewSize,
                                     Integer maxCrewSize, Double minRating, Double maxRating, ShipOrder order,
                                     Integer pageNumber, Integer pageSize) {

        pageNumber = pageNumber != null ? pageNumber : DEFAULT_PAGE_NUMBER;
        pageSize = pageSize != null ? pageSize : DEFAULT_PAGE_SIZE;
        order = order != null ? order : ShipOrder.ID;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,
                new Sort(Sort.DEFAULT_DIRECTION, order.getFieldName()));

        Specification<Ship> spec = getSpec(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);

        return shipRepository.findAll(spec, pageRequest);
    }

    @Override
    public Long getShipsCount(String name, String planet, ShipType shipType, Long after,
                              Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                              Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {

        Specification<Ship> spec = getSpec(name, planet, shipType, after, before, isUsed, minSpeed,
                maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);

        return shipRepository.count(spec);
    }

    @Override
    public Ship createShip(Ship ship) {
        if (ship.isValid()) {
            ship.calculateRatingAndRound();
            return shipRepository.save(ship);
        } else throw new IllegalArgumentException();
    }

    @Override
    public Optional<Ship> getShip(Long id) {
        return shipRepository.findById(id);
    }

    @Override
    public Ship updateShip(Long id, Ship shipNew) {
        Ship shipOld = shipRepository.findById(id).orElse(null);
        if (shipOld == null) {
            throw new EmptyResultDataAccessException(1);
        }
        shipOld.updateFields(shipNew);
        return createShip(shipOld);
    }

    @Override
    public void deleteShip(Long id) {
        shipRepository.deleteById(id);
    }

    private Specification<Ship> getSpec(String name, String planet, ShipType shipType, Long after,
                                        Long before, Boolean isUsed, Double minSpeed, Double maxSpeed,
                                        Integer minCrewSize, Integer maxCrewSize, Double minRating, Double maxRating) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null)
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));

            if (planet != null)
                predicates.add(builder.like(root.get("planet"), "%" + planet + "%"));

            if (shipType != null)
                predicates.add(builder.equal(root.get("shipType"), shipType));

            if (after != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("prodDate"), new Date(after)));

            if (before != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("prodDate"), new Date(before)));

            if (isUsed != null)
                predicates.add(builder.equal(root.get("isUsed"), isUsed));

            if (minSpeed != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("speed"), minSpeed));

            if (maxSpeed != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("speed"), maxSpeed));

            if (minCrewSize != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("crewSize"), minCrewSize));

            if (maxCrewSize != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("crewSize"), maxCrewSize));

            if (minRating != null)
                predicates.add(builder.greaterThanOrEqualTo(root.get("rating"), minRating));

            if (maxRating != null)
                predicates.add(builder.lessThanOrEqualTo(root.get("rating"), maxRating));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
