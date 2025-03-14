package Controller;

import Constant.Constants;
import Dto.EnrollmentDto;
import Model.Course;
import Model.Enrollment;
import Service.impl.CourseServiceImpl;
import Service.impl.EnrollmentServiceImpl;
import Service.impl.StudentServiceImpl;
import Utils.AlertUtil;
import Utils.DateTimeUtil;
import Utils.UtilConstants;
import Utils.viewUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentController {
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> courseCodeColumn;
    @FXML
    private TableColumn<Course,Integer> creditsColumn;
    @FXML
    private TextField searchField;
    @FXML
    private TextField creditsTextfield;
    @FXML
    private Label dateField;
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private TableView<Enrollment> selectedTable;
    @FXML
    private TableColumn<Enrollment, String> selectedCourseCodeColumn;
    @FXML
    private TableColumn<Enrollment,String> selectedCourseNameColumn;
    @FXML
    private TableColumn<Enrollment,String>selectedStatusColumn;

    private static int credit = 0;
    private int studentId = StudentServiceImpl.studentId;
    private String studentName = StudentServiceImpl.studentName;

    private CourseServiceImpl courseService;
    ObservableList<Course> courseList;
    private EnrollmentServiceImpl enrollmentService;
    private  List<Enrollment> enrollments;

    @FXML
    public void initialize() {
        initializeServices();
        setupTables();
        setBackGroundColor();
        dateField.setText(DateTimeUtil.DateFormatter(LocalDateTime.now()));
        idLabel.setText(String.valueOf(studentId));
        nameLabel.setText(studentName);
        enrollments = enrollmentService.getAllEnrolledCourses(studentId);
        ObservableList<Enrollment> enrollmentObservableList = FXCollections.observableArrayList(enrollments);
        selectedTable.setItems(enrollmentObservableList);
    }

    private void initializeServices() {
        courseService = new CourseServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
    }

    private void setupTables() {
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        courseList = FXCollections.observableArrayList(courseService.availableCourses());
        courseTable.setItems(courseList);

        selectedCourseCodeColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue().getCourse();
            return new javafx.beans.property.SimpleStringProperty(course != null ? course.getCourse_code() : "");
        });

        selectedCourseNameColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue().getCourse();
            return new javafx.beans.property.SimpleStringProperty(course != null ? course.getCourse_name() : "");
        });

        selectedStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }



    @FXML
    private void handleSearchAction() {
        List<Course> courses= courseService.searchCourseByKeyword(searchField.getText());
        courseList.clear();
        courseList.addAll(courses);
    }

    @FXML
    private void handleCourseTableClick(MouseEvent event) {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            credit += selectedCourse.getCredits();
            if (credit > 20) {
                AlertUtil.alert(Constants.FieldConstraints.MAX_CREDITS, Constants.Alerts.ERROR);
                return;
            }
            creditsTextfield.setText(String.valueOf(credit));
            updateCourseAndEnrollment(selectedCourse);
        }
    }

    private void updateCourseAndEnrollment(Course selectedCourse) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse_id(selectedCourse);
        enrollment.setStatus("New");
        selectedTable.getItems().add(enrollment);
        courseTable.getItems().remove(selectedCourse);
    }

    @FXML
    private void handleResultTableClick(MouseEvent event) {
        Enrollment selectedEnrollment = selectedTable.getSelectionModel().getSelectedItem();
        if (selectedEnrollment != null && !selectedEnrollment.getStatus().equals("Registered")) {
            Course selectedCourse = selectedEnrollment.getCourse();
            credit -= selectedCourse.getCredits();
            creditsTextfield.setText(String.valueOf(credit));
            selectedTable.getItems().remove(selectedEnrollment);
           courseTable.getItems().add(selectedCourse);
        }
    }

    @FXML
    public void enrollmentBtn() {
        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();
        for (Enrollment enrollment : selectedTable.getItems()) {
            if (!enrollmentService.isEnrollmentRegistered(enrollment)) {
                EnrollmentDto enrollmentDto = new EnrollmentDto();
                enrollmentDto.setStudent_id(String.valueOf(studentId));
                enrollmentDto.setCourse(enrollment.getCourse().getCourse_name());
                enrollmentDtos.add(enrollmentDto);
                enrollment.setStatus("Registered");
            }
        }
        enrollmentService.saveEnrollment(enrollmentDtos);
        selectedTable.refresh();
    }

    @FXML
    private void mainPagebtn(ActionEvent event) {
        viewUtil.loadPage(event,"/org/example/mylearningproject/main-view.fxml");
    }

    private void setBackGroundColor(){
        selectedStatusColumn.setCellFactory(column -> new TableCell<Enrollment, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    setStyle(status.equalsIgnoreCase("registered") ? "-fx-background-color: lightgreen; -fx-text-fill: black;" : "");
                }
            }
        });
    }


}
