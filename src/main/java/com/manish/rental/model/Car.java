package com.manish.rental.model;

import java.util.Objects;

public class Car {
    /*
       Represnts a Car object. Contains carID, type and rental
       price per day.
     */
    private String carId;
    private CarType type;
    private double pricePerDay;

    public Car(String carId, CarType type, double pricePerDay) {
        this.carId = carId;
        this.type = type;
        this.pricePerDay = pricePerDay;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public CarType getType() {
        return type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(carId, car.carId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carId);
    }

    @Override
    public String toString() {
        return "Car[ CarId = " + carId +
                ", Type = " + type +
                ", PricePerDay=" + pricePerDay +
                "]";
    }
}

