package com.manish.rental.model;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RentalTrip {
    /*
      Hold a rental. Contains car object and dates for which it has been rented.
     */
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Car car;
    private RentalDates rentalDate;
    private double tripCost;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public RentalDates getRentalDates() {
        return rentalDate;
    }

    public void setRentalDates(RentalDates rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getTripCost() {
        return tripCost;
    }

    public void setTripCost(double tripCost) {
        this.tripCost = tripCost;
    }

    public double calculateTripCost(){
        long days = rentalDate.getCountOfDays();
        return days * car.getPricePerDay();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalTrip that = (RentalTrip) o;
        return Objects.equals(car, that.car) &&
                Objects.equals(rentalDate, that.rentalDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, rentalDate);
    }

    @Override
    public String toString() {
        return "RentalTrip[ Car= " + car.getCarId() +
                ", StartDate = " + rentalDate.getStartDate().format(formatter) +
                ", EndDate = " + rentalDate.getEndDate().format(formatter) +
                ", TripCost = " + tripCost + "]";
    }
}
