package model;

import java.time.ZonedDateTime;

public class Observation {
    private Integer observationId;
    private Integer surveyId;
    private Integer userId;
    private String common;
    private String binomial;
    private String location;
    private String kingdom;
    private ZonedDateTime date;

    public Observation() {
    }

    public void setObservationId(Integer observationId) {
        this.observationId = observationId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public void setBinomial(String binomial) {
        this.binomial = binomial;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setKingdom(String kingdom) {
        this.kingdom = kingdom;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Integer getObservationId() {
        return observationId;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCommon() {
        return common;
    }

    public String getBinomial() {
        return binomial;
    }

    public String getLocation() {
        return location;
    }

    public String getKingdom() {
        return kingdom;
    }

    public ZonedDateTime getDate() {
        return date;
    }

}