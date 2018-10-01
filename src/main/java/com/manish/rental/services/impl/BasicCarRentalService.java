package com.manish.rental.services.impl;

import com.manish.rental.helper.DateHelper;
import com.manish.rental.model.*;
import com.manish.rental.repository.CarRentalRepository;
import com.manish.rental.repository.CarRepository;
import com.manish.rental.repository.CustomerRentalRepository;
import com.manish.rental.services.CarRentalService;
import com.manish.rental.services.PaymentService;

import java.util.List;

public class BasicCarRentalService implements CarRentalService{

    private CustomerRentalRepository customerRentalRepository;
    private CarRepository carRepository;
    private CarRentalRepository carRentalRepository;
    private PaymentService paymentService;

    public BasicCarRentalService(CustomerRentalRepository customerRentalRepository, CarRepository carRepository,
                                 CarRentalRepository carRentalRepository, PaymentService paymentService){
        this.customerRentalRepository = customerRentalRepository;
        this.carRepository = carRepository;
        this.carRentalRepository = carRentalRepository;
        this.paymentService = paymentService;
    }

    @Override
    public RentalTrip getTripsDuringARentalPeriod(CarRentalContext context, RentalDates rentalDates) {
        for(RentalTrip trip : context.getTrips()){
            if(DateHelper.doRentalsOverLap(trip.getRentalDates(), rentalDates)){
                return trip;
            }
        }

        if (context.getCustomer() != null){
            List<RentalTrip> trips = customerRentalRepository.getAllRentalByCustomer(context.getCustomer());
            for(RentalTrip trip : trips){
                if(DateHelper.doRentalsOverLap(trip.getRentalDates(), rentalDates)){
                    return trip;
                }
            }
        }

        return null;
    }

    @Override
    public List<Car> getAllAvailableCars(RentalDates rentalDates) {
        return carRentalRepository.getAllCarsAvailableInRentalPeriod(rentalDates);

    }

    @Override
    public Car blockCarForTrip(String carId, RentalDates dates) {
        Car car = carRepository.getCarByID(carId);
        carRentalRepository.addRentalToCar(car, dates);
        return car;
    }

    @Override
    public boolean bookCarRentals(CarRentalContext carRentalContext) {
        boolean validated = paymentService.validatePayment(carRentalContext.getPaymentDetails());
        if(validated) {
            Customer customer = carRentalContext.getCustomer();
            for (RentalTrip trip : carRentalContext.getTrips()) {
                customerRentalRepository.addRentalTrip(customer, trip);
            }
            return true;

        }else{
            carRentalContext.setPaymentDetails(null);
            for (RentalTrip trip : carRentalContext.getTrips()) {
                carRentalContext.removeTrip(trip);
            }
            carRentalContext.setCostOfRental(0);
            return false;
        }
    }
}
