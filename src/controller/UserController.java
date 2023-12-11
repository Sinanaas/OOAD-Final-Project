package controller;

import helper.Helper;
import javafx.scene.control.Alert.AlertType;
import model.User;


public class UserController {
	public static boolean login(String username, String userPassword) {
		// username validation
		if (username.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Username cannot be empty!");
			return false;
		}

		if (!User.isUsernameExists(username)) {
			Helper.showAlert(AlertType.ERROR, "Username doesn't exists!");
			return false;
		}

		// password validation
		if (userPassword.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Password cannot be empty!");
			return false;
		}

		// matching validation
		if (!User.isPasswordMatch(username, userPassword)) {
			Helper.showAlert(AlertType.ERROR, "Password doesn't match!");
			return false;
		}

		Helper.showAlert(AlertType.INFORMATION, "Login Success!");
		return true;
	}

	public static boolean register(String username, String userPassword, Integer ageInput, String confirmPassword) {
		// username validation
		if (username.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Username cannot be empty!");
			return false;
		}

		if (username.length() < 7) {
			Helper.showAlert(AlertType.ERROR, "Username must be more than 7 characters!");
			return false;
		}

		if (User.isUsernameExists(username)) {
			Helper.showAlert(AlertType.ERROR, "Username already exists!");
			return false;
		}

		// password validation
		if (userPassword.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Password cannot be empty!");
			return false;
		}

		if (confirmPassword.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Confirm Password cannot be empty!");
			return false;
		}

		if (!userPassword.equals(confirmPassword)) {
			Helper.showAlert(AlertType.ERROR, "Password and Confirm Password must be same!");
			return false;
		}

		if (userPassword.length() < 6) {
			Helper.showAlert(AlertType.ERROR, "Password must be more than 6 characters!");
			return false;
		}

		// password alphanumeric validation
		if (!userPassword.matches(".*[a-zA-Z]+.*")) {
			Helper.showAlert(AlertType.ERROR, "Password must contain at least one letter!");
			return false;
		}

		// age validation
		if (ageInput == null) {
			Helper.showAlert(AlertType.ERROR, "Age cannot be empty!");
			return false;
		}

		if (ageInput < 13 || ageInput > 65) {
			Helper.showAlert(AlertType.ERROR, "Age must be between 13 and 65!");
			return false;
		}
		
		User.register(username, userPassword, ageInput, 0);
		Helper.showAlert(AlertType.INFORMATION, "Register Success!");
		return true;
	}
}
