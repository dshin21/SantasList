package ca.bcit.ass2.shin_lee;

import java.util.ArrayList;

public class Child {
    public static ArrayList<Child> children;

    String firstName;
    String lastName;
    String DOB;
    String street;
    String city;
    String province;
    String postalCode;
    String country;
    double lat;
    double lng;
    String isNaughty;

    public Child() {
        children = new ArrayList<>();
    }

    public Child(String firstName, String lastName, String DOB,
                 String street, String city, String province,
                 String postalCode, String country, double lat, double lng, String isNaughty) {
        children = new ArrayList<>();

        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.street = street;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
        this.isNaughty = isNaughty;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDOB() {
        return DOB;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getIsNaughty() {
        return isNaughty;
    }
}
