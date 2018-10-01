package com.manish.rental.services;

import com.manish.rental.model.PaymentDetails;

public interface PaymentService {
    /*
      Service class for payment service.
     */
    public boolean validatePayment(PaymentDetails paymentDetails);
}
