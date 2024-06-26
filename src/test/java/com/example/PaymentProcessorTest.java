package com.example;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentProcessorTest {
    private UUID senderId = UUID.randomUUID();
    private UUID receiverId = UUID.randomUUID();

    private PaymentProcessor paymentProcessor;

    @BeforeEach
    public void initialize() {
        paymentProcessor = new PaymentProcessor();
    }

    @Test
    public void testShouldRegisterValidPayment() {
        Date paymentDate = new Date();
        PaymentData data = new PaymentData(UUID.randomUUID(), senderId, receiverId, 1.25, paymentDate);
        Payment payment = new Payment(data);

        Assertions.assertDoesNotThrow(() -> paymentProcessor.registerPayment(payment));

        List<Payment> registeredPayments = paymentProcessor.getPayments();

        boolean paymentExists = registeredPayments
            .stream()
            .filter(p -> p.data().id().equals(payment.data().id()) && p.getState() == PaymentState.REGISTERED)
            .count() == 1;

        Assertions.assertTrue(paymentExists);
    }

    public void testRefund() {

    }


    public void testValidatePayment() {

    }
}
