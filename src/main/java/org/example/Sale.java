package org.example;

public class Sale {
    private int saleId;
    private String employeeName;
    private String region;
    private double amount;

    public Sale(int saleId, String employeeName, String region, double amount) {
        this.saleId = saleId;
        this.employeeName = employeeName;
        this.region = region;
        this.amount = amount;
    }

    public int getSaleId() {
        return saleId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getRegion() {
        return region;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%d | %s | %s | $%.2f",
                saleId, employeeName, region, amount);
    }
}
