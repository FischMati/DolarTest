package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PaymentProcessor {
    private List<Payment> payments = Collections.synchronizedList(new ArrayList<>());

    public List<Payment> getPayments() {
        return payments;
    };

    public void registerPayment(Payment payment) throws InvalidPaymentException{
        if(!validatePayment(payment))
            throw new InvalidPaymentException(payment.data().id());

        payments.add(payment);
        payment.setState(PaymentState.REGISTERED);
    }

    public void processPayments() {
        payments
            .stream()
            .parallel()
            .forEach(p -> {
                synchronized (p) {
                    if(p.getState() == PaymentState.REGISTERED){
                        p.setState(PaymentState.SENDER_CHARGED);
                    }
                }
             }
            );
    }

    public Payment onAmountRecieved(UUID paymentId, UUID userId) throws PaymentNotFoundException {
        Payment payment = payments
            .stream()
            .filter(p -> p.data().id().equals(paymentId) && p.data().senderId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new PaymentNotFoundException(paymentId));

        payment.setState(PaymentState.SENDER_PAID);

        return payment;
    }

    public boolean validatePayment(Payment payment){
        if(payment.data().amount() <= 0)
            return false;

        if(payment.data().receiverID().equals(payment.data().senderId()))
            return false;

        return true;
    }

    public void refund(UUID paymentUuid) throws PaymentNotFoundException, InvalidPaymentException {
        Payment payment = payments
            .stream()
            .filter(p -> p.data().id().equals(paymentUuid))
            .findFirst()
            .orElseThrow(() -> new PaymentNotFoundException(paymentUuid));
        
        payment.setState(PaymentState.REFUNDED);

        registerPayment(
            new Payment(
                new PaymentData(UUID.randomUUID(), payment.data().receiverID(), payment.data().senderId(), payment.data().amount(), new Date())
            )
        );
    }
}
