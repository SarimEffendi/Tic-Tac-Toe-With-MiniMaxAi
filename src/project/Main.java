package project;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import project.Helpers.Database;

/**
 * Main class for the game.
 * It manages all pages and routes
 * It also manages the route Parameters for all the pages
 */
public class Main extends Application {

    private static Scene scene;

    private static Map<String, String> routeParameters = new HashMap<String, String>();

    @Override
    public void start(Stage stage) throws Exception {
        Database.connect();
        scene = new Scene(loadFXML("mainPage"), 640, 480);
        stage.minWidthProperty().bind(scene.heightProperty());
        stage.minHeightProperty().bind(scene.widthProperty());
        stage.setTitle("Tic-Tac-Toe");
        stage.setScene(scene);
        stage.show();
    }

    public static void gotoPage(String fxml) throws IOException {
        gotoPage(fxml, null);
    }

    public static void gotoPage(String fxml, Map<String, String> params) throws IOException {
        routeParameters.clear();
        if (params != null) {
            routeParameters.putAll(params);
        }
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("View/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * @return the routeParameters
     */
    public static Map<String, String> getRouteParameters() {
        return routeParameters;
    }

    public static void main(String[] args) {
        launch();
    }
}
