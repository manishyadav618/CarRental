package com.manish.rental.repository;

import com.manish.rental.model.Customer;
import com.manish.rental.model.RentalTrip;

import java.util.List;

public interface CustomerRentalRepository {

    public void addRentalTrip(Customer customer, RentalTrip trip);

    public void removeRentalTrip(Customer customer, RentalTrip trip);

    public List<RentalTrip> getAllRentalByCustomer(Customer customer);
}
