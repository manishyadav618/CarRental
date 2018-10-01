package com.manish.rental.services.impl;

import com.manish.rental.model.*;
import com.manish.rental.services.CarRentalService;
import com.manish.rental.services.WorkflowService;

import java.util.List;

public class WorkflowServiceImpl implements WorkflowService {

    private CarRentalService carRentalService;

    public WorkflowServiceImpl(CarRentalService carRentalService){
        this.carRentalService = carRentalService;
    }

    @Override
    public void doInitialWork(CarRentalContext context) {
        System.out.println("============Welcome to 'MY CAR RENTAL'============");
        System.out.println();
        System.out.println("Enter start and end dates for the rental");
        System.out.println();
    }

    @Override
    public boolean checkTrips(CarRentalContext context, RentalDates rentalDates) {
        System.out.println("==================Rental Details==================");
        RentalTrip trip = carRentalService.getTripsDuringARentalPeriod(context, rentalDates);
        if(trip != null){
            System.out.println("You have existing trip during rental period.");
            System.out.println(trip.toString());
            System.out.println("Enter another rental period.");
            return true;
        }
        context.setTentativeRentalDates(rentalDates);
        System.out.println();
        return false;
    }

    public void displayAllAvailableCars(CarRentalContext context){
        RentalDates rentalDates = context.getTentativeRentalDates();
        List<Car> availableCars = carRentalService.getAllAvailableCars(rentalDates);
        System.out.println("================ Choose Car ===================");
        System.out.println("All available cars: ");
        for(Car car: availableCars){
            System.out.println(car);
        }
        System.out.println("Choose car for your trip ( enter CarID number)");
    }

    @Override
    public boolean chooseCar(CarRentalContext context , String carID) {
        Car carBooked;
        carBooked = carRentalService.blockCarForTrip(carID, context.getTentativeRentalDates());
        if(carBooked == null){
            System.out.println("Car was not booked. Either it is not available or CarId is incorrect. Enter CarID again.");
            return false;
        }

        RentalTrip trip = new RentalTrip();
        trip.setCar(carBooked);
        trip.setRentalDates(context.getTentativeRentalDates());
        trip.setTripCost(trip.calculateTripCost());
        context.addTrip(trip);

        System.out.println();
        System.out.println("Rentals Selected by you:");
        for(RentalTrip trip1: context.getTrips()){
            System.out.println(trip1.toString());
        }
        return true;
    }

    @Override
    public boolean bookCars(CarRentalContext context, Customer customer, PaymentDetails paymentDetails) {
        System.out.println("================Booking Details===============");
        System.out.println();
        System.out.println("Proceeding to booking the Rentals");
        System.out.println();

        context.setCustomer(customer);
        context.setPaymentDetails(paymentDetails);

        boolean booked = carRentalService.bookCarRentals(context);
        if(booked){
            return true;
        }else{
            System.out.println("Your payment has failed to process. Please provide payment information again.");
            return false;
        }
    }

    @Override
    public void showConfirmation(CarRentalContext context) {
        System.out.println();
        System.out.println("=============Confirmation================");
        System.out.println("Your rental bookings are confirmed");
        System.out.println("Booking Details: ");
        for(RentalTrip trip : context.getTrips()){
            System.out.println(trip.toString());
        }
        System.out.println("Your Credit Card Has been charged : $" + context.getCostOfRental());
        System.out.println();
        System.out.println("Thank you for choosing 'MY CAR RENTAL'.");
    }
}
