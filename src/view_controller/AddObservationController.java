package view_controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Observation;
import model.Survey;
import model.DBController;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddObservationController implements Initializable {

    public Button button_cancel;
    public Button button_save;

    @FXML private TableView<Survey> table_surveys;
    @FXML private TableColumn<Survey, Integer> table_survey_id;
    @FXML private TableColumn<Survey, String> table_survey_name;

    @FXML private TextField input_common;
    @FXML private TextField input_binomial;
    @FXML private TextField input_location;
    @FXML private ChoiceBox dropdown_kingdom;
    @FXML private DatePicker datepicker_date;

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

        // this lambda goes through all the dates on the datepicker and disables dates prior to today and also weekends
        datepicker_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();
            setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) {
        Survey selectedSurvey = table_surveys.getSelectionModel().getSelectedItem();

        Integer surveyId = selectedSurvey.getSurveyId();
        Integer userId = LoginScreenController.getCurrUserId();
        String common = input_common.getText();
        String binomial = input_binomial.getText();
        String location = input_location.getText();
        String kingdom = dropdown_kingdom.getValue().toString();
        LocalDate date = datepicker_date.getValue();

        //String startSelection = "12:00 AM";

        //int startHourEnd = startSelection.indexOf(":");
        //String startHour = "12";
        //String startAmPm = "AM";

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");

        LocalDateTime startLDT = LocalDateTime.parse(date.toString() + " " + "12:00" + " " + "AM", dateFormat);

        ZoneId systemTZ = ZoneId.of(TimeZone.getDefault().getID());

        ZonedDateTime startZDT = ZonedDateTime.of(startLDT, systemTZ);

        ZonedDateTime startUTC = startZDT.withZoneSameInstant(ZoneId.of("UTC"));

        Observation observation = new Observation();
        observation.setSurveyId(surveyId);
        observation.setUserId(userId);
        observation.setCommon(common);
        observation.setBinomial(binomial);
        observation.setLocation(location);
        observation.setKingdom(kingdom);
        observation.setDate(startUTC);

        System.out.println(startUTC);

        int response = 0;
        try {
            response = DBController.addObservation(observation);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (response == 1){
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }
}