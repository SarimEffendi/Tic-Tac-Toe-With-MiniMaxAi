package project.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import project.Helpers.CustomAlerts;
import project.Helpers.Database;

public class UserModel {

	public static String getUserId(String username) {
		return UserModel.getUserField(username, "id");
	}

	public static ArrayList<String> getUsernames() {
		ArrayList<String> usernames = new ArrayList<String>();

		String query = "SELECT * FROM users";
		try {
			ResultSet resultSet = Database.getResult(query);
			while (resultSet.next()) {
				usernames.add(resultSet.getString("username"));
			}
		} catch (SQLException e) {
		}

		return usernames;
	}

	public static String getUserQuestion(String username) {
		String query = "SELECT * FROM users WHERE username = '" + username + "'";
		try {
			ResultSet resultSet = Database.getResult(query);
			if (resultSet.next()) {
				return resultSet.getString("security_question");
			}
		} catch (SQLException e) {
		}
		return null;
	}

	public static String getUserField(String username, String field) {
		String query = "SELECT * FROM users WHERE username = '" + username + "'";
		try {
			ResultSet resultSet = Database.getResult(query);
			if (resultSet.next()) {
				return resultSet.getString(field);
			}
		} catch (SQLException e) {
		}
		return null;
	}

	public static boolean registerUser(String username, String name, String password,
			String secretQuestion, String answer) {

		boolean isValid = false;
		try {
			String addAccountQuery = "INSERT INTO `users` (name,username,password,security_question,security_answer) VALUES ('"
					+
					name + "','" +
					username + "','" +
					password + "','" +
					secretQuestion + "','" +
					answer + "');";
			System.out.println(addAccountQuery);
			Database.executeIUD(addAccountQuery);
			System.out.println("Account Registration Success");
			isValid = true;
		} catch (Exception e) {
			CustomAlerts.error("Error Occured!", "Error: " + e.getMessage());
			e.printStackTrace();
		}

		return isValid;
	}

	public static boolean resetPassword(String username,
			String secretQuestion, String answer, String newPassword) {

		try {
			String validateQuery = "SELECT * FROM `users` WHERE `username` = '" + username + "';";
			ResultSet resultSet = Database.getResult(validateQuery);

			if (resultSet.next()) {
				String dbSecurityQuestion = resultSet.getString("security_question");
				String dbSecurityAnswer = resultSet.getString("security_answer");
				if (dbSecurityAnswer.equals(answer)) {
					if (dbSecurityQuestion.equals(secretQuestion)) {
						if (!dbSecurityAnswer.equals("")) {
							String resetPasswordQuery = "UPDATE users SET `password` = '"
									+ newPassword
									+ "' WHERE `username` = '"
									+ username + "';";
							Database.executeIUD(resetPasswordQuery);
							return true;
						} else {
							System.out.println("Incorrect Question/Answer");
							CustomAlerts.error("Incorrect question", "Please check your answers");
						}
					}
				}
			} else {
				System.out.println("Incorrect Username");
				CustomAlerts.error("Incorrect Username", "Please select the correct Username");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static String checkLogin(String username, String password) throws SQLException {
		try {
			if (username.isEmpty()) {
				System.out.println("Please Enter Username");
				return null;
			}
			if (password.isEmpty()) {
				System.out.println("Please Enter Password");
				return null;
			}

			String loginQuery = "SELECT * FROM `users` WHERE `username` = '" + username + "';";
			ResultSet resultSet = Database.getResult(loginQuery);

			if (resultSet.next()) {
				String dbUsername = resultSet.getString("username");
				String dbPassword = resultSet.getString("password");
				if ((!(dbUsername.equals(username)) || (!(dbPassword.equals(password))))) {
					System.out.println("The Username Or Password Were Incorrect.");
				} else {
					System.out.println("login successful ");
					return resultSet.getString("id");
				}
			} else {
				System.out.println("The Username Or Password Were Incorrect.");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
