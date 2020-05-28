package C868_Capstone;

import javafx.scene.control.Alert;

public class ThrownExceptions {
    public static class InvalidFieldsException extends Exception {
        public InvalidFieldsException(String error) {
            Alert emptyFields = new Alert(Alert.AlertType.ERROR);
            emptyFields.setTitle("Error");
            emptyFields.setHeaderText("Required information missing");
            emptyFields.setContentText(error);
            emptyFields.showAndWait();
        }
    }
}