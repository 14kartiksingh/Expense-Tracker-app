package com.example.expensetracker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Expense {
    private double amount;
    private String description;
    private String dateTime; // 🆕

    public Expense(double amount, String description) {
        this.amount = amount;
        this.description = description;
        this.dateTime = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
                .format(new Date());
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getDateTime() { return dateTime; }

    public void setAmount(double amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
}
