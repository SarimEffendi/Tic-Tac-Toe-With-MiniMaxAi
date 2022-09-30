package project.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import project.Main;
import project.Helpers.Captcha;
import project.Helpers.CustomAlerts;
import project.Models.UserModel;

public class SignupController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ComboBox<String> secretQuestion;

    @FXML
    private TextField answer;

    @FXML
    private TextField captcha1;
    @FXML
    private TextField captcha2;

    private ObservableList<String> secretQuestionsList;

    @FXML
    public void doSignup(ActionEvent e) {
        String name = this.name.getText();
        String username = this.username.getText();
        String password = this.password.getText();
        String securityQuestion = this.secretQuestion.getSelectionModel().getSelectedItem();
        String answer = this.answer.getText();

        if (name.equals("") || username.equals("") || password.equals("") || securityQuestion.equals("")
                || answer.equals("")) {
            System.out.println("Please enter all Values");
            CustomAlerts.error("Invalid Entry!", "Please enter all valid values! ");
            return;
        }

        try {
            if (Captcha.balancedBrackets(captcha1.getText() + captcha2.getText())) {
                if (UserModel.registerUser(username, name, password, securityQuestion, answer)) {
                    System.out.println("Signup Successful");
                    CustomAlerts.info("Signup Success", "You can login now!");
                    Main.gotoPage("mainPage");
                } else {
                    System.out.println("Cannot Signup");
                    CustomAlerts.error("Cannot Signup", "An error occured while signup! ");
                }
            } else {
                CustomAlerts.error("Invalid Captcha", "Please complete the brackets correctly!");
                System.out.println("Invalid Captcha");
            }
        } catch (IOException e1) {
        }
    }

    public void gotoMenu() throws IOException {
        Main.gotoPage("mainPage");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        captcha1.setText(Captcha.generateRandomBrackets());
        secretQuestionsList = FXCollections.observableArrayList("City of birth?", "Favourite Dog", "Favorite food",
                "Favorite color");
        secretQuestion.setItems(secretQuestionsList);
    }

}
