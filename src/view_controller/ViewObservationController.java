package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DBController;
import model.Observation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewObservationController implements Initializable {

    @FXML public Button button_delete;
    @FXML public Button button_close;
    @FXML public Button button_view;
    public Button button_search;
    @FXML private TableView<Observation> table_observations;
    @FXML private TableColumn<Observation, Integer> col_survey_id;
    @FXML private TableColumn<Observation, String> col_common;
    @FXML private TableColumn<Observation, String> col_binomial;
    @FXML private TableColumn<Observation, String> col_location;
    @FXML private TableColumn<Observation, ZonedDateTime> col_date;
    //@FXML private ChoiceBox dropdown_filter;
    @FXML private TextField input_search;

    private ObservableList<Observation> allObservations;
    private static Observation observationToModify;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.allObservations = DBController.getObservations();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate observations table
        col_survey_id.setCellValueFactory(new PropertyValueFactory<>("surveyId"));
        col_common.setCellValueFactory(new PropertyValueFactory<>("common"));
        col_binomial.setCellValueFactory(new PropertyValueFactory<>("binomial"));
        col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table_observations.refresh();
        table_observations.setItems(allObservations);
    }

    private void setObservationToModify(Observation observation){
        observationToModify = observation;
    }

    static Observation getObservationToModify(){
        return observationToModify;
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void editButtonHandler(ActionEvent event) throws IOException {
        setObservationToModify(table_observations.getSelectionModel().getSelectedItem());

        Parent root = FXMLLoader.load(getClass().getResource("EditObservation.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Edit Observation");
        stage.setScene(new Scene(root, 700, 550));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void deleteButtonHandler() throws SQLException {
        Observation observation = table_observations.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing observation");
        alert.setContentText("Are you sure you want to remove this observation?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table_observations.getItems().remove(observation);
            DBController.deleteObservation(observation);
        }
    }

    @FXML
    public void searchObservations(ActionEvent event) throws SQLException {
        String searchTerm = input_search.getText().toLowerCase();
        ObservableList<Observation> observations = DBController.getObservations(searchTerm);

        if(observations.size() <= 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("\"" + searchTerm + "\" " + "Not Found");
            alert.setContentText("Search does not match any common or binomial names.");
            alert.showAndWait();
        } else {
            // populate observations table
            col_survey_id.setCellValueFactory(new PropertyValueFactory<>("surveyId"));
            col_common.setCellValueFactory(new PropertyValueFactory<>("common"));
            col_binomial.setCellValueFactory(new PropertyValueFactory<>("binomial"));
            col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            table_observations.refresh();
            table_observations.setItems(observations);
        }
    }

//    @FXML
//    private void filterChoiceHandler() {
//        ZonedDateTime minDate;
//        ZonedDateTime maxDate;
//        ZonedDateTime now = ZonedDateTime.now();
//        ObservableList<Observation> observations = null;
//
//        try {
//            observations = DBController.getObservations();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String filter = (String) dropdown_filter.getSelectionModel().getSelectedItem();
//
//        switch(filter) {
//            case "This Month":
//                minDate = now;
//                maxDate = now.plusMonths(1);
//                break;
//            case "This Week":
//                minDate = now;
//                maxDate = now.plusWeeks(1);
//                break;
//            default:
//                minDate = now.minusYears(100);
//                maxDate = now.plusYears(100);
//        }
//
//        ZonedDateTime finalMinDate = minDate;
//        ZonedDateTime finalMaxDate = maxDate;
//        if (observations != null) {
//            // this lambda is a very concise way to check the whole list of observations and remove any that match the condition
//            observations.removeIf(observation -> observation.getStart().isBefore(finalMinDate) || observation.getStart().isAfter(finalMaxDate));
//        }
//
//        col_survey_id.setCellValueFactory(new PropertyValueFactory<>("surveyId"));
//        col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
//        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
//        col_location.setCellValueFactory(new PropertyValueFactory<>("location"));
//        col_start.setCellValueFactory(new PropertyValueFactory<>("start"));
//        table_observations.refresh();
//        table_observations.setItems(observations);
//    }
}