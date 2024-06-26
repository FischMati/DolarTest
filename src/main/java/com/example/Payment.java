package com.example;


public class Payment {
    private PaymentData data;
    private PaymentState state;

    public Payment(PaymentData data){
        this.data = data;
        this.state = PaymentState.CREATED;
    }

    public Payment(PaymentData data, PaymentState state){
        this.data = data;
        this.state = state;
    }

    public PaymentData data() {
        return data;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }  
    
    public PaymentState getState() {
        return state;
    }

}
