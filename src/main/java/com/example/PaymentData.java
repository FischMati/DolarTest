package com.example;

import java.util.Date;
import java.util.UUID;

public record PaymentData(UUID id, UUID senderId, UUID receiverID, double amount, Date date) {};
