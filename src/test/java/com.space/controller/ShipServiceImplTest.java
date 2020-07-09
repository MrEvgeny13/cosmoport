package com.space.controller;

import com.space.service.ShipService;
import com.space.service.ShipServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ShipServiceImplTest extends AbstractTest {

    @Autowired
    private ShipService shipService;

    @Test
    public void getShipList() {
        System.out.println(shipService.getShipList());
    }
}