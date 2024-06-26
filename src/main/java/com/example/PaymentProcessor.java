package com.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * Descripción:
Implementa una clase PaymentProcessor que maneje transacciones de pago. Debe incluir funcionalidades para:

Registrar pagos.
Validar pagos.
Procesar reembolsos.
Generar reportes de transacciones.
Requisitos:
Escribir pruebas unitarias para cada método.
Asegurarse de que el código esté limpio y listo para producción.
Utilizar patrones de diseño adecuados.
 */

public class PaymentProcessor {
    private List<Payment> payments = new ArrayList<>();

    public List<Payment> getPayments() {
        return payments;
    };

    public void registerPayment(Payment payment) throws InvalidPaymentException{
        if(!validatePayment(payment))
            throw new InvalidPaymentException(payment.data().id());

        payments.add(payment);
        payment.setState(PaymentState.REGISTERED);
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
