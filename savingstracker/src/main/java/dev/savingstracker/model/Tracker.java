package dev.savingstracker.model;

import lombok.Getter;

@Getter
public class Tracker {

    private String name;
    private String date;
    private String description;
    private int amount;

    public Tracker(String name, String date, String description, int amount) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }
}
