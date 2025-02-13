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
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Remove the fullscreen mode to ensure the close button appears
        stage.setFullScreen(false);  // Disable fullscreen
        stage.setMaximized(true);     // Optionally, maximize the window, keeping the title bar

        stage.setOnCloseRequest(event -> {
            // Optionally, add custom behavior here if needed
            System.out.println("Window close requested."); // This will show in the console when the close button is clicked.
            // The window will still close normally
        });

        stage.setTitle("Student Management");
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
