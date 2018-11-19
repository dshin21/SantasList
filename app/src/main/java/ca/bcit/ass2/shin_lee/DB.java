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

public class DB extends SQLiteOpenHelper {

    private static final String DB_NAME = "SANTASLIST.sqlite";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private ArrayList<String> attributes;
    private static boolean isInitialized = false;


    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
        attributes = new ArrayList<>();
        attributes.add("FirstName");
        attributes.add("LastName");
        attributes.add("BirthDate");
        attributes.add("Street");
        attributes.add("City");
        attributes.add("Province");
        attributes.add("PostalCode");
        attributes.add("Country");
        attributes.add("Latitude");
        attributes.add("Longitude");
        attributes.add("IsNaughty");
        if (!isInitialized) {
            isInitialized = true;
            add("Daniel", "Johnson", "2013-02-12", "Random", "Vancouver", "BC", "V5I 7H2", "Canada", 123.2, 54.8, "N");
            add("Tom", "Ling", "2013-02-12", "Random", "Vancouver", "BC", "V5I 7H2", "Canada", 123.2, 54.8, "Y");
            add("Pat", "Ray", "2013-02-12", "Random", "Vancouver", "BC", "V5I 7H2", "Canada", 123.2, 54.8, "N");
            add("Bob", "Matthew", "2013-02-12", "Random", "Vancouver", "BC", "V5I 7H2", "Canada", 123.2, 54.8, "Y");
            add("Kim", "Richard", "2013-02-12", "Random", "Vancouver", "BC", "V5I 7H2", "Canada", 123.2, 54.8, "Y");
        }

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

        return id;
    }

    public boolean remove(String[] args) {
        if (args.length != 11) {
            return false;
        }
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM SANTASLIST WHERE");
        sql.append(" FirstName = " + "\'" + args[0] + "\'");
        sql.append(" AND LastName = " + "\'" + args[1] + "\'");
        sql.append(" AND BirthDate = " + "\'" + args[2] + "\'");
        sql.append(" AND Street = " + "\'" + args[3] + "\'");
        sql.append(" AND City = " + "\'" + args[4] + "\'");
        sql.append(" AND Province = " + "\'" + args[5] + "\'");
        sql.append(" AND PostalCode = " + "\'" + args[6] + "\'");
        sql.append(" AND Country = " + "\'" + args[7] + "\'");
        sql.append(" AND Latitude = " + args[8]);
        sql.append(" AND Longitude = " + args[9]);
        sql.append(" AND IsNaughty = " + Integer.valueOf(args[10].charAt(0)) % 2);
        //sql.append(", DateCreated = " + "\'"+ args[+ "\'");

        db.execSQL(sql.toString());
        return true;
    }

    public boolean update(String[] query, String column, String value) {
        if (query.length != 11) {
            return false;
        }
        String colName = convertCriteraToColumn(column);

        //lat, lng, and isNaughty do not need quotes
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE SANTASLIST SET ");
        switch (colName) {
            case "Latitude":
            case "Longitude":
                sql.append(colName + " = " + value);
                break;
            case "IsNaughty":
                sql.append(colName + " = " + Integer.valueOf(value.charAt(0)) % 2);
                break;
            default:
                sql.append(colName + " = " + "\'" + value + "\'");
        }
        sql.append(" WHERE");
        sql.append(" FirstName = " + "\'" + query[0] + "\'");
        sql.append(" AND LastName = " + "\'" + query[1] + "\'");
        sql.append(" AND BirthDate = " + "\'" + query[2] + "\'");
        sql.append(" AND Street = " + "\'" + query[3] + "\'");
        sql.append(" AND City = " + "\'" + query[4] + "\'");
        sql.append(" AND Province = " + "\'" + query[5] + "\'");
        sql.append(" AND PostalCode = " + "\'" + query[6] + "\'");
        sql.append(" AND Country = " + "\'" + query[7] + "\'");
        sql.append(" AND Latitude = " + query[8]);
        sql.append(" AND Longitude = " + query[9]);
        sql.append(" AND IsNaughty = " + Integer.valueOf(query[10].charAt(0)) % 2);

        db.execSQL(sql.toString());
        return true;

    }

    public ArrayList<Child> get() {
        ArrayList<Child> children = new ArrayList<>();
        ArrayList<ArrayList<String>> pp = getHelper();
        for (int i = 0; i < pp.get(0).size(); ++i) {
            children.add(new Child(
                    pp.get(0).get(i),
                    pp.get(1).get(i),
                    pp.get(2).get(i),
                    pp.get(3).get(i),
                    pp.get(4).get(i),
                    pp.get(5).get(i),
                    pp.get(6).get(i),
                    pp.get(7).get(i),
                    Double.parseDouble(pp.get(8).get(i)),
                    Double.parseDouble(pp.get(9).get(i)),
                    pp.get(10).get(i)));
        }

        return children;
    }

    public ArrayList<ArrayList<String>> getHelper() {
        ArrayList<String> allQueries = new ArrayList<>();
        for (String column : attributes)
            allQueries.add("SELECT " + column + " FROM " + "SANTASLIST");

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();

        for (int i = 0; i < allQueries.size(); ++i) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(allQueries.get(i), null);

            if (cursor.moveToFirst()) {
                do {
                    temp.add(cursor.getString(cursor.getColumnIndex(attributes.get(i))));
                } while (cursor.moveToNext());
            }
            result.add(temp);
            temp = new ArrayList<>();
        }

        db.close();

        return result;
    }

    public ArrayList<ArrayList<String>> search(String searchCriteria, String userInput) {
        ArrayList<String> allQueries = new ArrayList<>();
        for (String column : attributes)
            allQueries.add("SELECT " + column + " FROM " + "SANTASLIST" + " WHERE " + convertCriteraToColumn(searchCriteria) + " = \"" + userInput + "\"");

        ArrayList<ArrayList<String>> result = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();

        for (int i = 0; i < allQueries.size(); ++i) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(allQueries.get(i), null);

            if (cursor.moveToFirst()) {
                do {
                    temp.add(cursor.getString(cursor.getColumnIndex(attributes.get(i))));
                } while (cursor.moveToNext());
            }
            result.add(temp);
            temp = new ArrayList<>();
        }

        db.close();

        return result;
    }

    public ArrayList<Child> getSearchValues(String searchCriteria, String userInput) {
        ArrayList<Child> children = new ArrayList<>();
        ArrayList<ArrayList<String>> pp = search(searchCriteria, userInput);
        for (int i = 0; i < pp.get(0).size(); ++i) {
            children.add(new Child(
                    pp.get(0).get(i),
                    pp.get(1).get(i),
                    pp.get(2).get(i),
                    pp.get(3).get(i),
                    pp.get(4).get(i),
                    pp.get(5).get(i),
                    pp.get(6).get(i),
                    pp.get(7).get(i),
                    Double.parseDouble(pp.get(8).get(i)),
                    Double.parseDouble(pp.get(9).get(i)),
                    pp.get(10).get(i)));
        }

        return children;
    }

    String convertCriteraToColumn(String searchCritera) {
        String converted = "";
        switch (searchCritera) {
            case "First Name":
                converted = "FirstName";
                break;
            case "Last Name":
                converted = "LastName";
                break;
            case "Birthday":
                converted = "BirthDate";
                break;
            case "Street":
                converted = "Street";
                break;
            case "City":
                converted = "City";
                break;
            case "Province":
                converted = "Province";
                break;
            case "Postal Code":
                converted = "PostalCode";
                break;
            case "Country":
                converted = "Country";
                break;
            case "Latitude":
                converted = "Latitude";
                break;
            case "Longitude":
                converted = "Longitude";
                break;
            case "Is Naughty?":
                converted = "IsNaughty";
                break;
            default:
                break;
        }
        return converted;
    }
}
