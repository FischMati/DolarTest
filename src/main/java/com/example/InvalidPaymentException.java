package com.example;

import java.io.IOException;
import java.util.UUID;

public class InvalidPaymentException extends IOException {
    public InvalidPaymentException(UUID id) {
        super("Tried to register invalid payment with UUID " + id.toString());
    }
}
