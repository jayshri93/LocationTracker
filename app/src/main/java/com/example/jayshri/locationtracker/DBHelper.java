package com.example.jayshri.locationtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "location_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_LOCATIONS = "locatios";
    private static final String KEY_ID = "id";
    private static final String KEY_LATITUDE= "latitude";
    private static final String KEY_LONGITUDE = "longitude";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_LOCATIONS_TABLE = "CREATE TABLE "+TABLE_LOCATIONS+" ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_LATITUDE + " REAL NOT NULL, "
                + KEY_LONGITUDE + " REAL NOT NULL, "
                + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);";

        sqLiteDatabase.execSQL(CREATE_LOCATIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST "+ TABLE_LOCATIONS);
        onCreate(sqLiteDatabase);
    }

    public void addLocation(LocationEntity locationEntity){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LATITUDE,locationEntity.getLatitude());
        values.put(KEY_LONGITUDE,locationEntity.getLongitude());
        db.insert(TABLE_LOCATIONS,null,values);
        db.close();
    }

    public List<LocationEntity> getAllLocation(){
        List<LocationEntity> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATIONS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                LocationEntity locationEntity = new LocationEntity();
                locationEntity.set_id(Integer.parseInt(cursor.getString(0)));
                locationEntity.setLatitude(Double.parseDouble(cursor.getString(1)));
                locationEntity.setLongitude(Double.parseDouble(cursor.getString(2)));
                list.add(locationEntity);
            }while(cursor.moveToNext());
        }
        return list;
    }
}
