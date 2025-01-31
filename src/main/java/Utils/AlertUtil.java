package Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;


public class AlertUtil {
    public static void alert(String message,String errorType) {
        Alert alert=null;
        if(errorType.toUpperCase().equals("INFORMATION")){
           alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Information !!");
        }else if(errorType.toUpperCase().equals("ERROR")){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error occurred !!");
        }
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    public static Boolean confirmationDialog(String title,String headerText ){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText("Click OK to confirm, or Cancel to abort.");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }


}
