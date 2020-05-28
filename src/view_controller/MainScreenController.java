package view_controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import model.Observation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public Button button_add_observation;
    public Button button_edit_observation;
    public Button button_edit_survey;

    @FXML private Button button_logout;
    @FXML private Button button_add_survey;

    public void initialize(URL url, ResourceBundle rb) {

//        @FXML
//        private void logoutButtonHandler(ActionEvent event) throws IOException {
//            Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
//            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//            stage.setTitle("Scheduler Login");
//            stage.setScene(new Scene(root, 550, 500));
//            stage.centerOnScreen();
//            stage.show();
//        }


        button_logout.setOnAction(event -> { // this lambda is an alternative to specifying a function that runs on button click in FXML. Seems to not be very useful in a program that is using FXML, but this looks like it could be useful for applying to many buttons that do similar things
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../resources/LoginScreen.fxml"));
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                stage.setTitle("Scheduler Login");
                stage.setScene(new Scene(root, 550, 500));
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        button_add_survey.setOnAction(event -> { // this lambda simply sets an OnAction event TO a specific function rather than "being" the function
//            try {
//                addSurveyButtonHandler();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

//        Observation upcomingObservation = null;
//        try { // check for observation within the next 15 minutes
//            upcomingObservation = DBController.checkUpcomingObservations();
//            System.out.println(ZonedDateTime.now());
//            System.out.println(upcomingObservation.getTitle());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        assert upcomingObservation != null;
//        if (upcomingObservation.getSurveyId() != null) {
//            checkUpcomingObservations(upcomingObservation);
//        }
    }

//    private void checkUpcomingObservations(Observation upcomingObservation){
//        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a");
//        String start = upcomingObservation.getStart().format(formatTime);
//
//        Alert emptyFields = new Alert(Alert.AlertType.INFORMATION);
//        emptyFields.setTitle("Upcoming Observation");
//        emptyFields.setHeaderText("Observation at " + start);
//        emptyFields.setContentText("Type: " + upcomingObservation.getType() + "\nContact: " + upcomingObservation.getContact() + "\nLocation: " + upcomingObservation.getLocation());
//        emptyFields.showAndWait();
//    }

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
    private void reportObservationTypesButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportObservationTypes.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Observation Types");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }

    @FXML
    private void reportConsultantScheduleButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportConsultantSchedule.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Consultant Schedule");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }

    @FXML
    private void reportSurveysPerCityButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportSurveysPerCity.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Surveys Per City");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }
}