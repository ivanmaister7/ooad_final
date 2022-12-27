package com.example.ooad_final.request;

public enum PaymentTariff {
    TARIFF1,
    TARIFF2,
    TARIFF3;

    public Long toValue() {
        return switch (this) {
            case TARIFF1 -> 100L;
            case TARIFF2 -> 200L;
            case TARIFF3 -> 300L;
        };
    }
}
