package com.manish.rental.repository.impl;

import com.manish.rental.helper.DateHelper;
import com.manish.rental.model.Car;
import com.manish.rental.model.RentalDates;
import com.manish.rental.repository.CarRentalRepository;
import com.manish.rental.repository.CarRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyCarRentalRepository implements CarRentalRepository{
    public Map<String, List<RentalDates>> carRentalRepository;
    public CarRepository carRepository;

    public DummyCarRentalRepository(CarRepository carRepository){
        this.carRentalRepository = new HashMap<>();
        this.carRepository = carRepository;
    }

    @Override
    public void addRentalToCar(Car car, RentalDates rentalDate) {
        if(carRentalRepository.containsKey(car.getCarId())){
            carRentalRepository.get(car.getCarId()).add(rentalDate);
        }else{
            List<RentalDates> rentalDates = new ArrayList<>();
            rentalDates.add(rentalDate);
            carRentalRepository.put(car.getCarId(), rentalDates);
        }
    }

    @Override
    public void removeRentalFromCar(Car car, RentalDates rentalDate) {
        if(carRentalRepository.containsKey(car.getCarId())){
            carRentalRepository.get(car.getCarId()).remove(rentalDate);
        }
    }

    @Override
    public List<Car> getAllCarsAvailableInRentalPeriod(RentalDates rentalDates) {
        List<Car> availableCars = new ArrayList<>();
        outer:
        for(Car car : carRepository.getAllCars().values()){
            if(carRentalRepository.containsKey(car.getCarId())){
                for(RentalDates date: carRentalRepository.get(car.getCarId())){
                    if(DateHelper.doRentalsOverLap(date, rentalDates)){
                        continue outer;
                    }
                }
                availableCars.add(car);
            }else{
                availableCars.add(car);
            }
        }
        return availableCars;
    }
}
