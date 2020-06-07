package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

import static model.DBController.checkLogin;

public class LoginScreenController implements Initializable {

    private static String currUser;
    private static Integer currUserId;

    public Button button_login;

    @FXML
    private TextField input_username, input_password;

    public void initialize(URL url, ResourceBundle rb) {}

    public static void setCurrUser(String userName) {
        currUser = userName;
    }

    private static void setCurrUserId(Integer userId) {
        currUserId = userId;
    }

    public static Integer getCurrUserId(){
        return currUserId;
    }

    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException, SQLException, IllegalArgumentException {
        String username = input_username.getText();
        String password = input_password.getText();
        Integer emptyThrown = 0;
        currUserId = -1; // comment this out for bypassing login during development

        // use these lines for bypassing login during development
//        currUserId = 1;
//        setCurrUserId(checkLogin("test", "test"));

        try {
            if (username.equals("") || password.equals("")) {
                throw new IllegalArgumentException();
            } else {
                setCurrUserId(checkLogin(username, password));
            }
        } catch (IllegalArgumentException iae){
            Alert emptyFields = new Alert(Alert.AlertType.WARNING);

            emptyFields.setTitle("Warning");
            emptyFields.setHeaderText("Username and password are required");
            emptyFields.setContentText("Please complete both fields.");
            emptyFields.showAndWait();
            emptyThrown = 1;
        }

        if (currUserId > -1){
            Path path = Paths.get("logins.txt");
            Files.write(path, Collections.singletonList("User:" + currUser + " -- Login Time: " + Date.from(Instant.now()).toString() + "."), StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);

            Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("SODA Main Screen");
            stage.setScene(new Scene(root, 550, 700));
            stage.centerOnScreen();
            stage.show();

        } else if (emptyThrown.equals(0)) {
            Alert emptyFields = new Alert(Alert.AlertType.WARNING);
            emptyFields.setTitle("Warning");
            emptyFields.setHeaderText("Invalid credentials.");
            emptyFields.setContentText("Please check your credentials and try again.");
            emptyFields.showAndWait();
        }
    }
}