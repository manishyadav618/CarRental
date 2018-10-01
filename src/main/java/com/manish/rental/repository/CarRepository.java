package com.manish.rental.repository;

import com.manish.rental.model.Car;

import java.util.Map;

public interface CarRepository {
    public Map<String, Car> getAllCars();

    public Car getCarByID(String carID);
}
