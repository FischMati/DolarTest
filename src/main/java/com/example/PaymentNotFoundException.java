package com.example;

import java.util.UUID;

public class PaymentNotFoundException extends NoSuchFieldException {
    public PaymentNotFoundException(UUID id) {
        super("Could not find payment with UUID " + id.toString());
    }
}
