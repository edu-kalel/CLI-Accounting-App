package com.encora.apprentice.cliaccounting;

public class Amount {
    private float amount;
    private String unit;

    public Amount() {
    }

    public Amount(float amount) {
        this.amount = amount;
    }

    public Amount(float amount, String unit) {
        this.amount = amount;
        this.unit = unit;
    }
    public Amount(Amount amountRef){
        this.amount=amountRef.getAmount();
        this.unit=amountRef.getUnit();
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
    public void addToAmount(float amount){
        this.amount += amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        if (unit=="$"){
            return unit+String.format("%.2f", amount);
        }
        else {
            return String.format("%.2f", amount)+" "+unit;
        }
    }
}
