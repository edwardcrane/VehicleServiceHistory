package com.airanza.vehicleservicehistory;

import java.io.Serializable;

/**
 * Created by ecrane on 6/12/2015.
 */
public class Vehicle implements Serializable{
    private long id_;
    private String type;
    private String vin;
    private String plate;
    private String nickname;
    private int year;
    private String make;
    private String model;
    private String color;
    private String notes;


    public void setID(long id_) {
        this.id_ = id_;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getID() {
        return id_;
    }

    public String getType() {
        return type;
    }

    public String getVin() {
        return vin;
    }

    public String getPlate() {
        return plate;
    }

    public String getNickname() {
        return nickname;
    }

    public int getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    public String getNotes() {
        return notes;
    }

    public String toString() {
        return nickname + " " + year + " " + make + " " + model + " " + color + " " + vin + " " + type + " " + notes;
    }
}
