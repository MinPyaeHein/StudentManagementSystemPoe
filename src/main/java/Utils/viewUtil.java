package Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class viewUtil {

    public static void loadPage(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(viewUtil.class.getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setHeight(700);
            stage.setWidth(1500);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML file: " + fxmlPath, e);
        }
    }
}
