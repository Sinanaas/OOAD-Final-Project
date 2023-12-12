package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.Connect;

public class User {
	private String UserID;
	private String Username;
	private String UserPassword;
	private Integer UserAge;
	private Integer UserRole;

	public static String generateID() {
		Connect connect = Connect.getConnection();
		String query = "SELECT * FROM Users ORDER BY UserID DESC LIMIT 1";
		ResultSet rs = connect.executeQuery(query);

		String nextID = "US001";

		try {
			if (rs.next()) {
				String lastID = rs.getString("UserID");
				String lastDigitID = lastID.substring(2);
				Integer lastDigitIDInt = Integer.parseInt(lastDigitID);
				nextID = "US" + String.format("%03d", (lastDigitIDInt + 1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextID;
	}

	public static void register(String username, String userPassword, int userAge, int userRole) {
		Connect connect = Connect.getConnection();
		String query = String.format("INSERT INTO users VALUES('%s', '%s', '%s', '%d', '%d')", generateID(), username, userPassword, userAge, userRole);
		connect.executeUpdate(query);
	}

	public static boolean isUsernameExists(String username) {
		Connect connect = Connect.getConnection();
		String query = String.format("SELECT * FROM users WHERE Username = '%s'", username);
		ResultSet rs = connect.executeQuery(query);

		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isPasswordMatch(String username, String userPassword) {
		Connect connect = Connect.getConnection();
		String query = String.format("SELECT * FROM users WHERE Username = '%s' AND UserPassword = '%s'", username, userPassword);
		ResultSet rs = connect.executeQuery(query);

		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static User findUser(String username, String password) {
		Connect connect = Connect.getConnection();
		String query = String.format("SELECT * FROM users WHERE Username = '%s' AND UserPassword = '%s'", username, password);
		ResultSet rs = connect.executeQuery(query);

		try {
			if (rs.next()) {
				String userID = rs.getString("UserID");
				String userUsername = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				Integer userAge = rs.getInt("UserAge");
				Integer userRole = rs.getInt("UserRole");
				return new User(userID, userUsername, userPassword, userAge, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static User getUserById(String UserID) {
		Connect connect = Connect.getConnection();
		String query = String.format("SELECT * FROM users WHERE UserID = '%s'", UserID);
		ResultSet rs = connect.executeQuery(query);

		try {
			if (rs.next()) {
				String userID = rs.getString("UserID");
				String userUsername = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				Integer userAge = rs.getInt("UserAge");
				Integer userRole = rs.getInt("UserRole");
				return new User(userID, userUsername, userPassword, userAge, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public Integer getUserAge() {
		return UserAge;
	}

	public void setUserAge(Integer userAge) {
		UserAge = userAge;
	}

	public Integer getUserRole() {
		return UserRole;
	}

	public void setUserRole(Integer userRole) {
		UserRole = userRole;
	}

	public User(String userID, String username, String userPassword, Integer userAge, Integer userRole) {
		UserID = userID;
		Username = username;
		UserPassword = userPassword;
		UserAge = userAge;
		UserRole = userRole;
	}
}
