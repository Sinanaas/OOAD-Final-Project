package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.Connect;

public class User {
	private String UserID;
	private String Username;
	private String userEmail;

	private String UserPassword;
	private String userGender;
	
	
	public User(String userID, String username, String userEmail, String userPassword, String userGender) {
		super();
		this.UserID = userID;
		this.Username = username;
		this.userEmail = userEmail;
		this.UserPassword = userPassword;
		this.userGender = userGender;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	
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
	
	public static void register(String username, String userEmail, String userPassword, String userGender) {
		Connect connect = Connect.getConnection();
		String query = String.format("INSERT INTO users VALUES('%s', '%s', '%s', '%s', '%s')", generateID(), username, userEmail, userPassword, userGender);
		connect.executeUpdate(query);
	}
	
}
