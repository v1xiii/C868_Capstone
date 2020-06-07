package model;

import java.time.ZonedDateTime;

public class ReportItem {

    private String month;
    private String kingdom;
    private Integer quantity;

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

    public String getMonth() {
        return month;
    }

    public String getKingdom() {
        return kingdom;
    }

    public Integer getQuantity() {
        return quantity;
    }
}