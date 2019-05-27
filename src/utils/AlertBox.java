package utils;

import javafx.scene.control.Alert;

/**
 *
 * @author Jan
 */
public class AlertBox {
    public void showAlertBox(Alert.AlertType alertType, String title, String content){
        Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
    }
}
