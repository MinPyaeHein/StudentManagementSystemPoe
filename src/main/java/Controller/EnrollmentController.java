package Controller;

import Constant.Constants;
import Dto.EnrollmentDto;
import Model.Course;
import Model.Enrollment;
import Service.impl.CourseServiceImpl;
import Service.impl.EnrollmentServiceImpl;
import Service.impl.SemesterServiceImpl;
import Service.impl.StudentServiceImpl;
import Utils.AlertUtil;
import Utils.DateTimeUtil;
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
    private SemesterServiceImpl semesterService;
    private  List<Enrollment> enrollments;

    @FXML
    public void initialize() {
        initializeServices();
        courseTableSetup();
        selectedTableSetup();
        setBackGroundColor();
        dateField.setText(String.valueOf(semesterService.getActiveSemesterStartDate()));
        idLabel.setText(String.valueOf(studentId));
        nameLabel.setText(studentName);
        enrollments = enrollmentService.getAllEnrolledCoursesByStudentId(studentId);
        ObservableList<Enrollment> enrollmentObservableList = FXCollections.observableArrayList(enrollments);
        selectedTable.setItems(enrollmentObservableList);
        calculateCredit();
        selectedTable.setItems(FXCollections.observableArrayList(enrollments));

    }

    private void initializeServices() {
        courseService = new CourseServiceImpl();
        enrollmentService = new EnrollmentServiceImpl();
        semesterService =new SemesterServiceImpl();
    }

    private void courseTableSetup() {
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        courseList = FXCollections.observableArrayList(courseService.availableCourses());
        courseTable.setItems(courseList);
    }

    private void selectedTableSetup() {
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
            int newTotalCredits = credit + selectedCourse.getCredits();

            if (newTotalCredits > Constants.FieldConstraints.CREDITS_LIMIT) {
                AlertUtil.alert(Constants.FieldConstraints.MAX_CREDITS, Constants.Alerts.ERROR);
                return;
            }
            creditsTextfield.setText(String.valueOf(newTotalCredits));
            updateCourseAndEnrollment(selectedCourse);
        }
    }


    private void updateCourseAndEnrollment(Course selectedCourse) {
        Enrollment enrollment = new Enrollment();
        enrollment.setCourse_id(selectedCourse);
        enrollment.setStatus(Constants.FieldConstraints.NEW_STATUS);
        selectedTable.getItems().add(enrollment);
        courseTable.getItems().remove(selectedCourse);
    }

    @FXML
    private void selectedCourseTable(MouseEvent event) {
        Enrollment selectedEnrollment = selectedTable.getSelectionModel().getSelectedItem();
        if (selectedEnrollment != null && !selectedEnrollment.getStatus().equals(Constants.FieldConstraints.REGISTER_STATUS)) {
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
            if(!enrollment.getStatus().equals(Constants.FieldConstraints.REGISTER_STATUS)) {
                EnrollmentDto enrollmentDto = new EnrollmentDto();
                enrollmentDto.setStudent_id(String.valueOf(studentId));
                enrollmentDto.setCourse(enrollment.getCourse().getCourse_name());
                enrollmentDto.setStatus(Constants.FieldConstraints.REGISTER_STATUS);
                enrollmentDtos.add(enrollmentDto);
                setBackGroundColor();
            }
        }
        enrollmentService.saveEnrollment(enrollmentDtos);
        reloadEnrollmentTable();

    }
    private void reloadEnrollmentTable() {
        List<Enrollment> enrollments = enrollmentService.getAllEnrolledCoursesByStudentId(studentId);
        calculateCredit();
        selectedTable.getItems().setAll(FXCollections.observableArrayList(enrollments));
        selectedTable.refresh();
    }

    private void calculateCredit(){
        credit = 0;
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStatus().equals(Constants.FieldConstraints.REGISTER_STATUS)) {
                credit += enrollment.getCourse().getCredits();
            }
        }
        creditsTextfield.setText(String.valueOf(credit));
    }


    @FXML
    private void mainPagebtn(ActionEvent event) {
        viewUtil.loadPage(event,Constants.Views.MAIN_VIEW);
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
                    setStyle(status.equals(Constants.FieldConstraints.REGISTER_STATUS) ? "-fx-background-color: lightgreen; -fx-text-fill: black;" : "");
                }
            }
        });
    }


}
