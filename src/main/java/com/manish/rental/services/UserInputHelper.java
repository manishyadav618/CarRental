package com.manish.rental.services;

import com.manish.rental.model.CarRentalContext;
import com.manish.rental.model.Customer;
import com.manish.rental.model.PaymentDetails;
import com.manish.rental.model.RentalDates;

public interface UserInputHelper {

    public RentalDates getRentalDatesFromUser();

    public String getCarChoiceFromUser();

    public boolean wantToBookAnotherTrip();

    public Customer getCustomerDetails(CarRentalContext context);

    public PaymentDetails getPaymentDetails(CarRentalContext context);

}
