package model;

import java.time.ZonedDateTime;

public class ReportItem {
    private String month;
    private String kingdom;
    private Integer quantity;

    private String userName;
    private String customerName;
    private ZonedDateTime dateTime;

    private String city;
    private String country;

    public ReportItem() {
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMonth() {
        return month;
    }

    public String getKingdom() {
        return kingdom;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getUserName() {
        return userName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}