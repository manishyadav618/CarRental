package com.manish.rental.services;

import com.manish.rental.model.*;

import java.util.List;

public interface CarRentalService {
    /*
        Service class for checking available cars and booking them.
     */

    public RentalTrip getTripsDuringARentalPeriod(CarRentalContext context, RentalDates rentalDates);

    public List<Car> getAllAvailableCars(RentalDates dates);

    public Car blockCarForTrip(String carId, RentalDates dates);

    public boolean bookCarRentals(CarRentalContext carRentalContext);

}
