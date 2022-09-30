package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project.Main;
import project.Helpers.CustomAlerts;
import project.Models.GameMode;
import project.Models.UserModel;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username2;
    @FXML
    private PasswordField password2;

    public void doLogin(ActionEvent e) throws IOException {
        try {
            String password = this.password.getText();
            String username = this.username.getText();
            String userId = UserModel.checkLogin(username, password);
            if (userId != null) {
                Main.gotoPage("game", Map.ofEntries(
                        Map.entry("userid", userId),
                        Map.entry("mode", GameMode.ONE_PLAYER.toString())));
            }

        } catch (SQLException throwables) {
            CustomAlerts.error("Error", "Please fill all fields Correctly");
            throwables.printStackTrace();
        }
    }

    public void doLoginTP(ActionEvent e) throws IOException {
        try {
            String username = this.username.getText();
            String password = this.password.getText();
            String userId = UserModel.checkLogin(username, password);

            String username2 = this.username2.getText();
            String password2 = this.password2.getText();
            String userId2 = UserModel.checkLogin(username2, password2);
            if (userId == null) {
                CustomAlerts.error("Error", "Player 1 Credentials are Incorrect!");
                return;
            }
            if (userId2 == null) {
                CustomAlerts.error("Error", "Player 2 Credentials are Incorrect!");
                return;
            }
            if (userId2.equals(userId)) {
                CustomAlerts.error("Error", "You cannot play against yourself!");
                return;
            }
            Main.gotoPage("game", Map.ofEntries(
                    Map.entry("userid", userId),
                    Map.entry("userid2", userId2),
                    Map.entry("mode", GameMode.TWO_PLAYER.toString())));
        } catch (SQLException throwables) {
            CustomAlerts.error("Error", "Please fill all fields Correctly");
            throwables.printStackTrace();
        }
    }

    public void gotoMenu() throws IOException {
        Main.gotoPage("mainPage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
