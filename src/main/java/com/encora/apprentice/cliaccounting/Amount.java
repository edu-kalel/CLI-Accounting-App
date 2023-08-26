package com.encora.apprentice.cliaccounting;

public class Amount {
    private float amount;
    private String unit;

    public Amount(float amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "amount=" + amount +
                ", unit='" + unit + '\'' +
                '}';
    }
}
