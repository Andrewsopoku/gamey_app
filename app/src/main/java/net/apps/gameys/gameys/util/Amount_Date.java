package net.apps.gameys.gameys.util;

/**
 * Created by andrews on 9/11/18.
 */

public class Amount_Date {

    private String amount, date;

    public Amount_Date() {
    }

    public Amount_Date(String amount, String date) {
        this.amount = amount;
        this.date = date;

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String name) {
        this.amount = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String year) {
        this.date= year;
    }


}