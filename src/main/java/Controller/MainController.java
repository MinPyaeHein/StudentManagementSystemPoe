package Controller;
import Utils.UtilConstants;
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
        loadPage(UtilConstants.STUDENT_VIEW);
    }

    @FXML
    private void showTeacherManagement() {loadPage(UtilConstants.TEACHER_VIEW);
    }

    @FXML
    private void showClassroomManagement() {
        loadPage("classroom-management.fxml");
    }

    @FXML
    public void showFacultyManagement() {
        loadPage(UtilConstants.FACULTY_VIEW);
    }



    @FXML
    public void showDepartmentManagement() {
        loadPage(UtilConstants.DEPARTMENT_VIEW);
    }

    @FXML
    public void showDegreeManagement() {
        loadPage(UtilConstants.DEGREE_VIEW);
    }
    @FXML
    public void showCourseManagement() {
        loadPage(UtilConstants.COURSE_VIEW);
    }

    @FXML
    public void showEnrollmentManagement() {
        loadPage(UtilConstants.ENROLLMENT_VIEW);
    }



}

