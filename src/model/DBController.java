package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import view_controller.LoginScreenController;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DBController {

    private static final String DB_NAME = "0mchleUkAZ";
    private static final String DB_URL = "jdbc:mysql://remotemysql.com/"+ DB_NAME +"?autoReconnect=true&useSSL=false";
    private static final String DB_USER = "0mchleUkAZ";
    private static final String DB_PASS = "5T6BxHUEux";

    public static Integer checkLogin(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE userName = ? AND password = ?");
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        Integer retrievedUserId = null;
        String retrievedUsername = null;
        String retrievedPassword = null;

        while (rs.next()) {
            retrievedUserId = rs.getInt(1);
            retrievedUsername = rs.getString(2);
            retrievedPassword = rs.getString(3);
        }
        rs.close();

        if (username.equals(retrievedUsername) && password.equals(retrievedPassword)){
            LoginScreenController.setCurrUser(username);
            return retrievedUserId;
        } else {
            return -1;
        }
    }

    public static void addSurvey(Survey survey) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO survey (title, description, location, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)");
        ps.setString(1, survey.getTitle());
        ps.setString(2, survey.getDescription());
        ps.setString(3, survey.getLocation());
        ps.setInt(4, LoginScreenController.getCurrUserId());
        ps.setInt(5, LoginScreenController.getCurrUserId());
        ps.executeUpdate();
    }

    public static ObservableList<Survey> getSurveys() throws SQLException {
        ObservableList<Survey> allSurveys = FXCollections.observableArrayList();

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT surveyId, title, description, location FROM survey");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Survey survey = new Survey();
            survey.setSurveyId(rs.getInt(1));
            survey.setTitle(rs.getString(2));
            survey.setDescription(rs.getString(3));
            survey.setLocation(rs.getString(4));

            allSurveys.add(survey);
        }
        rs.close();

        return allSurveys;
    }

    public static void deleteSurvey(Survey survey) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        PreparedStatement ps = conn.prepareStatement("DELETE FROM survey WHERE surveyId = ?");
        ps.setInt(1, survey.getSurveyId());
        ps.executeUpdate();
    }

    public static void updateSurvey(Survey survey) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.println("Update started...");
        System.out.println("Updating survey table...");

        PreparedStatement ps = conn.prepareStatement("UPDATE survey SET title = ?, description = ?, location = ?, lastUpdate = UTC_TIMESTAMP(), lastUpdateBy = ? WHERE surveyId = ?");
        ps.setString(1, survey.getTitle());
        ps.setString(2, survey.getDescription());
        ps.setString(3, survey.getLocation());
        ps.setInt(4, LoginScreenController.getCurrUserId());
        ps.setInt(5, survey.getSurveyId());
        ps.executeUpdate();

        System.out.println("Survey table updated");
        System.out.println("**Survey update complete**");
    }

    public static Integer addObservation(Observation observation) throws SQLException {
        System.out.println(observation.getDate());
        Timestamp dateTS = Timestamp.valueOf(observation.getDate().toLocalDateTime());

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("" +
                "INSERT INTO observation (" +
                "survey_id, common_name, binomial_name, location, kingdom, observation_date, createDate, createdBy, lastUpdate, lastUpdateBy" +
                ") " +
                "VALUES (?, ?, ?, ?, ?, ?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)" // wow, there must be a better way to do these prepared statements...
        );

        ps.setInt(1, observation.getSurveyId());
        ps.setString(2, observation.getCommon());
        ps.setString(3, observation.getBinomial());
        ps.setString(4, observation.getLocation());
        ps.setString(5, observation.getKingdom());
        ps.setTimestamp(6, dateTS);
        ps.setInt(7, LoginScreenController.getCurrUserId());
        ps.setInt(8, LoginScreenController.getCurrUserId());
        ps.executeUpdate();

        return 1;
    }

    public static ObservableList<Observation> getObservations() throws SQLException { // all observations
        ObservableList<Observation> allObservations = FXCollections.observableArrayList();

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM observation ORDER BY observation_date");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Observation observation = new Observation();
            observation.setObservationId(Integer.parseInt(rs.getString(1)));
            observation.setSurveyId(Integer.parseInt(rs.getString(2)));
            observation.setKingdom(rs.getString(3));
            observation.setBinomial(rs.getString(4));
            observation.setCommon(rs.getString(5));
            observation.setLocation(rs.getString(6));
            Timestamp startTS = rs.getTimestamp(7);
            observation.setUserId(Integer.parseInt(rs.getString(9)));

            LocalDateTime startLDT = startTS.toLocalDateTime();
            ZonedDateTime startUTC = startLDT.atZone(ZoneId.of("UTC"));
            ZonedDateTime startZDT = startUTC.withZoneSameInstant(ZoneId.systemDefault());

            observation.setDate(startZDT);

            allObservations.add(observation);
        }
        rs.close();

        return allObservations;
    }

    public static ObservableList<Observation> getObservations(String term) throws SQLException { // observations by search term
        ObservableList<Observation> allObservations = FXCollections.observableArrayList();

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM observation WHERE common_name LIKE ? OR binomial_name LIKE ? ORDER BY observation_date");
        ps.setString(1, "%" + term + "%");
        ps.setString(2, "%" + term + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Observation observation = new Observation();
            observation.setObservationId(Integer.parseInt(rs.getString(1)));
            observation.setSurveyId(Integer.parseInt(rs.getString(2)));
            observation.setKingdom(rs.getString(3));
            observation.setBinomial(rs.getString(4));
            observation.setCommon(rs.getString(5));
            observation.setLocation(rs.getString(6));
            Timestamp startTS = rs.getTimestamp(7);
            observation.setUserId(Integer.parseInt(rs.getString(9)));

            LocalDateTime startLDT = startTS.toLocalDateTime();
            ZonedDateTime startUTC = startLDT.atZone(ZoneId.of("UTC"));
            ZonedDateTime startZDT = startUTC.withZoneSameInstant(ZoneId.systemDefault());

            observation.setDate(startZDT);

            allObservations.add(observation);
        }
        rs.close();

        return allObservations;
    }

    public static int updateObservation(Observation observation) throws SQLException {
        Timestamp dateTS = Timestamp.valueOf(observation.getDate().toLocalDateTime());

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("UPDATE observation SET survey_id = ?, common_name = ?, binomial_name = ?, location = ?, kingdom = ?, observation_date = ?, lastUpdate = UTC_TIMESTAMP(), lastUpdateBy = ? WHERE observation_id = ?");
        ps.setInt(1, observation.getSurveyId());
        ps.setString(2, observation.getCommon());
        ps.setString(3, observation.getBinomial());
        ps.setString(4, observation.getLocation());
        ps.setString(5, observation.getKingdom());
        ps.setTimestamp(6, dateTS);
        ps.setInt(7, LoginScreenController.getCurrUserId());
        ps.setInt(8, observation.getObservationId());

        ps.executeUpdate();

        System.out.println("**Observation update complete**");

        return 1;
    }

    public static void deleteObservation(Observation observation) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM observation WHERE observation_id = ?");
        ps.setInt(1, observation.getObservationId());
        ps.executeUpdate();
    }

    public static ObservableList<ReportItem> getObservationsByKingdom() throws SQLException {
        ObservableList<ReportItem> allObservations = FXCollections.observableArrayList();

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT MONTHNAME(observation_date) AS 'month', kingdom, COUNT(*) AS 'quantity' FROM observation GROUP BY kingdom, MONTHNAME(observation_date)");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ReportItem item = new ReportItem();

            item.setMonth(rs.getString(1));
            item.setKingdom(rs.getString(2));
            item.setQuantity(Integer.parseInt(rs.getString(3)));

            allObservations.add(item);
        }
        rs.close();

        return allObservations;
    }
}