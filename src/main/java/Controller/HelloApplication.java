package Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/mylearningproject/main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 990, 550);
        stage.setScene(scene);
        stage.setTitle("Student Management");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
