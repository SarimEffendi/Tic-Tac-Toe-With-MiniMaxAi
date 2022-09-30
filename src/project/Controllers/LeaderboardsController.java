package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import project.Main;
import project.Models.GameModel;
import project.Models.UserModel;

public class LeaderboardsController implements Initializable {

	@FXML
	public ListView<String> usersList;
	@FXML
	public ListView<String> userGamesList;

	private ObservableList<String> users;

	private ObservableList<String> usersGames;

	private String selectedUser = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		users = FXCollections.observableArrayList(UserModel.getUsernames());

		usersList.setItems(users);

		usersList.setOnMousePressed((arg) -> {
			String selection = usersList.getSelectionModel().getSelectedItem();
			System.out.println(selection);
			if (selectedUser != selection) {
				userGamesList.setDisable(true);
				this.selectedUser = selection;
				fillGames();
			}
		});
	}

	public void fillGames() {
		ArrayList<Map<String, String>> games = GameModel.getUserGames(selectedUser);
		System.out.println(games);
		this.usersGames = FXCollections.observableArrayList();

		for (Map<String, String> map : games) {
			usersGames.add(map.get("user1") + " VS " + map.get("user2") + "    Winner: " + map.get("winner"));
		}
		userGamesList.setItems(usersGames);
		userGamesList.setDisable(false);
	}

	@FXML
	private void gotoMenu() throws IOException {
		Main.gotoPage("mainPage");
	}

}
