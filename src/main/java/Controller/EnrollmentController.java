package Controller;

import Model.Course;
import Service.impl.CourseServiceImpl;
import Utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


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
    private TableView<Course> selectedTable;
    @FXML
    private TableColumn<Course, String> selectedCourseCodeColumn;
    @FXML
    private TableColumn<Course,String> selectedCourseNameColumn;
    @FXML
    private TableColumn<Course, String> actionColumn;

    private int credit = 0;


    private CourseServiceImpl courseService;
    ObservableList<Course> courseList;

    @FXML
    public void initialize(){
        courseService=new CourseServiceImpl();
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("credits"));
        courseList  = FXCollections.observableArrayList(courseService.getAllCourses());
        courseTable.setItems(courseList);
        selectedCourseNameColumn.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        selectedCourseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));


    }

    @FXML
    private void handleSearchAction() {
        List<Course> resultStudent= courseService.searchCourseByKeyword(searchField.getText());
        courseList.clear();
        courseList.addAll(resultStudent);
    }

    @FXML
    private void handleTableClick(MouseEvent event) {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            selectedTable.getItems().add(selectedCourse);
            courseTable.getItems().remove(selectedCourse);
             credit += selectedCourse.getCredits();
             if(credit < 20){
                 creditsTextfield.setText(String.valueOf(credit));
            }else{
                 AlertUtil.alert("credit can not be more than 20","Error");
             }


        }
    }




}
