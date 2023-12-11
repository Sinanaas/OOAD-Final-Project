package controller;

import helper.Helper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.User;


public class UserController {
	public static boolean register(String username, String userEmail, String userPassword, String userGender) {
		if (username.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Username cannot be empty!");
			return false;
		}
		
		User.register(username, userEmail, userPassword, userGender);
		Helper.showAlert(AlertType.INFORMATION, "Register Success!");
		return false;
	}
}
