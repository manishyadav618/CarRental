package com.manish.rental.repository;

import com.manish.rental.model.Car;
import com.manish.rental.model.RentalDates;

import java.util.List;

public interface CarRentalRepository {

    public void addRentalToCar(Car car, RentalDates rentalDate);

    public void removeRentalFromCar(Car car, RentalDates rentalDate);

    public List<Car> getAllCarsAvailableInRentalPeriod(RentalDates rentalDates);

}
