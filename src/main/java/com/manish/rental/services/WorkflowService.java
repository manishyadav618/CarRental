package com.manish.rental.services;

import com.manish.rental.model.CarRentalContext;
import com.manish.rental.model.Customer;
import com.manish.rental.model.PaymentDetails;
import com.manish.rental.model.RentalDates;

public interface WorkflowService {
    /*
        Service class of methods to do work during various stages of car rental
        booking workflow.
     */

    public void doInitialWork(CarRentalContext context);

    public boolean checkTrips(CarRentalContext context, RentalDates rentalDates);

    public void displayAllAvailableCars(CarRentalContext context);

    public boolean chooseCar(CarRentalContext context, String carId);

    public boolean bookCars(CarRentalContext context,Customer customer, PaymentDetails paymentDetails);

    public void showConfirmation(CarRentalContext context);
}
