package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import view_controller.LoginScreenController;

import java.sql.*;

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
//
//    private static Boolean checkObservationOverlap(ZonedDateTime startZDT, ZonedDateTime endZDT) throws SQLException {
//        ObservableList<Observation> allObservations = DBController.getObservations();
//
//        Timestamp startTS = Timestamp.valueOf(startZDT.toLocalDateTime());
//        Timestamp endTS = Timestamp.valueOf(endZDT.toLocalDateTime());
//
//        for(Observation observation:allObservations){
//            ZonedDateTime tempStartZDT = observation.getStart().toInstant().atZone(ZoneId.of("UTC"));
//            ZonedDateTime tempEndZDT = observation.getEnd().toInstant().atZone(ZoneId.of("UTC"));
//            Timestamp thisStart = Timestamp.valueOf(tempStartZDT.toLocalDateTime());
//            Timestamp thisEnd = Timestamp.valueOf(tempEndZDT.toLocalDateTime());
//
//            System.out.println(startTS + " - " + thisStart);
//
//            if(endTS.after(thisStart) && endTS.before(thisEnd)){
//                return true;
//            }else if(startTS.after(thisStart) && endTS.before(thisEnd)){
//                return true;
//            }else if(startTS.before(thisEnd) && endTS.after(thisEnd)){
//                return true;
//            }else if(startTS.equals(thisStart)){
//                return true;
//            }else if(endTS.equals(thisEnd)){
//                return true;
//            }
//        }
//        return false;
//    }
//
    public static Integer addObservation(Observation observation) throws SQLException {
        System.out.println(observation.getDate());
        Timestamp dateTS = Timestamp.valueOf(observation.getDate().toLocalDateTime());

        //if (!checkObservationOverlap(observation.getStart(), observation.getEnd())) {
            //System.out.println(checkObservationOverlap(observation.getStart(), observation.getEnd()));
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
//        } else {
//            System.out.println("Overlapping observation");
//            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
//            emptyFields.setTitle("Error");
//            emptyFields.setHeaderText("Overlapping Observation");
//            emptyFields.setContentText("Please check the observation list and try again.");
//            emptyFields.showAndWait();
//        }
//        return 0;
    }
//
//    public static ObservableList<Observation> getObservations() throws SQLException {
//        ObservableList<Observation> allObservations = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT * FROM observation ORDER BY start");
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            Observation observation = new Observation();
//            observation.setObservationId(Integer.parseInt(rs.getString(1)));
//            observation.setSurveyId(Integer.parseInt(rs.getString(2)));
//            observation.setUserId(Integer.parseInt(rs.getString(3)));
//            observation.setTitle(rs.getString(4));
//            observation.setDescription(rs.getString(5));
//            observation.setLocation(rs.getString(6));
//            observation.setContact(rs.getString(7));
//            observation.setType(rs.getString(8));
//            observation.setUrl(rs.getString(9));
//
//            Timestamp startTS = rs.getTimestamp(10);
//            LocalDateTime startLDT = startTS.toLocalDateTime();
//            ZonedDateTime startUTC = startLDT.atZone(ZoneId.of("UTC"));
//            ZonedDateTime startZDT = startUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//            Timestamp endTS = rs.getTimestamp(11);
//            LocalDateTime endLDT = endTS.toLocalDateTime();
//            ZonedDateTime endUTC = endLDT.atZone(ZoneId.of("UTC"));
//            ZonedDateTime endZDT = endUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//            observation.setStart(startZDT);
//            observation.setEnd(endZDT);
//
//            allObservations.add(observation);
//        }
//        rs.close();
//
//        return allObservations;
//    }
//
//    public static int updateObservation(Observation observation) throws SQLException {
//        Timestamp startTS = Timestamp.valueOf(observation.getStart().toLocalDateTime());
//        Timestamp endTS = Timestamp.valueOf(observation.getEnd().toLocalDateTime());
//
//        if (!checkObservationOverlap(observation.getStart(), observation.getEnd())) {
//            System.out.println(checkObservationOverlap(observation.getStart(), observation.getEnd()));
//            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//            PreparedStatement ps = conn.prepareStatement("UPDATE observation SET surveyId = ?, UserId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = UTC_TIMESTAMP(), lastUpdateBy = ? WHERE observationId = ?");
//            ps.setInt(1, observation.getSurveyId());
//            ps.setInt(2, observation.getUserId());
//            ps.setString(3, observation.getTitle());
//            ps.setString(4, observation.getDescription());
//            ps.setString(5, observation.getLocation());
//            ps.setString(6, observation.getContact());
//            ps.setString(7, observation.getType());
//            ps.setString(8, observation.getUrl());
//            ps.setTimestamp(9, startTS);
//            ps.setTimestamp(10, endTS);
//            ps.setString(11, LoginScreenController.getCurrUser());
//            ps.setInt(12, observation.getObservationId());
//            ps.executeUpdate();
//
//            System.out.println("**Observation update complete**");
//
//            return 1;
//        } else {
//            System.out.println("Overlapping observation");
//            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
//            emptyFields.setTitle("Error");
//            emptyFields.setHeaderText("Overlapping Observation");
//            emptyFields.setContentText("Please check the observation list and try again.");
//            emptyFields.showAndWait();
//        }
//        return 0;
//    }
//
//    public static void deleteObservation(Observation observation) throws SQLException {
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("DELETE FROM observation WHERE observationId = ?");
//        ps.setInt(1, observation.getObservationId());
//        ps.executeUpdate();
//    }
//
//    public static ObservableList<ReportItem> getObservationsByType() throws SQLException {
//        ObservableList<ReportItem> allObservations = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT MONTHNAME(start) AS 'month', type, COUNT(*) AS 'quantity' FROM observation GROUP BY type, MONTH(start)");
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            ReportItem item = new ReportItem();
//
//            item.setMonth(rs.getString(1));
//            item.setType(rs.getString(2));
//            item.setQuantity(Integer.parseInt(rs.getString(3)));
//
//            allObservations.add(item);
//        }
//        rs.close();
//
//        return allObservations;
//    }
//
//    public static ObservableList<ReportItem> getConsultantSchedules() throws SQLException {
//        ObservableList<ReportItem> consultantSchedules = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT user.userName AS 'consultant', survey.title AS 'survey', observation.type, observation.start FROM survey JOIN observation ON survey.surveyId = observation.surveyId JOIN user ON observation.userId = user.userId ORDER BY user.userId, observation.start");
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            ReportItem item = new ReportItem();
//
//            item.setUserName(rs.getString(1));
//            item.setTitle(rs.getString(2));
//            item.setType(rs.getString(3));
//
//            Timestamp startTS = rs.getTimestamp(4);
//            LocalDateTime startLDT = startTS.toLocalDateTime();
//            ZonedDateTime startUTC = startLDT.atZone(ZoneId.of("UTC"));
//            ZonedDateTime startZDT = startUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//            item.setDateTime(startZDT);
//
//            consultantSchedules.add(item);
//        }
//        rs.close();
//
//        return consultantSchedules;
//    }
//
//    public static ObservableList<ReportItem> getSurveysPerCity() throws SQLException {
//        ObservableList<ReportItem> surveysPerCity = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT ci.city, co.country, COUNT(DISTINCT cu.title) AS surveyCount FROM country co JOIN city ci ON co.countryId = ci.countryId LEFT JOIN address a ON ci.cityId = a.cityId LEFT JOIN survey cu ON a.addressId = cu.addressId GROUP BY city");
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            ReportItem item = new ReportItem();
//
//            item.setCity(rs.getString(1));
//            item.setCountry(rs.getString(2));
//            item.setQuantity(Integer.parseInt(rs.getString(3)));
//
//            surveysPerCity.add(item);
//        }
//        rs.close();
//
//        return surveysPerCity;
//    }
//
//    public static Observation checkUpcomingObservations() throws SQLException {
//        Observation observation = new Observation();
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//
//        LocalDateTime nowLDT = LocalDateTime.now();
//        ZonedDateTime nowZDT = nowLDT.atZone(ZoneId.systemDefault());
//        LocalDateTime startLDT = nowZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//        LocalDateTime startLDT15 = startLDT.plusMinutes(15);
//
//        PreparedStatement ps = conn.prepareStatement("SELECT * FROM observation WHERE userId = ? AND (start BETWEEN ? AND ?) ORDER BY start LIMIT 1");
//        ps.setInt(1, LoginScreenController.getCurrUserId());
//        ps.setString(2, String.valueOf(startLDT));
//        ps.setString(3, String.valueOf(startLDT15));
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            observation.setObservationId(Integer.parseInt(rs.getString(1)));
//            observation.setSurveyId(Integer.parseInt(rs.getString(2)));
//            observation.setUserId(Integer.parseInt(rs.getString(3)));
//            observation.setTitle(rs.getString(4));
//            observation.setDescription(rs.getString(5));
//            observation.setLocation(rs.getString(6));
//            observation.setContact(rs.getString(7));
//            observation.setType(rs.getString(8));
//            observation.setUrl(rs.getString(9));
//
//            Timestamp startTS = rs.getTimestamp(10);
//            LocalDateTime startLDT2 = startTS.toLocalDateTime();
//            ZonedDateTime startUTC = startLDT2.atZone(ZoneId.of("UTC"));
//            ZonedDateTime startZDT = startUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//            Timestamp endTS = rs.getTimestamp(11);
//            LocalDateTime endLDT = endTS.toLocalDateTime();
//            ZonedDateTime endUTC = endLDT.atZone(ZoneId.of("UTC"));
//            ZonedDateTime endZDT = endUTC.withZoneSameInstant(ZoneId.systemDefault());
//
//            observation.setStart(startZDT);
//            observation.setEnd(endZDT);
//        }
//
//        rs.close();
//
//        return observation;
//    }
}