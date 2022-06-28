package dev.savingstracker.model;

import lombok.Getter;

@Getter
public class Tracker {

    private String sessionID;
    private String name;
    private String date;
    private String description;
    private int amount;

    private int savingsAmount;

    public Tracker(String sessionID, String name, String date, String description, int amount, int savingsAmount) {
        this.sessionID = sessionID;
        this.name = name;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.savingsAmount = savingsAmount;
    }
}
