package project.Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import project.Helpers.CustomAlerts;
import project.Helpers.Database;

public class GameModel {

	public static ArrayList<Map<String, String>> getUserGames(String username) {
		String userId = UserModel.getUserId(username);

		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		try {
			ResultSet result = Database
					.getResult(
							"SELECT games.*, p1U.username as p1name, p2U.username as p2name, winU.username as winname "
									+
									"FROM games " +
									"LEFT JOIN users p1U ON player1 = p1U.id " +
									"LEFT JOIN users p2U ON player2 = p2U.id " +
									"LEFT JOIN users winU ON winner = winU.id " +
									"WHERE player1='" + userId + "' OR player2='" + userId + "'");
			while (result.next()) {
				String user1 = result.getString("player1");
				String user1Name = result.getString("p1name");
				String user2 = result.getString("player2");
				String user2Name = result.getString("p2name");

				String winner = result.getString("winner");
				String winnerName = result.getString("winname");
				if (user2.equals("COMP")) {
					user2Name = "COMP";
				}
				if (winner.equals("COMP") || winner.equals("TIE")) {
					winnerName = winner;
				}
				data.add(Map.ofEntries(
						Map.entry("user1", user1Name),
						Map.entry("user2", user2Name),
						Map.entry("winner", winnerName)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static int getGamesCount() {
		try {
			return Database.getResult("SELECT COUNT(*) as gamesCount FROM games").getInt("gamesCount");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void addSPRecord(String userid, String winner) {
		try {
			Database.executeIUD("INSERT INTO games (player1, player2, winner) VALUES ('" + userid + "', '"
					+ "COMP" + "', '" + winner + "')");
		} catch (SQLException e) {
			CustomAlerts.error("Error Occured!", "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void addTPRecord(String userid, String opponentid, String winner) {
		try {
			Database.executeIUD("INSERT INTO games (player1, player2, winner) VALUES ('" + userid + "', '"
					+ opponentid + "', '" + winner + "')");
		} catch (SQLException e) {
			CustomAlerts.error("Error Occured!", "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
