package view_controller;

import C868_Capstone.ThrownExceptions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Survey;
import model.DBController;
//import model.ThrownExceptions;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditSurveyController implements Initializable {
    public Button button_cancel;
    public Button button_delete;
    public Button button_save;

    @FXML private TableView<Survey> table_surveys;
    @FXML private TableColumn<Survey, Integer> table_survey_id;
    @FXML private TableColumn<Survey, String> table_survey_name;
    @FXML private TextField input_name;
    @FXML private TextField input_description;
    @FXML private TextField input_location;

    public void initialize(URL url, ResourceBundle rb) {
        // populate surveys table
        table_survey_id.setCellValueFactory(new PropertyValueFactory<>("surveyId"));
        table_survey_name.setCellValueFactory(new PropertyValueFactory<>("title"));
        table_surveys.refresh();
        try {
            table_surveys.setItems(DBController.getSurveys());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelButtonHandler (ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void tableClickHandler(){ // fills the text fields for the selected survey
        Survey selectedSurvey = table_surveys.getSelectionModel().getSelectedItem();
        input_name.setText(selectedSurvey.getTitle());
        input_description.setText(selectedSurvey.getDescription());
        input_location.setText(selectedSurvey.getLocation());
    }

    @FXML
    private void deleteButtonHandler() throws SQLException {
        Survey survey = table_surveys.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Removing survey " + survey.getSurveyId() + " - " + survey.getTitle());
        alert.setContentText("Are you sure you want to remove this survey?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            table_surveys.getItems().remove(survey);
            DBController.deleteSurvey(survey);
        }
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws SQLException, ThrownExceptions.InvalidFieldsException {
        Survey survey = table_surveys.getSelectionModel().getSelectedItem();

        String title = input_name.getText();
        String description = input_description.getText();
        String location = input_location.getText();

        String error = Survey.hasValidFields(title, description);

        if (error.length() == 0) {
            survey.setTitle(title);
            survey.setDescription(description);
            survey.setLocation(location);

            DBController.updateSurvey(survey);
        } else {
            throw new ThrownExceptions.InvalidFieldsException(error);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}