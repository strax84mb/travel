package com.htec.travel.airports;

public enum AirportDst {
    E("Europe"),
    A("US/Canada"),
    S("South America"),
    O("Australia"),
    Z("New Zealand"),
    N("None"),
    U("Unknown");

    private String description;

    AirportDst(String description) {
        this.description = description;
    }
}
