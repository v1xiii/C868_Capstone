package view_controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DBController;
import model.ReportItem;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReportObservationKingdomsController implements Initializable {
    @FXML private TableView<ReportItem> table_observations;
    @FXML private TableColumn<ReportItem, String> col_month;
    @FXML private TableColumn<ReportItem, String> col_kingdom;
    @FXML private TableColumn<ReportItem, String> col_quantity;
    public Button button_close;

    private ObservableList<ReportItem> observationsByKingdom;

    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.observationsByKingdom = DBController.getObservationsByKingdom();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // populate observations table
        col_month.setCellValueFactory(new PropertyValueFactory<>("month"));
        col_kingdom.setCellValueFactory(new PropertyValueFactory<>("kingdom"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table_observations.refresh();
        table_observations.setItems(observationsByKingdom);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
