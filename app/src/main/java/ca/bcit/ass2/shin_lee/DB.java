package ca.bcit.ass2.shin_lee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    public long add(String firstName, String lastName, String DOB, String street, String city, String province,
                    String postalCode, String country, double lat, double lng, String isNaughty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("BirthDate", DOB);
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

        Child temp = new Child();

        temp.getChildren().add(new Child(firstName, lastName, DOB, street, city, province, postalCode, country, lat, lng, isNaughty));
//        MainActivity.adapter.notifyDataSetChanged();


        return id;
    }

    public ArrayList<Child> get() {
        ArrayList<Child> children = new ArrayList<>();

        for (int i = 0; i < getFirstName().size(); ++i) {
            children.add(new Child(
                    getFirstName().get(i),
                    getLastName().get(i),
                    getDOB().get(i),
                    getStreet().get(i),
                    getCity().get(i),
                    getProvince().get(i),
                    getPostalCode().get(i),
                    getCountry().get(i),
                    Double.parseDouble(getLatitude().get(i)),
                    Double.parseDouble(getLongitude().get(i)),
                    getIsNaughty().get(i)));
        }


        return children;
    }

    public List<String> getFirstName() {
        String selectQuery = "SELECT FirstName FROM " + "SANTASLIST";

        return getHelper(selectQuery, "FirstName");
    }

    public List<String> getLastName() {
        String selectQuery = "SELECT LastName FROM " + "SANTASLIST";

        return getHelper(selectQuery, "LastName");
    }

    public List<String> getDOB() {
        String selectQuery = "SELECT BirthDate FROM " + "SANTASLIST";

        return getHelper(selectQuery, "BirthDate");
    }

    public List<String> getStreet() {
        String selectQuery = "SELECT Street FROM " + "SANTASLIST";

        return getHelper(selectQuery, "Street");
    }

    public List<String> getCity() {
        String selectQuery = "SELECT City FROM " + "SANTASLIST";

        return getHelper(selectQuery, "City");
    }

    public List<String> getProvince() {
        String selectQuery = "SELECT Province FROM " + "SANTASLIST";

        return getHelper(selectQuery, "Province");
    }

    public List<String> getPostalCode() {
        String selectQuery = "SELECT PostalCode FROM " + "SANTASLIST";

        return getHelper(selectQuery, "PostalCode");
    }

    public List<String> getCountry() {
        String selectQuery = "SELECT Country FROM " + "SANTASLIST";

        return getHelper(selectQuery, "Country");
    }

    public List<String> getLatitude() {
        String selectQuery = "SELECT Latitude FROM " + "SANTASLIST";

        return getHelper(selectQuery, "Latitude");
    }

    public List<String> getLongitude() {
        String selectQuery = "SELECT Longitude FROM " + "SANTASLIST";

        return getHelper(selectQuery, "Longitude");
    }

    public List<String> getIsNaughty() {
        String selectQuery = "SELECT IsNaughty FROM " + "SANTASLIST";

        return getHelper(selectQuery, "IsNaughty");
    }

    public List<String> getHelper(String selectQuery, String columnName) {
        List<String> temp = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                temp.add(cursor.getString(cursor.getColumnIndex(columnName)));
            } while (cursor.moveToNext());
        }

        db.close();

        return temp;
    }
}
