package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project.Main;
import project.Helpers.CustomAlerts;
import project.Models.UserModel;

public class ForgotPasswordController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button submitBtn;
    @FXML
    private TextField secretQuestion;
    @FXML
    private TextField answer;

    public void setNewPassword(ActionEvent e) {
        String question = this.secretQuestion.getText();
        String answer = this.answer.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        try {
            if (UserModel.resetPassword(username, question, answer, password)) {
                CustomAlerts.info("Password changed", "Password changed successfully");
            } else {
                CustomAlerts.error("Error Occured!", "Cannot change Password, Invalid Answer!");
            }
        } catch (Exception ex) {
            CustomAlerts.error("Error Occured!", ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void getQuestion(ActionEvent e) {
        String question = UserModel.getUserQuestion(this.username.getText());
        if (question != null) {
            this.secretQuestion.setText(question);
            this.password.setDisable(false);
            this.submitBtn.setDisable(false);
            this.answer.setDisable(false);
        } else {
            CustomAlerts.error("Incorrect Username", "Please enter the correct Username");
        }
    }

    public void gotoMenu() throws IOException {
        Main.gotoPage("mainPage");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
