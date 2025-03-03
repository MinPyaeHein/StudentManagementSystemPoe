package Controller;
import Model.Course;
import Model.Enrollment;
import Model.Student;
import Service.impl.EnrollmentServiceImpl;
import Service.impl.StudentServiceImpl;
import Utils.*;
import java.time.LocalDateTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class EnrollmentLoginConroller {
    @FXML private Label dateLabel;
    @FXML private TextField nameField;
    @FXML private TextField genderField;
    @FXML private TextField facultyField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private ImageView imageFrame;
    @FXML private TextField searchField;
    @FXML private TextField idField;
    @FXML private TableView<Enrollment> subjectTable;
    @FXML private TableColumn<Enrollment, String> subjectColumn;
    @FXML private TableColumn<Enrollment, String> gradeColumn;
    private static int studentId;

    private EnrollmentServiceImpl enrollmentService;
    private StudentServiceImpl studentService;
    ObservableList<Enrollment> enrollmentList =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        enrollmentService = new EnrollmentServiceImpl();
        studentService = new StudentServiceImpl();
        dateLabel.setText(DateTimeUtil.DateFormatter(LocalDateTime.now()));
    }

    @FXML
    private void toEnrollmentPage(ActionEvent event) {
        if (studentService.getStudentById(studentId) != null) {
            viewUtil.loadPage(event, "/org/example/mylearningproject/view/enrollments.fxml");
        }
    }

    @FXML
    private void searchBtn() {
        int studentIdInput = Integer.parseInt(searchField.getText());
        Student student = studentService.getStudentById(studentIdInput);
        if (student == null) {
            clearField();
        } else {
            studentId = student.getId();
            setTextField(student);
            tableView(studentId);
            searchField.clear();
        }
    }

    private void setTextField(Student student) {
        idField.setText(String.valueOf(student.getId()));
        nameField.setText(student.getName());
        genderField.setText(String.valueOf(student.getGender()));
        facultyField.setText(String.valueOf(student.getFaculty().getName()));
        addressField.setText(student.getAddress());
        phoneField.setText(student.getPhone());
        emailField.setText(student.getEmail());
        ImgUtil.displayProfileImage(student.getId(), UtilConstants.STUDENT_IMAGE_FOLDER_PATH, imageFrame);
    }

    public void tableView(int studentId) {
        List<Enrollment> enrollments =
                enrollmentService.getAllEnrolledCourses(studentId);
        enrollmentList.addAll(enrollments);
        subjectColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue().getCourse();
            return new javafx.beans.property.SimpleStringProperty(course != null ? course.getCourse_code() : "");
        });
        gradeColumn.setCellValueFactory(cellData -> {
            Enrollment enollment = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(enollment != null ? enollment.getGrade() : "");
        });
        subjectTable.setItems(enrollmentList);
    }

    public void clearField() {
        idField.clear();
        nameField.clear();
        genderField.clear();
        facultyField.clear();
        addressField.clear();
        phoneField.clear();
        emailField.clear();
        imageFrame.setImage(null);
        enrollmentList.clear();
        subjectTable.setItems(enrollmentList);
    }
}
