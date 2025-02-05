package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

public class MainController {
    @FXML
    private AnchorPane contentArea;

    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/mylearningproject/view/" + fxmlFile));
            AnchorPane view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showStudentManagement() {
        loadPage("student-management.fxml");
    }

    @FXML
    private void showTeacherManagement() {
        loadPage("teacher-management.fxml");
    }

    @FXML
    private void showClassroomManagement() {
        loadPage("classroom-management.fxml");
    }

    @FXML
    public void showFacultyManagement() {
        loadPage("faculty-management.fxml");
    }

    @FXML
    public void showDepartmentManagement() {
        loadPage("department-management.fxml");
    }
}

