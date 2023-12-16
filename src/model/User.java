package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	// getAllUserData()
	public static List<User> getAllUserData() {
		Connect connect = Connect.getConnection();
		String query = "SELECT * FROM users";
		ResultSet rs = connect.executeQuery(query);
		List<User> userList = new ArrayList<>();  // Initialize the list here
		try {
			while (rs.next()) {
				String userID = rs.getString("UserID");
				String username = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				Integer userAge = rs.getInt("UserAge");
				Integer userRole = rs.getInt("UserRole");
				userList.add(new User(userID, username, userPassword, userAge, userRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userList;
	}

	// getUserData(Username, Password)
	public static User getUserData(String username, String password) {
		Connect connect = Connect.getConnection();
		String query = String.format("SELECT * FROM users WHERE Username = '%s' AND UserPassword = '%s'", username, password);
		ResultSet rs = connect.executeQuery(query);
		User user = null;
		try {
			if (rs.next()) {
				String userID = rs.getString("UserID");
				String userUsername = rs.getString("Username");
				String userPassword = rs.getString("UserPassword");
				Integer userAge = rs.getInt("UserAge");
				Integer userRole = rs.getInt("UserRole");
				user = new User(userID, userUsername, userPassword, userAge, userRole);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	// addNewUser(Username, UserPassword, UserAge)
	public static void addNewUser(String username, String userPassword, int userAge) {
		Connect connect = Connect.getConnection();
		String query = String.format("INSERT INTO users VALUES('%s', '%s', '%s', '%d', '%d')", generateID(), username, userPassword, userAge, 0);
		connect.executeUpdate(query);
	}

	// changeUserRole(UserID, newRole)
	public static void changeUserRole(String userID, int newRole) {
		Connect connect = Connect.getConnection();
		String query = String.format("UPDATE users SET UserRole = '%d' WHERE UserID = '%s'", newRole, userID);
		connect.executeUpdate(query);
	}

	// getter, setter, and constructor
	@Override
	public String toString() {
		return "User{" +
			"UserID='" + UserID + '\'' +
			", Username='" + Username + '\'' +
			", UserPassword='" + UserPassword + '\'' +
			", UserAge=" + UserAge +
			", UserRole=" + UserRole +
			'}';
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

	public User() {
	}

	public User(String userID, String username, String userPassword, Integer userAge, Integer userRole) {
		UserID = userID;
		Username = username;
		UserPassword = userPassword;
		UserAge = userAge;
		UserRole = userRole;
	}

	public String getRoleName() {
		switch (UserRole) {
			case 1:
				return "Computer Technician";
			case 2:
				return "Operator";
			case 3:
				return "Admin";
			default:
				return "Unknown Role";
		}
	}
}
