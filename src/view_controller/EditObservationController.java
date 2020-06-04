package view_controller;

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
import model.Observation;
import model.Survey;
import model.DBController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class EditObservationController implements Initializable {
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

    private Observation observation;

    public void initialize(URL url, ResourceBundle rb) {
        observation = ViewObservationController.getObservationToModify();

        input_common.setText(observation.getCommon());
        input_binomial.setText(observation.getBinomial());
        input_location.setText(observation.getLocation());
        dropdown_kingdom.setValue(observation.getKingdom());

        // this lambda goes through all the dates on the datepicker and disables dates prior to today and also weekends
        datepicker_date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            setDisable(empty || date.compareTo(today) < 0 || dayOfWeek == SATURDAY || dayOfWeek == SUNDAY);
            }
        });

        // set the datepicker value
        LocalDate date = observation.getDate().toLocalDate();
        datepicker_date.setValue(date);

        // set the times
        DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("h:mm a");

        // populate surveys table
        table_survey_id.setCellValueFactory(new PropertyValueFactory<>("surveyId"));
        table_survey_name.setCellValueFactory(new PropertyValueFactory<>("surveyName"));
        table_surveys.refresh();
        try {
            table_surveys.setItems(DBController.getSurveys());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // select the survey for this observation
        for (Survey survey : table_surveys.getItems()) {
            if (table_survey_id.getCellData(survey).equals(observation.getSurveyId())){
                table_surveys.getSelectionModel().select(survey);
                break;
            }
        }
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewObservation.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Observations");
        stage.setScene(new Scene(root, 700, 550));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    private void saveButtonHandler (ActionEvent event) throws IOException {
        Survey selectedSurvey = table_surveys.getSelectionModel().getSelectedItem();

        Integer surveyId = selectedSurvey.getSurveyId();
        Integer userId = LoginScreenController.getCurrUserId();
        String common = input_common.getText();
        String binomial = input_binomial.getText();
        String location = input_location.getText();
        String kingdom = dropdown_kingdom.getValue().toString();
        LocalDate date = datepicker_date.getValue();

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
            Parent root = FXMLLoader.load(getClass().getResource("ViewObservation.fxml"));
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Observations");
            stage.setScene(new Scene(root, 700, 550));
            stage.centerOnScreen();
            stage.show();
        }
    }
}