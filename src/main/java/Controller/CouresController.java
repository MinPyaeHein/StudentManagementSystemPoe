package Controller;

import Dto.CourseDto;
import Model.*;

import Service.impl.CourseServiceImpl;
import Service.impl.DepartmentServiceImpl;
import Service.impl.TeacherServiceImpl;
import Utils.AlertUtil;
import Utils.DateTimeUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CouresController {

    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, Integer> idColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> courseCodeColumn;
    @FXML
    private TableColumn<Course, String> descriptionColumn;
    @FXML
    private TableColumn<Course, Integer> creditsColumn;
    @FXML
    private TableColumn<Course, String> departmentColumn;
    @FXML
    private TableColumn<Course, String> teacherColumn;
    @FXML
    private TableColumn<Course,Integer> capacityColumn;
    @FXML
    private TableColumn<Course,String> createdTimeColumn;
    @FXML
    private TableColumn<Course,String> updatedTimeColumn;

    @FXML
    private TextField idField;
    @FXML
    private TextField courseNameField;
    @FXML
    private TextField courseCodeField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField creditField;
    @FXML
    private ChoiceBox<String> departmentChoiceBox;
    @FXML
    private ChoiceBox<String> teacherChoiceBox;
    @FXML
    private TextField searchField;
    @FXML
    private TextField capacityFied;



    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    private DepartmentServiceImpl departmentService;
    private TeacherServiceImpl teacherService;
    private CourseServiceImpl courseService;

    @FXML
    public void initialize(){
        idField.setDisable(true);

        this.departmentService =new DepartmentServiceImpl();
        this.teacherService = new TeacherServiceImpl();
        this.courseService = new CourseServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));

        departmentColumn.setCellValueFactory(cellData -> {
            Department department = cellData.getValue().getDepartment();
            return new SimpleStringProperty(department != null ? department.getDepartment() : "No Department");
        });

        departmentChoiceBox.getItems().add("--Please select one Department--");
        departmentChoiceBox.getItems().addAll(departmentService.getAllDepartment().stream().map(Department::getDepartment).toList());
        departmentChoiceBox.getSelectionModel().selectFirst();
        teacherColumn.setCellValueFactory(cellData -> {
            Teacher teacher = cellData.getValue().getTeacher();
            return new SimpleStringProperty(teacher != null ? teacher.getName(): "No Teacher Name");
        });

        teacherChoiceBox.getItems().add("--Please select one teacher--");
        teacherChoiceBox.getItems().addAll(teacherService.getAllTeacher().stream().map(Teacher::getName).toList());
        teacherChoiceBox.getSelectionModel().selectFirst();

        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        createdTimeColumn.setCellValueFactory(cellData -> {
            LocalDateTime createdAt = cellData.getValue().getCreated_at();
            return new SimpleStringProperty(DateTimeUtil.DateFormatter(createdAt));

        });
        updatedTimeColumn.setCellValueFactory(cellData -> {
            LocalDateTime updatedAt = cellData.getValue().getUpdated_at();
            try{
                return new SimpleStringProperty(DateTimeUtil.DateFormatter(updatedAt));
            } catch (RuntimeException e) {
                return new SimpleStringProperty("Not updated yet");
            }

        });

        courseTable.setItems(courseList);
        loadDummyData();
    }

    private void loadDummyData() {
        courseList.clear();
        courseList.addAll(courseService.getAllCourses());
    }

    @FXML
    private void addCourse(){
        CourseDto courseDto=new CourseDto();
        courseDto.setCourse_name(courseNameField.getText());
        courseDto.setCourse_code(courseCodeField.getText());
        courseDto.setDescription(descriptionField.getText());
        courseDto.setCredits(creditField.getText());
        courseDto.setDepartment(this.departmentChoiceBox.getSelectionModel().getSelectedItem());
        courseDto.setTeacher(this.teacherChoiceBox.getSelectionModel().getSelectedItem());
        courseDto.setCapacity(capacityFied.getText());
        courseDto.setCreated_at(LocalDateTime.now());
        this.courseService.saveCourse(courseDto);
            loadDummyData();
            clearFields();
    }
    @FXML
    private void deleteCourse(){
        CourseDto courseDto=new CourseDto();
        courseDto.setId(idField.getText());
            this.courseService.delete(courseDto);
        loadDummyData();
        clearFields();
    }
    @FXML
    private void cleanForm() {
        clearFields();
    }

    @FXML
    private void updateCourse(){
        CourseDto courseDto=new CourseDto();
        courseDto.setId(idField.getText());
        courseDto.setCourse_name(courseNameField.getText());
        courseDto.setCourse_code(courseCodeField.getText());
        courseDto.setDescription(descriptionField.getText());
        courseDto.setCredits(creditField.getText());
        courseDto.setDepartment(this.departmentChoiceBox.getSelectionModel().getSelectedItem());
        courseDto.setTeacher(this.teacherChoiceBox.getSelectionModel().getSelectedItem());
        courseDto.setCapacity(capacityFied.getText());
        LocalDateTime updatedTime = LocalDateTime.now();
        courseDto.setUpdated_at(updatedTime);
        this.courseService.update(courseDto);

        courseTable.refresh();
        loadDummyData();
        clearFields();
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Course course=courseTable.getSelectionModel().getSelectedItem();
        if(course!=null){
            idField.setText(String.valueOf(course.getId()));
            courseNameField.setText(course.getCourse_name());
            courseCodeField.setText(course.getCourse_code());
            descriptionField.setText(course.getDescription());
            creditField.setText(String.valueOf(course.getCredits()));
            String department = String.valueOf(course.getDepartment().getDepartment());
            departmentChoiceBox.setValue(department);
            String teacher = String.valueOf(course.getTeacher().getName());
            teacherChoiceBox.setValue(teacher);
            capacityFied.setText(String.valueOf(course.getCapacity()));

        }
    }

    @FXML
    private void handleSearchAction() {
        List<Course> resultCourse=this.courseService.searchCourseByKeyword(searchField.getText());
        courseList.clear();
        courseList.addAll(resultCourse);
    }

    private void clearFields() {
        idField.clear();
        courseNameField.clear();
        courseCodeField.clear();
        descriptionField.clear();
        creditField.clear();
        departmentChoiceBox.getSelectionModel().selectFirst();
        teacherChoiceBox.getSelectionModel().selectFirst();
        capacityFied.clear();



    }

}
