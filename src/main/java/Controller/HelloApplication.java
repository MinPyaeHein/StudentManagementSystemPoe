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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/mylearningproject/login.fxml"));
        Parent root = fxmlLoader.load();
        Scene loginScene = new Scene(root, 400, 400);
        stage.setScene(loginScene);
        stage.centerOnScreen();
        stage.setTitle("Student Management - Login");
        LoginController loginController = fxmlLoader.getController();
        loginController.setStage(stage);

        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}
