package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public Button button_add_observation;
    public Button button_edit_observation;
    public Button button_edit_survey;
    public Button button_logout;
    public Button button_add_survey;

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void logoutButtonHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../C868_Capstone/res/LoginScreen.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("SODA Login");
        stage.setScene(new Scene(root, 550, 500));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void addSurveyButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddSurvey.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Survey");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 500));
        stage.show();
    }

    @FXML
    private void editSurveyButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditSurvey.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Survey");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void addObservationButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddObservation.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Observation");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void viewObservationButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewObservation.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Observations");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void reportObservationKingdomsButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportObservationKingdoms.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Observation Types");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }
}