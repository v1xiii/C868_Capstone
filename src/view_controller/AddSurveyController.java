package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Survey;
import model.DBController;
import C868_Capstone.ThrownExceptions;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSurveyController implements Initializable {

    public Button button_cancel;
    public TextField input_id;
    public Button button_save;
    public Label label_login;

    @FXML private TextField input_title;
    @FXML private TextField input_description;
    @FXML private TextField input_location;

    public void initialize(URL url, ResourceBundle rb) {}

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws SQLException, ThrownExceptions.InvalidFieldsException {
        String title = input_title.getText();
        String description = input_description.getText();
        String location = input_location.getText();

        String error = Survey.hasValidFields(title, description);

        if (error.length() == 0) {
            Survey survey = new Survey();
            survey.setTitle(title);
            survey.setDescription(description);
            survey.setLocation(location);

            DBController.addSurvey(survey);
        } else {
            throw new ThrownExceptions.InvalidFieldsException(error);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}