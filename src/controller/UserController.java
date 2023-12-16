package controller;

import helper.Helper;
import helper.UserSessionHelper;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.User;
import java.util.List;


public class UserController {
	// getUserData(String username, String password)
	public static User getUserData(String username, String userPassword) {
		// username validation
		if (username.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Error", "Username cannot be empty!", null);
			return null;
		}

		// password validation
		if (userPassword.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Error", "Password cannot be empty!", null);
			return null;
		}

		// check if user exists
		if (User.getUserData(username, userPassword) == null) {
			Helper.showAlert(AlertType.ERROR, "Error", "Username or Password is incorrect!", null);
			return null;
		}

		Helper.showAlert(AlertType.INFORMATION, "Login Success!", "Login Success!", null);
		return User.getUserData(username, userPassword);
	}

	// addNewUser(String username, String userPassword, Integer ageInput, String confirmPassword)
	public static boolean addNewUser(String username, String userPassword, Integer ageInput, String confirmPassword) {
		// username validation
		if (username.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Error", "Username cannot be empty!", null);
			return false;
		}

		if (username.length() <= 7) {
			Helper.showAlert(AlertType.ERROR, "Error", "Username must be more than 7 characters!", null);
			return false;
		}

		if (User.getAllUserData().stream().anyMatch(user -> user.getUsername().equals(username))) {
			Helper.showAlert(AlertType.ERROR, "Error", "Username already exists!", null);
			return false;
		}

		// password validation
		if (userPassword.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Error", "Password cannot be empty!", null);
			return false;
		}

		if (confirmPassword.isEmpty()) {
			Helper.showAlert(AlertType.ERROR, "Error", "Confirm Password cannot be empty!", null);
			return false;
		}

		if (!userPassword.equals(confirmPassword)) {
			Helper.showAlert(AlertType.ERROR, "Error", "Password and Confirm Password must be the same!", null);
			return false;
		}

		if (userPassword.length() <= 6) {
			Helper.showAlert(AlertType.ERROR, "Error", "Password must be more than 6 characters!", null);
			return false;
		}

		// password alphanumeric validation
		if (!userPassword.matches(".*[a-zA-Z]+.*")) {
			Helper.showAlert(AlertType.ERROR, "Error", "Password must contain at least 1 alphabet!", null);
			return false;
		}

		// age validation
		if (ageInput == null) {
			Helper.showAlert(AlertType.ERROR, "Error", "Age cannot be empty!", null);
			return false;
		}

		if (ageInput < 13 || ageInput > 65) {
			Helper.showAlert(AlertType.ERROR, "Error", "Age must be between 13 and 65!", null);
			return false;
		}

		User.addNewUser(username, userPassword, ageInput);
		Helper.showAlert(AlertType.INFORMATION, "Success", "User successfully added!", null);
		return true;
	}

	// getAllUserData()
	public static List<User> getAllUserData() {
		return User.getAllUserData();
	}

	// changeUserRole(String userID, int newRole)
	public static void changeUserRole(String userID, int newRole) {
		if (userID.equals(UserSessionHelper.getInstance().getLoggedInUserId())) {
			Alert alert = new Alert(AlertType.ERROR);
			Helper.showAlert(AlertType.ERROR, "Error", "You cannot change your own role!", null);
			return;
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		User.changeUserRole(userID, newRole);
		Helper.showAlert(AlertType.INFORMATION, "Success", "User role successfully changed!", null);
	}
}
