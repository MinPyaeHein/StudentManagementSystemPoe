package Controller;
import Constant.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.postgresql.core.Utils;

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
        loadPage(Constants.Views.STUDENT);
    }

    @FXML
    private void showTeacherManagement() {loadPage(Constants.Views.TEACHER);
    }

    @FXML
    private void showClassroomManagement() {
        loadPage("classroom-management.fxml");
    }

    @FXML
    public void showFacultyManagement() {
        loadPage(Constants.Views.FACULTY);
    }



    @FXML
    public void showDepartmentManagement() {
        loadPage(Constants.Views.DEPARTMENT);
    }

    @FXML
    public void showDegreeManagement() {
        loadPage(Constants.Views.DEGREE);
    }
    @FXML
    public void showCourseManagement() {
        loadPage(Constants.Views.COURSE);
    }

    @FXML
    public void showEnrollmentLoginManagement() {
        loadPage(UtilConstants.ENROLLMENT_LOGIN_VIEW);
    }



}

