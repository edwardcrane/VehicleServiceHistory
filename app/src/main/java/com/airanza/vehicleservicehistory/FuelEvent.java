package com.airanza.vehicleservicehistory;

import java.io.Serializable;

/**
 * Created by ecrane on 6/13/2015.
 */
public class FuelEvent implements Serializable {
    private long id_;
    private long vehicle_id = 0;
    private long timestamp = 0;

    private long distance = 0;
    private String distance_unit;  // miles or kilometers

    private float volume = 0.0f;
    private String volume_unit;

    private int octane = 0;
    private String octane_method;

    private float price_per_unit = 0.00f;
    private String currency;

    private String notes;

    private double gps_latitude = 0.0d;
    private double gps_longitude = 0.0d;

    public long getID() {
        return id_;
    }

    public void setID(long id_) {
        this.id_ = id_;
    }

    public long getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(long vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public void setDistanceUnit(String distance_unit) {
        this.distance_unit = distance_unit;
    }
    public String getDistanceUnit() {
        return distance_unit;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public String getVolume_unit() {
        return volume_unit;
    }

    public void setVolume_unit(String volume_unit) {
        this.volume_unit = volume_unit;
    }

    public int getOctane() {
        return octane;
    }

    public void setOctane(int octane) {
        this.octane = octane;
    }

    public String getOctane_method() {
        return octane_method;
    }

    public void setOctane_method(String octane_method) {
        this.octane_method = octane_method;
    }

    public float getPrice_per_unit() {
        return price_per_unit;
    }

    public void setPrice_per_unit(float price_per_unit) {
        this.price_per_unit = price_per_unit;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getGps_latitude() {
        return gps_latitude;
    }

    public void setGps_latitude(double gps_latitude) {
        this.gps_latitude = gps_latitude;
    }

    public double getGps_longitude() {
        return gps_longitude;
    }

    public void setGps_longitude(double gps_longitude) {
        this.gps_longitude = gps_longitude;
    }
}
