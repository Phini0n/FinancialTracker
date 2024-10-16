package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    BigDecimal amount; // Can be negative (for payment) or positive (for deposit).
    boolean isPayment; // Determines whether the amount is positive (false) or negative (true).

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, BigDecimal amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.isPayment = 0 > amount.signum();
    }

    // Getter and Setter
    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getVendor() { return vendor; }

    public void setVendor(String vendor) { this.vendor = vendor; }

    public BigDecimal getAmount() { return amount; }

    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public boolean isPayment() { return isPayment; }

    public void setPayment(boolean payment) { isPayment = payment; }

    public LocalDate getDate() { return date;
    }

    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getTime() { return time; }

    public void setTime(LocalTime time) { this.time = time; }

    @Override
    public String toString() {
        return this.getDate() + "|" + this.getTime() + "|" + this.getDescription() + "|" +
                this.getVendor() + "|" + this.getAmount();
    }
}