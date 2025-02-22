package models;

import java.util.Date;

public class Customer {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Date date;

    public Customer(String firstName, String lastName, String address, String phone, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Date getDate() {
        return date;
    }


}
