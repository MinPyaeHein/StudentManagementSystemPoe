package Utils;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Success!");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
