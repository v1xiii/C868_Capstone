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

//    public static void addCustomer(Customer customer) throws SQLException {
//        System.out.println("Process started...");
//        System.out.println("addCountry up next... three remaining");
//
//        Integer countryID = addCountry(customer.getCountry());
//        System.out.println("addCountry() returns "+countryID);
//
//        System.out.println("addCity up next... two remaining");
//
//        Integer cityID = addCity(customer.getCity(), countryID);
//        System.out.println("addCity() returns "+cityID);
//
//        System.out.println("addAddress up next... one remaining");
//
//        Integer addressID = addAddress(customer.getAddress1(), customer.getAddress2(), cityID, customer.getPostalCode(), customer.getPhone());
//        System.out.println("addAddress() returns "+addressID);
//
//        System.out.println("**Customer added**");
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, 1, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)");
//        ps.setString(1, customer.getCustomerName());
//        ps.setInt(2, addressID);
//        ps.setString(3, LoginScreenController.getCurrUser());
//        ps.setString(4, LoginScreenController.getCurrUser());
//        ps.executeUpdate();
//    }

    private static Integer addCountry(String country) throws SQLException {
        Integer countryID = null;

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT countryId FROM country WHERE country = ?");
        ps.setString(1, country);
        ResultSet currID = ps.executeQuery();

        if (currID.next()){ // if there is already a matching country
            countryID = currID.getInt(1);
            currID.close();
            return countryID; // return the ID
        } else { // else add a new country
            currID.close();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO country (country, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, country);
            ps2.setString(2, LoginScreenController.getCurrUser());
            ps2.setString(3, LoginScreenController.getCurrUser());
            ps2.executeUpdate();
            ResultSet nextID = ps2.getGeneratedKeys(); // get the autoincrement value that was generated during the last query

            if(nextID.next()){
                countryID = nextID.getInt(1);
                nextID.close();
            }
        }
        return countryID; // and return that as the id
    }

    private static Integer addCity(String city, Integer countryID) throws SQLException {
        Integer cityID = null;

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT cityId FROM city WHERE city = ? AND countryId = ?");
        ps.setString(1, city);
        ps.setInt(2, countryID);
        ResultSet currID = ps.executeQuery();

        if (currID.next()){ // if there is already a matching city
            cityID = currID.getInt(1);
            currID.close();
            return cityID; // return the ID
        } else { // else add a new city
            currID.close();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO city (city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, city);
            ps2.setInt(2, countryID);
            ps2.setString(3, LoginScreenController.getCurrUser());
            ps2.setString(4, LoginScreenController.getCurrUser());
            ps2.executeUpdate();
            ResultSet nextID = ps2.getGeneratedKeys(); // get the autoincrement value that was generated during the last query

            if(nextID.next()){
                cityID = nextID.getInt(1);
                nextID.close();
            }
        }
        return cityID; // and return that as the id
    }

    private static Integer addAddress(String address1, String address2, Integer cityID, String postalCode, String phone) throws SQLException {
        Integer addressID = null;

        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        PreparedStatement ps = conn.prepareStatement("SELECT addressId FROM address WHERE address = ? AND address2 = ? AND cityId = ? AND postalCode = ? AND phone = ?");
        ps.setString(1, address1);
        ps.setString(2, address2);
        ps.setInt(3, cityID);
        ps.setString(4, postalCode);
        ps.setString(5, phone);
        ResultSet currID = ps.executeQuery();

        if (currID.next()){ // if there is already a matching address
            addressID = currID.getInt(1);
            currID.close();
            return addressID; // return the ID
        } else { // else add a new address
            currID.close();
            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO address (address, address2, cityID, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?, ?, ?, ?, ?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)", Statement.RETURN_GENERATED_KEYS);
            ps2.setString(1, address1);
            ps2.setString(2, address2);
            ps2.setInt(3, cityID);
            ps2.setString(4, postalCode);
            ps2.setString(5, phone);
            ps2.setString(6, LoginScreenController.getCurrUser());
            ps2.setString(7, LoginScreenController.getCurrUser());
            ps2.executeUpdate();
            ResultSet nextID = ps2.getGeneratedKeys(); // get the autoincrement value that was generated during the last query

            if(nextID.next()){
                addressID = nextID.getInt(1);
                nextID.close();
            }
        }
        return addressID; // and return that as the id
    }

//    public static ObservableList<Customer> getCustomers() throws SQLException {
//        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("" +
//                "SELECT cu.customerId, cu.customerName, a.address, a.address2, ci.city, co.country, a.postalCode, a.phone, cu.active, a.addressId " +
//                "FROM customer cu " +
//                "LEFT JOIN address a ON cu.addressId = a.addressId " +
//                "LEFT JOIN city ci ON a.cityId = ci.cityId " +
//                "LEFT JOIN country co ON ci.countryId = co.countryId;"
//        );
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            Customer customer = new Customer();
//            customer.setCustomerId(rs.getInt(1));
//            customer.setCustomerName(rs.getString(2));
//            customer.setAddress1(rs.getString(3));
//            customer.setAddress2(rs.getString(4));
//            customer.setCity(rs.getString(5));
//            customer.setCountry(rs.getString(6));
//            customer.setPostalCode(rs.getString(7));
//            customer.setPhone(rs.getString(8));
//            customer.setActive(rs.getBoolean(9));
//            customer.setAddressId(rs.getInt(10));
//
//            allCustomers.add(customer);
//        }
//        rs.close();
//
//        return allCustomers;
//    }
//
//    public static void deleteCustomer(Customer customer) throws SQLException {
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//
//        PreparedStatement ps = conn.prepareStatement("DELETE FROM customer WHERE customerId = ?");
//        ps.setInt(1, customer.getCustomerId());
//        ps.executeUpdate();
//
//        /*
//        PreparedStatement ps2 = conn.prepareStatement("DELETE FROM address WHERE addressId = ?");
//        ps2.setInt(1, customer.getAddressId());
//        ps2.executeUpdate();
//        */
//    }
//
//    public static void updateCustomer(Customer customer) throws SQLException {
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//
//        System.out.println("Update started...");
//        System.out.println("Checking country table...");
//
//        Integer countryId = addCountry(customer.getCountry());
//
//        System.out.println("addCountry() returns "+countryId);
//        System.out.println("Checking city table...");
//
//        Integer cityId = addCity(customer.getCity(), countryId);
//
//        System.out.println("addCity() returns "+cityId);
//        System.out.println("Updating address table...");
//
//        PreparedStatement ps2 = conn.prepareStatement("UPDATE address SET address = ?, address2 = ?,  cityId = ?, postalCode = ?, phone = ?, lastUpdate = UTC_TIMESTAMP(), lastUpdateBy = ? WHERE addressId = ?");
//        ps2.setString(1, customer.getAddress1());
//        ps2.setString(2, customer.getAddress2());
//        ps2.setInt(3, cityId);
//        ps2.setString(4, customer.getPostalCode());
//        ps2.setString(5, customer.getPhone());
//        ps2.setString(6, LoginScreenController.getCurrUser());
//        ps2.setInt(7, customer.getAddressId());
//        ps2.executeUpdate();
//
//        System.out.println("Address table updated");
//        System.out.println("Updating customer table...");
//
//        PreparedStatement ps = conn.prepareStatement("UPDATE customer SET customerName = ?, lastUpdate = UTC_TIMESTAMP(), lastUpdateBy = ? WHERE customerId = ?");
//        ps.setString(1, customer.getCustomerName());
//        ps.setString(2, LoginScreenController.getCurrUser());
//        ps.setInt(3, customer.getCustomerId());
//        ps.executeUpdate();
//
//        System.out.println("Customer table updated");
//        System.out.println("**Customer update complete**");
//    }
//
//    private static Boolean checkAppointmentOverlap(ZonedDateTime startZDT, ZonedDateTime endZDT) throws SQLException {
//        ObservableList<Appointment> allAppointments = DBController.getAppointments();
//
//        Timestamp startTS = Timestamp.valueOf(startZDT.toLocalDateTime());
//        Timestamp endTS = Timestamp.valueOf(endZDT.toLocalDateTime());
//
//        for(Appointment appointment:allAppointments){
//            ZonedDateTime tempStartZDT = appointment.getStart().toInstant().atZone(ZoneId.of("UTC"));
//            ZonedDateTime tempEndZDT = appointment.getEnd().toInstant().atZone(ZoneId.of("UTC"));
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
//    public static Integer addAppointment(Appointment appointment) throws SQLException {
//
//        Timestamp startTS = Timestamp.valueOf(appointment.getStart().toLocalDateTime());
//        Timestamp endTS = Timestamp.valueOf(appointment.getEnd().toLocalDateTime());
//
//        if (!checkAppointmentOverlap(appointment.getStart(), appointment.getEnd())) {
//            System.out.println(checkAppointmentOverlap(appointment.getStart(), appointment.getEnd()));
//            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//            PreparedStatement ps = conn.prepareStatement("" +
//                    "INSERT INTO appointment (" +
//                    "customerId, userId, title, description, location, contact, type, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy" +
//                    ") " +
//                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, UTC_TIMESTAMP(), ?, UTC_TIMESTAMP(), ?)" // wow, there must be a better way to do these prepared statements...
//            );
//
//            ps.setInt(1, appointment.getCustomerId());
//            ps.setInt(2, appointment.getUserId());
//            ps.setString(3, appointment.getTitle());
//            ps.setString(4, appointment.getDescription());
//            ps.setString(5, appointment.getLocation());
//            ps.setString(6, appointment.getContact());
//            ps.setString(7, appointment.getType());
//            ps.setString(8, appointment.getUrl());
//            ps.setTimestamp(9, startTS);
//            ps.setTimestamp(10, endTS);
//            ps.setString(11, LoginScreenController.getCurrUser());
//            ps.setString(12, LoginScreenController.getCurrUser());
//            ps.executeUpdate();
//
//            return 1;
//        } else {
//            System.out.println("Overlapping appointment");
//            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
//            emptyFields.setTitle("Error");
//            emptyFields.setHeaderText("Overlapping Appointment");
//            emptyFields.setContentText("Please check the appointment list and try again.");
//            emptyFields.showAndWait();
//        }
//        return 0;
//    }
//
//    public static ObservableList<Appointment> getAppointments() throws SQLException {
//        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointment ORDER BY start");
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            Appointment appointment = new Appointment();
//            appointment.setAppointmentId(Integer.parseInt(rs.getString(1)));
//            appointment.setCustomerId(Integer.parseInt(rs.getString(2)));
//            appointment.setUserId(Integer.parseInt(rs.getString(3)));
//            appointment.setTitle(rs.getString(4));
//            appointment.setDescription(rs.getString(5));
//            appointment.setLocation(rs.getString(6));
//            appointment.setContact(rs.getString(7));
//            appointment.setType(rs.getString(8));
//            appointment.setUrl(rs.getString(9));
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
//            appointment.setStart(startZDT);
//            appointment.setEnd(endZDT);
//
//            allAppointments.add(appointment);
//        }
//        rs.close();
//
//        return allAppointments;
//    }
//
//    public static int updateAppointment(Appointment appointment) throws SQLException {
//        Timestamp startTS = Timestamp.valueOf(appointment.getStart().toLocalDateTime());
//        Timestamp endTS = Timestamp.valueOf(appointment.getEnd().toLocalDateTime());
//
//        if (!checkAppointmentOverlap(appointment.getStart(), appointment.getEnd())) {
//            System.out.println(checkAppointmentOverlap(appointment.getStart(), appointment.getEnd()));
//            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//            PreparedStatement ps = conn.prepareStatement("UPDATE appointment SET customerId = ?, UserId = ?, title = ?, description = ?, location = ?, contact = ?, type = ?, url = ?, start = ?, end = ?, lastUpdate = UTC_TIMESTAMP(), lastUpdateBy = ? WHERE appointmentId = ?");
//            ps.setInt(1, appointment.getCustomerId());
//            ps.setInt(2, appointment.getUserId());
//            ps.setString(3, appointment.getTitle());
//            ps.setString(4, appointment.getDescription());
//            ps.setString(5, appointment.getLocation());
//            ps.setString(6, appointment.getContact());
//            ps.setString(7, appointment.getType());
//            ps.setString(8, appointment.getUrl());
//            ps.setTimestamp(9, startTS);
//            ps.setTimestamp(10, endTS);
//            ps.setString(11, LoginScreenController.getCurrUser());
//            ps.setInt(12, appointment.getAppointmentId());
//            ps.executeUpdate();
//
//            System.out.println("**Appointment update complete**");
//
//            return 1;
//        } else {
//            System.out.println("Overlapping appointment");
//            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
//            emptyFields.setTitle("Error");
//            emptyFields.setHeaderText("Overlapping Appointment");
//            emptyFields.setContentText("Please check the appointment list and try again.");
//            emptyFields.showAndWait();
//        }
//        return 0;
//    }
//
//    public static void deleteAppointment(Appointment appointment) throws SQLException {
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("DELETE FROM appointment WHERE appointmentId = ?");
//        ps.setInt(1, appointment.getAppointmentId());
//        ps.executeUpdate();
//    }
//
//    public static ObservableList<ReportItem> getAppointmentsByType() throws SQLException {
//        ObservableList<ReportItem> allAppointments = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT MONTHNAME(start) AS 'month', type, COUNT(*) AS 'quantity' FROM appointment GROUP BY type, MONTH(start)");
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
//            allAppointments.add(item);
//        }
//        rs.close();
//
//        return allAppointments;
//    }
//
//    public static ObservableList<ReportItem> getConsultantSchedules() throws SQLException {
//        ObservableList<ReportItem> consultantSchedules = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT user.userName AS 'consultant', customer.customerName AS 'customer', appointment.type, appointment.start FROM customer JOIN appointment ON customer.customerId = appointment.customerId JOIN user ON appointment.userId = user.userId ORDER BY user.userId, appointment.start");
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            ReportItem item = new ReportItem();
//
//            item.setUserName(rs.getString(1));
//            item.setCustomerName(rs.getString(2));
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
//    public static ObservableList<ReportItem> getCustomersPerCity() throws SQLException {
//        ObservableList<ReportItem> customersPerCity = FXCollections.observableArrayList();
//
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//        PreparedStatement ps = conn.prepareStatement("SELECT ci.city, co.country, COUNT(DISTINCT cu.customerName) AS customerCount FROM country co JOIN city ci ON co.countryId = ci.countryId LEFT JOIN address a ON ci.cityId = a.cityId LEFT JOIN customer cu ON a.addressId = cu.addressId GROUP BY city");
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
//            customersPerCity.add(item);
//        }
//        rs.close();
//
//        return customersPerCity;
//    }
//
//    public static Appointment checkUpcomingAppointments() throws SQLException {
//        Appointment appointment = new Appointment();
//        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
//
//        LocalDateTime nowLDT = LocalDateTime.now();
//        ZonedDateTime nowZDT = nowLDT.atZone(ZoneId.systemDefault());
//        LocalDateTime startLDT = nowZDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
//        LocalDateTime startLDT15 = startLDT.plusMinutes(15);
//
//        PreparedStatement ps = conn.prepareStatement("SELECT * FROM appointment WHERE userId = ? AND (start BETWEEN ? AND ?) ORDER BY start LIMIT 1");
//        ps.setInt(1, LoginScreenController.getCurrUserId());
//        ps.setString(2, String.valueOf(startLDT));
//        ps.setString(3, String.valueOf(startLDT15));
//
//        ResultSet rs = ps.executeQuery();
//
//        while (rs.next()) {
//            appointment.setAppointmentId(Integer.parseInt(rs.getString(1)));
//            appointment.setCustomerId(Integer.parseInt(rs.getString(2)));
//            appointment.setUserId(Integer.parseInt(rs.getString(3)));
//            appointment.setTitle(rs.getString(4));
//            appointment.setDescription(rs.getString(5));
//            appointment.setLocation(rs.getString(6));
//            appointment.setContact(rs.getString(7));
//            appointment.setType(rs.getString(8));
//            appointment.setUrl(rs.getString(9));
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
//            appointment.setStart(startZDT);
//            appointment.setEnd(endZDT);
//        }
//
//        rs.close();
//
//        return appointment;
//    }
}