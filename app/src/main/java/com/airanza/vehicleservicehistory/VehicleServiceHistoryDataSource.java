package com.airanza.vehicleservicehistory;

import android.app.backup.BackupManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleServiceHistoryDataSource {
    private SQLiteDatabase database;
    private VehicleServiceHistoryDBHelper dbHelper;
    private Context mContext;

    private String[] allVehicleColumns = {
            VehicleServiceHistoryDBHelper.COLUMN_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_TYPE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_VIN,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_PLATE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NICKNAME,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_YEAR,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MAKE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MODEL,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_COLOR,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NOTES
    };

    private String[] allServiceHistoryColumns = {
            VehicleServiceHistoryDBHelper.COLUMN_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_VEHICLE_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_TIMESTAMP,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_MILEAGE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_DESCRIPTION,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_COST,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_CURRENCY_ID
    };

    private String[] allFuelEventColumns = {
            VehicleServiceHistoryDBHelper.COLUMN_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VEHICLE_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_TIMESTAMP,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_MILEAGE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME_UNIT_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE_METHOD,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_PRICE_PER_UNIT,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_CURRENCY_ID,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_NOTES,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LATITUDE,
            VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LONGITUDE
    };

    private String[] allCurrencyColumns = {
            VehicleServiceHistoryDBHelper.COLUMN_ID,
            VehicleServiceHistoryDBHelper.COLUMN_ID_CURRENCY_CODE,
            VehicleServiceHistoryDBHelper.COLUMN_ID_CURRENCY_DESCRIPTION,
            VehicleServiceHistoryDBHelper.COLUMN_ID_CURRENCY_CONVERSION_RATE
    };

    public VehicleServiceHistoryDataSource(Context context) {
        mContext = context;
        dbHelper = new VehicleServiceHistoryDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }


    /*
     *  First lets worry about the Vehicle objects
     *
     */

    public Vehicle createVehicle(String type, String vin, String plate, String nickname, int year, String make, String model, String color, String notes) {
        // Create a new map of values, where column names are the keys:
        ContentValues values = new ContentValues();
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_TYPE, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_VIN, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_PLATE, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NICKNAME, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_YEAR, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MAKE, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MODEL, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_COLOR, type);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NOTES, type);

        long newRowId = database.insert(
                VehicleServiceHistoryDBHelper.VEHICLE_TABLE_NAME,
                null,
                values);

        Cursor cursor = database.query(VehicleServiceHistoryDBHelper.VEHICLE_TABLE_NAME,
                allVehicleColumns, VehicleServiceHistoryDBHelper.COLUMN_ID + " = " + newRowId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        Vehicle vehicle = cursorToVehicle(cursor);
        cursor.close();
        requestBackup();
        return (vehicle);
    }

    public Vehicle cursorToVehicle(Cursor cursor) {
        Vehicle vehicle = new Vehicle();
        vehicle.setID(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_ID)));
        vehicle.setVin(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_VIN)));
        vehicle.setPlate(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_PLATE)));
        vehicle.setNickname(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NICKNAME)));
        vehicle.setYear(cursor.getInt(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_YEAR)));
        vehicle.setMake(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MAKE)));
        vehicle.setModel(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MODEL)));
        vehicle.setColor(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_COLOR)));
        vehicle.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NOTES)));

        return vehicle;
    }

    public void update(Vehicle vehicle) {
        long id = vehicle.getID();

        String filterString = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_VIN, vehicle.getVin());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_PLATE, vehicle.getPlate());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NICKNAME, vehicle.getNickname());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_YEAR, vehicle.getYear());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MAKE, vehicle.getMake());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_MODEL, vehicle.getModel());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_COLOR, vehicle.getColor());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NOTES, vehicle.getNotes());

        database.update(VehicleServiceHistoryDBHelper.VEHICLE_TABLE_NAME, args, filterString, null);

        requestBackup();

        Log.w(this.getClass().getName(), "Updated: " + vehicle.getNickname());
    }

    public void deleteVehicle(Vehicle vehicle) {
        long id = vehicle.getID();
        int i = database.delete(VehicleServiceHistoryDBHelper.VEHICLE_TABLE_NAME, VehicleServiceHistoryDBHelper.COLUMN_ID + " = " + id, null);
        if (i != 1) {
            Log.w(this.getClass().getName(), "Deleting [" + vehicle + "] failed.  Delete returned [" + i + "] rows.");
        } else {
            requestBackup();
        }
    }

    public List<Vehicle> findVehicles(String findString) {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        String[] projection = allVehicleColumns;
        String whereClause = VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NICKNAME + " LIKE \'%" + findString + "%\' OR "
                + VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NOTES + " LIKE \'%" + findString + "%\'";

        String sortOrder = VehicleServiceHistoryDBHelper.COLUMN_NAME_VEHICLE_NICKNAME + " COLLATE NOCASE ASC";

        Cursor cursor = database.query(
                VehicleServiceHistoryDBHelper.VEHICLE_TABLE_NAME,    // the table to query
                projection,                                 // the columns to return
                whereClause,            //selection,        // the values for the WHERE clause
                null,              //selectionArgs,    // the values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vehicle vehicle = this.cursorToVehicle(cursor);
            vehicles.add(vehicle);
            cursor.moveToNext();
        }
        return (vehicles);
    }


    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        Cursor cursor = database.query(VehicleServiceHistoryDBHelper.VEHICLE_TABLE_NAME, allVehicleColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Vehicle vehicle = cursorToVehicle(cursor);
            vehicles.add(vehicle);
            cursor.moveToNext();
        }
        cursor.close();
        return vehicles;
    }

    /*
     *  ServiceEvent objects
     *
     */

    public ServiceEvent createServiceHistory(long vehicle_id, long timestamp, long mileage, String description, float cost, long currency_id) {
        // Create a new map of values, where column names are the keys:
        ContentValues values = new ContentValues();

        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_VEHICLE_ID, vehicle_id);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_TIMESTAMP, timestamp);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_MILEAGE, mileage);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_DESCRIPTION, description);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_COST, cost);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_CURRENCY_ID, currency_id);

        long newRowId = database.insert(
                VehicleServiceHistoryDBHelper.SERVICE_HISTORY_TABLE_NAME,
                null,
                values);

        Cursor cursor = database.query(VehicleServiceHistoryDBHelper.SERVICE_HISTORY_TABLE_NAME,
                allServiceHistoryColumns, VehicleServiceHistoryDBHelper.COLUMN_ID + " = " + newRowId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        ServiceEvent serviceEvent = cursorToServiceHistory(cursor);
        cursor.close();
        requestBackup();
        return (serviceEvent);
    }

    public ServiceEvent cursorToServiceHistory(Cursor cursor) {
        ServiceEvent history = new ServiceEvent();
        history.setID(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_ID)));
        history.setVehicle_id(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_VEHICLE_ID)));
        history.setTimestamp(cursor.getInt(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_TIMESTAMP)));
        history.setMileage(cursor.getInt(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_MILEAGE)));
        history.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_DESCRIPTION)));
        history.setCost(cursor.getFloat(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_COST)));
        history.setCurrency_id(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_CURRENCY_ID)));

        return history;
    }

    public void update(ServiceEvent history) {
        long id = history.getID();

        String filterString = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_VEHICLE_ID, history.getVehicle_id());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_TIMESTAMP, history.getTimestamp());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_MILEAGE, history.getMileage());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_DESCRIPTION, history.getDescription());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_COST, history.getCost());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_CURRENCY_ID, history.getCurrency_id());

        database.update(VehicleServiceHistoryDBHelper.SERVICE_HISTORY_TABLE_NAME, args, filterString, null);

        requestBackup();

        Log.w(this.getClass().getName(), "Updated: " + history.getDescription());
    }

    public void deleteServiceHistory(ServiceEvent history) {
        long id = history.getID();
        int i = database.delete(VehicleServiceHistoryDBHelper.SERVICE_HISTORY_TABLE_NAME, VehicleServiceHistoryDBHelper.COLUMN_ID + " = " + id, null);
        if (i != 1) {
            Log.w(this.getClass().getName(), "Deleting [" + history + "] failed.  Delete returned [" + i + "] rows.");
        } else {
            requestBackup();
        }
    }

    public List<ServiceEvent> findServiceHistories(String findString) {
        List<ServiceEvent> histories = new ArrayList<ServiceEvent>();

        String[] projection = allServiceHistoryColumns;
        String whereClause = VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_DESCRIPTION + " LIKE \'%" + findString+ "%\'";

        String sortOrder = VehicleServiceHistoryDBHelper.COLUMN_NAME_SERVICE_HISTORY_TIMESTAMP + " COLLATE NOCASE ASC";

        Cursor cursor = database.query(
                VehicleServiceHistoryDBHelper.SERVICE_HISTORY_TABLE_NAME,    // the table to query
                projection,                                 // the columns to return
                whereClause,            //selection,        // the values for the WHERE clause
                null,              //selectionArgs,    // the values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ServiceEvent history = this.cursorToServiceHistory(cursor);
            histories.add(history);
            cursor.moveToNext();
        }
        return (histories);
    }


    public List<ServiceEvent> getAllServiceHistories() {
        List<ServiceEvent> histories = new ArrayList<ServiceEvent>();

        Cursor cursor = database.query(VehicleServiceHistoryDBHelper.SERVICE_HISTORY_TABLE_NAME, allServiceHistoryColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ServiceEvent history = cursorToServiceHistory(cursor);
            histories.add(history);
            cursor.moveToNext();
        }
        cursor.close();
        return histories;
    }

    /*
     *  FuelEvent objects
     *
     */

    //***
    private long vehicle_id;
    private long timestamp;
    private int mileage;
    private float volume;
    private long volume_unit;
    private int octane;
    private long octane_method_id;
    private float price_per_unit;
    private long currency_id;
    private String notes;
    private double gps_latitude;
    private double gps_longitude;
    //***
    public FuelEvent createFuelHistory(long vehicle_id, long timestamp, long mileage, float volume, long volume_unit, int octane, long octane_method_id, float price_per_unit, long currency_id,
                                            String notes, long gps_latitude, long gps_longitude) {
        // Create a new map of values, where column names are the keys:
        ContentValues values = new ContentValues();

        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VEHICLE_ID, vehicle_id);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_TIMESTAMP, timestamp);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_MILEAGE, mileage);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME, volume);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME_UNIT_ID, volume_unit);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE, octane);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE_METHOD, octane_method_id);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_PRICE_PER_UNIT, price_per_unit);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_CURRENCY_ID, currency_id);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_NOTES, notes);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LATITUDE, gps_latitude);
        values.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LONGITUDE, gps_longitude);

        long newRowId = database.insert(
                VehicleServiceHistoryDBHelper.FUEL_HISTORY_TABLE_NAME,
                null,
                values);

        Cursor cursor = database.query(VehicleServiceHistoryDBHelper.FUEL_HISTORY_TABLE_NAME,
                allFuelEventColumns, VehicleServiceHistoryDBHelper.COLUMN_ID + " = " + newRowId,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        FuelEvent fuelEvent = cursorToFuelEvent(cursor);
        cursor.close();
        requestBackup();
        return (fuelEvent);
    }

    public FuelEvent cursorToFuelEvent(Cursor cursor) {
        FuelEvent history = new FuelEvent();
        history.setVehicle_id(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_ID)));
        history.setVehicle_id(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VEHICLE_ID)));
        history.setTimestamp(cursor.getInt(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_TIMESTAMP)));
        history.setDistance(cursor.getInt(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_MILEAGE)));
        history.setVolume(cursor.getFloat(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME)));
        history.setVolume_unit(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME_UNIT_ID)));
        history.setOctane(cursor.getInt(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE)));
        history.setOctane_method(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE_METHOD)));
        history.setPrice_per_unit(cursor.getFloat(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_PRICE_PER_UNIT)));
        history.setCurrency(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_CURRENCY_ID)));
        history.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_NOTES)));
        history.setGps_latitude(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LATITUDE)));
        history.setGps_longitude(cursor.getLong(cursor.getColumnIndexOrThrow(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LONGITUDE)));

        return history;
    }

    // TODO:  START HERE!!!
    public void update(FuelEvent fuelEvent) {
        long id = fuelEvent.getID();

        String filterString = "_id=" + id;
        ContentValues args = new ContentValues();
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VEHICLE_ID, fuelEvent.getVehicle_id());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_TIMESTAMP, fuelEvent.getTimestamp());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_MILEAGE, fuelEvent.getDistance());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME, fuelEvent.getVolume());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_VOLUME_UNIT_ID, fuelEvent.getVolume_unit());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE, fuelEvent.getOctane());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_OCTANE_METHOD, fuelEvent.getOctane_method());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_PRICE_PER_UNIT, fuelEvent.getPrice_per_unit());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_CURRENCY_ID, fuelEvent.getCurrency());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_NOTES, fuelEvent.getNotes());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LATITUDE, fuelEvent.getGps_latitude());
        args.put(VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_GPS_LONGITUDE, fuelEvent.getGps_longitude());


        database.update(VehicleServiceHistoryDBHelper.FUEL_HISTORY_TABLE_NAME, args, filterString, null);

        requestBackup();

        Log.w(this.getClass().getName(), "Updated: " + fuelEvent.getVolume() + " for " + fuelEvent.getPrice_per_unit() * fuelEvent.getVolume());
    }

    public void deleteFuelHistory(FuelEvent history) {
        long id = history.getID();
        int i = database.delete(VehicleServiceHistoryDBHelper.FUEL_HISTORY_TABLE_NAME, VehicleServiceHistoryDBHelper.COLUMN_ID + " = " + id, null);
        if (i != 1) {
            Log.w(this.getClass().getName(), "Deleting [" + history + "] failed.  Delete returned [" + i + "] rows.");
        } else {
            requestBackup();
        }
    }

    public List<FuelEvent> findFuelHistories(String findString) {
        List<FuelEvent> events = new ArrayList<FuelEvent>();

        String[] projection = allFuelEventColumns;
        String whereClause = VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_NOTES + " LIKE \'%" + findString+ "%\'";

        String sortOrder = VehicleServiceHistoryDBHelper.COLUMN_NAME_FUEL_HISTORY_TIMESTAMP + " COLLATE NOCASE ASC";

        Cursor cursor = database.query(
                VehicleServiceHistoryDBHelper.FUEL_HISTORY_TABLE_NAME,    // the table to query
                projection,                                 // the columns to return
                whereClause,            //selection,        // the values for the WHERE clause
                null,              //selectionArgs,    // the values for the WHERE clause
                null,                                       // don't group the rows
                null,                                       // don't filter by row groups
                sortOrder                                   // The sort order
        );

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FuelEvent event = this.cursorToFuelEvent(cursor);
            events.add(event);
            cursor.moveToNext();
        }
        return (events);
    }


    public List<FuelEvent> getAllFuelEvents() {
        List<FuelEvent> fuelEvents = new ArrayList<FuelEvent>();

        Cursor cursor = database.query(VehicleServiceHistoryDBHelper.FUEL_HISTORY_TABLE_NAME, allFuelEventColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FuelEvent fuelEvent = cursorToFuelEvent(cursor);
            fuelEvents.add(fuelEvent);
            cursor.moveToNext();
        }
        cursor.close();
        return fuelEvents;
    }

    public void requestBackup() {
        BackupManager backupManager = new BackupManager(mContext);
        backupManager.dataChanged();
    }
}
