package model;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBControllerTest {

    @Test
    void checkLogin_valid() {
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
    void checkBlankLogin_invalid() {
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
    void checkBlankUser_invalid() {
        String username = "";
        String password = "test";

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
    void checkBlankPassword_invalid() {
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
    void checkCapitalized_invalid() {
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
    void checkWrongCreds_invalid() {
        String username = "John";
        String password = "Password";

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