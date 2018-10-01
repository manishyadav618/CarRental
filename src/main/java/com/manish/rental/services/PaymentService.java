package com.manish.rental.services;

import com.manish.rental.model.PaymentDetails;

public interface PaymentService {

    public boolean validatePayment(PaymentDetails paymentDetails);
}
