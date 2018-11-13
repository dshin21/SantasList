package ca.bcit.ass2.shin_lee;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DB extends SQLiteOpenHelper {

    private static final String DB_NAME = "SANTASLIST.sqlite";
    private static final int DB_VERSION = 1;
    private Context context;
    private SQLiteDatabase db;


    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS SANTASLIST");
        db.execSQL(createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String createTable() {
        String sql = "";
        sql += "CREATE TABLE SANTASLIST (";
        sql += "_id INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "FirstName TEXT, ";
        sql += "LastName TEXT, ";
        sql += "BirthDate TEXT, ";
        sql += "Street TEXT, ";
        sql += "City TEXT, ";
        sql += "Province TEXT, ";
        sql += "PostalCode TEXT, ";
        sql += "Country TEXT, ";
        sql += "Latitude REAL, ";
        sql += "Longitude REAL, ";
        sql += "IsNaughty INTEGER, ";
        sql += "DateCreated TEXT";
        sql += ");";

        return sql;
    }

    public long add(String firstName, String lastName, String birthday, String street, String city, String province,
                    String postalCode, String country, double lat, double lng, String isNaughty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("BirthDate", birthday);
        values.put("Street", street);
        values.put("City", city);
        values.put("Province", province);
        values.put("PostalCode", postalCode);
        values.put("Country", country);
        values.put("Latitude", lat);
        values.put("Longitude", lng);
        if (isNaughty.equals("Y"))
            values.put("IsNaughty", 1);
        if (isNaughty.equals("N"))
            values.put("IsNaughty", 0);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        values.put("DateCreated", df.format(c));

        long id = db.insert("SANTASLIST", null, values);
        db.close();

        return id;
    }
}
