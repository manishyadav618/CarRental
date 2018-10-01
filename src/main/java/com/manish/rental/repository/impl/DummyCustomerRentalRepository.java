package com.manish.rental.repository.impl;

import com.manish.rental.model.Customer;
import com.manish.rental.model.RentalTrip;
import com.manish.rental.repository.CustomerRentalRepository;

import java.util.*;

public class DummyCustomerRentalRepository implements CustomerRentalRepository {

    private Map<Customer, List<RentalTrip>> customerRentals;

    public DummyCustomerRentalRepository(){
        this.customerRentals = new HashMap<>();
    }

    @Override
    public void addRentalTrip(Customer customer, RentalTrip trip) {
        if(customerRentals.containsKey(customer)){
            customerRentals.get(customer).add(trip);
        }else{
            List<RentalTrip> trips = new ArrayList<>();
            trips.add(trip);
            customerRentals.put(customer, trips);
        }
    }

    @Override
    public void removeRentalTrip(Customer customer, RentalTrip trip) {
        if(customerRentals.containsKey(customer)){
            customerRentals.get(customer).remove(trip);
        }
    }

    @Override
    public List<RentalTrip> getAllRentalByCustomer(Customer customer) {
        List<RentalTrip> trips = customerRentals.get(customer);
        Collections.sort(trips, (o1, o2) -> {
            if(o1.getRentalDates().getStartDate().isBefore(o2.getRentalDates().getStartDate())){
                return -1;
            }else if(o1.getRentalDates().getStartDate().isAfter(o2.getRentalDates().getStartDate())) {
                return 1;
            }else{
                return 0;
            }
        });
        return  trips;
    }
}
