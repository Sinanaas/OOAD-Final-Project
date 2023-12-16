package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Helper {
	public static void showAlert(AlertType alertType, String title, String headerText, String contentText) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
