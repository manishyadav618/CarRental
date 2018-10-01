package com.manish.rental.repository.impl;

import com.manish.rental.model.Car;
import com.manish.rental.model.CarType;
import com.manish.rental.repository.CarRepository;

import java.util.HashMap;
import java.util.Map;

public class DummyCarRepository implements CarRepository {

    private Map<String, Car> allCars;

    public DummyCarRepository(){
        this.allCars = new HashMap<>();
        allCars.put("CAR1000", new Car("CAR1000", CarType.Compact, 30.0));
        allCars.put("CAR1001", new Car("CAR1001", CarType.Compact, 30.0));

        allCars.put("CAR1002", new Car("CAR1002", CarType.MidSize, 40.0));
        allCars.put("CAR1003", new Car("CAR1003", CarType.MidSize, 40.0));

        allCars.put("CAR1004", new Car("CAR1004", CarType.FullSize, 50.0));

    }

    @Override
    public Map<String, Car> getAllCars() {
        return allCars;
    }

    @Override
    public Car getCarByID(String carID) {
        return allCars.get(carID);
    }
}
