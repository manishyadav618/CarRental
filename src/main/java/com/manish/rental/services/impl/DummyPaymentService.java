package com.manish.rental.services.impl;

import com.manish.rental.model.PaymentDetails;
import com.manish.rental.services.PaymentService;

public class DummyPaymentService implements PaymentService {
    @Override
    public boolean validatePayment(PaymentDetails paymentDetails) {
        return true;
    }
}
