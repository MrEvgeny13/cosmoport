package com.space.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import static jdk.nashorn.internal.runtime.Debug.id;

@Entity
@Table(name="ship")
public class Ship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String planet;

    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    private Date prodDate;

    private Boolean isUsed;

    private Double speed;

    private Integer crewSize;

    private Double rating;

    public boolean isValid() {
        if (isUsed == null) isUsed = false;

        return (name != null && !name.equals("") && name.length() <= 50
                && planet != null && !planet.equals("") && planet.length() <= 50
                && shipType != null
                && prodDate != null && prodDate.getYear() + 1900 >= 2800 && prodDate.getYear() + 1900 <= 3019
                && speed != null && !(speed < 0.01) && !(speed > 0.99)
                && crewSize != null && crewSize >= 1 && crewSize <= 9999);
    }

    public void calculateRatingAndRound() {
        rating = (80 * speed * (isUsed ? 0.5 : 1)) / (3019 - (prodDate.getYear() + 1900) + 1);

        rating = BigDecimal.valueOf(rating).setScale(2, RoundingMode.HALF_UP).doubleValue();
        speed = BigDecimal.valueOf(speed).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void updateFields(Ship ship) {
        if (ship.name != null) this.name = ship.name;
        if (ship.planet != null) this.planet = ship.planet;
        if (ship.shipType != null) this.shipType = ship.shipType;
        if (ship.prodDate != null) this.prodDate = ship.prodDate;
        if (ship.isUsed != null) this.isUsed = ship.isUsed;
        if (ship.speed != null) this.speed = ship.speed;
        if (ship.crewSize != null) this.crewSize = ship.crewSize;
        calculateRatingAndRound();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", planet='" + planet + '\'' +
                ", shipType=" + shipType +
                ", prodDate=" + prodDate +
                ", isUsed=" + isUsed +
                ", speed=" + speed +
                ", crewSize=" + crewSize +
                ", rating=" + rating +
                '}';
    }
}
