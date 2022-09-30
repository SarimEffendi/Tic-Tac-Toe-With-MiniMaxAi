package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import project.Main;
import project.Models.GameModel;

public class MenuController implements Initializable {

    @FXML
    private Label statsTotalGames;

    @FXML
    private void loginComp() throws IOException {
        Main.gotoPage("loginPage");
    }

    @FXML
    private void loginHuman() throws IOException {
        Main.gotoPage("login2Page");
    }

    @FXML
    private void gotoSignup() throws IOException {
        Main.gotoPage("signupPage");
    }

    @FXML
    private void gotoForgotPassword() throws IOException {
        Main.gotoPage("forgotPassword");
    }

    @FXML
    private void gotoLeaderboards() throws IOException {
        Main.gotoPage("leaderboards");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statsTotalGames.setText("Total Games: " + GameModel.getGamesCount());
    }
}
