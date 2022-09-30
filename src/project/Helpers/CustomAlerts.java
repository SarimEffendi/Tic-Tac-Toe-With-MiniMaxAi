package project.Helpers;

import javafx.scene.control.Alert;

public class CustomAlerts {

    public static void error(String reason, String solution) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(reason);
        alert.setContentText(solution);
        alert.showAndWait();
    }

    public static void confirmation(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(description);
        alert.showAndWait();
    }

    public static void warning(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(description);
        alert.showAndWait();
    }

    public static void info(String title, String description) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(description);
        alert.showAndWait();
    }
}
