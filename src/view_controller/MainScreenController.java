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
//import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    public Button button_add_appointment;
    public Button button_edit_appointment;
    public Button button_edit_customer;

    @FXML private Button button_logout;
    @FXML private Button button_add_customer;

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

        button_add_customer.setOnAction(event -> { // this lambda simply sets an OnAction event TO a specific function rather than "being" the function
            try {
                addCustomerButtonHandler();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

//        Appointment upcomingAppointment = null;
//        try { // check for appointment within the next 15 minutes
//            upcomingAppointment = DBController.checkUpcomingAppointments();
//            System.out.println(ZonedDateTime.now());
//            System.out.println(upcomingAppointment.getTitle());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        assert upcomingAppointment != null;
//        if (upcomingAppointment.getCustomerId() != null) {
//            checkUpcomingAppointments(upcomingAppointment);
//        }
    }

//    private void checkUpcomingAppointments(Appointment upcomingAppointment){
//        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a");
//        String start = upcomingAppointment.getStart().format(formatTime);
//
//        Alert emptyFields = new Alert(Alert.AlertType.INFORMATION);
//        emptyFields.setTitle("Upcoming Appointment");
//        emptyFields.setHeaderText("Appointment at " + start);
//        emptyFields.setContentText("Type: " + upcomingAppointment.getType() + "\nContact: " + upcomingAppointment.getContact() + "\nLocation: " + upcomingAppointment.getLocation());
//        emptyFields.showAndWait();
//    }

    @FXML
    private void addCustomerButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 500));
        stage.show();
    }

    @FXML
    private void editCustomerButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Edit Customer");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void addAppointmentButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddAppointment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void viewAppointmentButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewAppointment.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Appointments");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 700, 550));
        stage.show();
    }

    @FXML
    private void reportAppointmentTypesButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportAppointmentTypes.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Appointment Types");
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
    private void reportCustomersPerCityButtonHandler() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ReportCustomersPerCity.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Customers Per City");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 550, 700));
        stage.show();
    }
}