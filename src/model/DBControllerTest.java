package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBControllerTest {

    @Test
    void checkLogin_valid() { // valid
        String username = "test";
        String password = "test";

        Integer expected = 1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void checkBlankLogin_invalid() { // empty inputs
        String username = "";
        String password = "";

        Integer expected = -1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void checkBlankUser_invalid() { // no username, valid password
        String username = "";
        String password = "test";

        Integer expected = 5;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void checkBlankPassword_invalid() { // valid username, no password
        String username = "test";
        String password = "";

        Integer expected = -1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void checkCapitalized_invalid() { // capitalized username and password
        String username = "Test";
        String password = "Test";

        Integer expected = -1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void checkLeadingSpaces_invalid() { // capitalized username and password
        String username = " test";
        String password = " test";

        Integer expected = -1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void checkTrailingSpaces_invalid() { // capitalized username and password
        String username = "test ";
        String password = "test ";

        Integer expected = -1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }

    @Test
    void mixedUserCreds_invalid() { // username from one account, password from another
        String username = "lschol1"; // username of userId 2
        String password = "test"; // password of userId 1

        Integer expected = -1;
        Integer actual = null;

        try {
            actual = DBController.checkLogin(username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(expected, actual);
    }
}