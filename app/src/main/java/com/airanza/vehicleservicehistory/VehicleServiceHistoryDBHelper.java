package com.airanza.vehicleservicehistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ecrane on 6/12/2015.
 */
public class VehicleServiceHistoryDBHelper extends SQLiteOpenHelper {

    // if you change the dataase scheme, you must increment the database version:
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VehicleServiceHistory.db";

    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";

    public static final String COLUMN_ID = "_id";

    public static final String VEHICLE_TABLE_NAME = "vehicle";
    public static final String COLUMN_NAME_VEHICLE_TYPE = "type";
    public static final String COLUMN_NAME_VEHICLE_VIN = "vin";
    public static final String COLUMN_NAME_VEHICLE_PLATE = "plate";
    public static final String COLUMN_NAME_VEHICLE_NICKNAME = "nickname";
    public static final String COLUMN_NAME_VEHICLE_YEAR = "year";
    public static final String COLUMN_NAME_VEHICLE_MAKE = "make";
    public static final String COLUMN_NAME_VEHICLE_MODEL = "model";
    public static final String COLUMN_NAME_VEHICLE_COLOR = "color";
    public static final String COLUMN_NAME_VEHICLE_NOTES = "notes";

    public static final String SQL_CREATE_VEHICLES =
            "CREATE TABLE " + VEHICLE_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_VEHICLE_TYPE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_VIN + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_PLATE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_NICKNAME + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_YEAR + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_MAKE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_MODEL + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_COLOR + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_VEHICLE_NOTES + TEXT_TYPE +
                    " )";

    public static final String SERVICE_HISTORY_TABLE_NAME = "service_history";
    public static final String COLUMN_NAME_SERVICE_EVENT_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_NAME_SERVICE_EVENT_TIMESTAMP = "timestamp";
    public static final String COLUMN_NAME_SERVICE_EVENT_DISTANCE = "distance";
    public static final String COLUMN_NAME_SERVICE_HISTORY_DISTANCE_UNIT = "distance_unit";
    public static final String COLUMN_NAME_SERVICE_EVENT_DESCRIPTION = "description";
    public static final String COLUMN_NAME_SERVICE_EVENT_COST = "cost";
    public static final String COLUMN_NAME_SERVICE_EVENT_CURRENCY_ID = "currency_id";

    public static final String SQL_CREATE_SERVICE_HISTORY =
            "CREATE TABLE " + SERVICE_HISTORY_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_VEHICLE_ID + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_TIMESTAMP + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_DISTANCE + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_SERVICE_HISTORY_DISTANCE_UNIT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_COST + " REAL" + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_CURRENCY_ID + TEXT_TYPE +
                    " )";

    public static final String FUEL_EVENT_TABLE_NAME = "fuel_event";

    public static final String COLUMN_NAME_FUEL_EVENT_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_NAME_FUEL_EVENT_TIMESTAMP = "timestamp";
    public static final String COLUMN_NAME_FUEL_EVENT_DISTANCE = "distance";
    public static final String COLUMN_NAME_SERVICE_EVENT_DISTANCE_UNIT = "distance_unit";
    public static final String COLUMN_NAME_FUEL_EVENT_VOLUME = "volume";
    public static final String COLUMN_NAME_FUEL_EVENT_VOLUME_UNIT = "volume_unit";
    public static final String COLUMN_NAME_FUEL_EVENT_OCTANE = "octane";
    public static final String COLUMN_NAME_FUEL_EVENT_OCTANE_METHOD = "octane_method_id";
    public static final String COLUMN_NAME_FUEL_EVENT_PRICE_PER_UNIT = "price_per_unit";
    public static final String COLUMN_NAME_FUEL_EVENT_CURRENCY = "currency_id";
    public static final String COLUMN_NAME_FUEL_EVENT_NOTES = "notes";
    public static final String COLUMN_NAME_FUEL_EVENT_GPS_LATITUDE = "gps_latitude";
    public static final String COLUMN_NAME_FUEL_EVENT_GPS_LONGITUDE = "gps_longitude";

    public static final String SQL_CREATE_FUEL_HISTORY =
            "CREATE TABLE " + FUEL_EVENT_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_VEHICLE_ID + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_TIMESTAMP + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_DISTANCE + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_SERVICE_EVENT_DISTANCE_UNIT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_VOLUME + " REAL" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_VOLUME_UNIT + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_OCTANE + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_OCTANE_METHOD + " INTEGER" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_PRICE_PER_UNIT + " REAL" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_CURRENCY + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_NOTES + TEXT_TYPE + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_GPS_LATITUDE + " REAL" + COMMA_SEP +
                    COLUMN_NAME_FUEL_EVENT_GPS_LONGITUDE + "REAL" +
                    " )";

    public static final String VOLUME_UNIT_TABLE_NAME = "volume_unit";
    public static final String COLUMN_ID_VOLUME_UNIT_NAME = "name";
    public static final String COLUMN_ID_VOLUME_UNIT_CONVERSION_FACTOR = "conversion_factor";

    public static final String CURRENCY_TABLE_NAME = "currency";
    public static final String COLUMN_ID_CURRENCY_CODE = "code";
    public static final String COLUMN_ID_CURRENCY_DESCRIPTION = "description";
    public static final String COLUMN_ID_CURRENCY_CONVERSION_RATE = "conversion_rate";

    public static final String SQL_CREATE_CURRENCY =
            "CREATE TABLE " + CURRENCY_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    COLUMN_ID_CURRENCY_CODE + TEXT_TYPE + COMMA_SEP +
                    COLUMN_ID_CURRENCY_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    COLUMN_ID_CURRENCY_CONVERSION_RATE + " REAL" +
                    " )";

    public VehicleServiceHistoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_VEHICLES);
        db.execSQL(SQL_CREATE_SERVICE_HISTORY);
        db.execSQL(SQL_CREATE_FUEL_HISTORY);
        db.execSQL(SQL_CREATE_CURRENCY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getName(), " Upgrading database from version " + oldVersion + " to version " + newVersion + ", NOT YET IMPLEMENTED.");
        // TODO:  implement code to upgrade from one version to the next as needed.
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: implement code to downgrade from one version to the next as needed.
    }
}
