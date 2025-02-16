package Controller;

import Model.Course;
import Service.impl.CourseServiceImpl;
import Service.impl.EnrollmentServiceImpl;
import Utils.AlertUtil;
import Utils.UtilConstants;
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

    private static int credit = 0;


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
                AlertUtil.alert(UtilConstants.CREDITS_MAXIMUM, UtilConstants.ERROR_ALERT);
                return;
            }
           creditsTextfield.setText(String.valueOf(credit));
            selectedTable.getItems().add(selectedCourse);
            courseTable.getItems().remove(selectedCourse);
        }
    }
    @FXML
    private void handleResultTableClick(MouseEvent event){
        Course selectedCourse = selectedTable.getSelectionModel().getSelectedItem();
        if(selectedCourse !=null){
            courseTable.getItems().add(selectedCourse);
            selectedTable.getItems().remove(selectedCourse);
            credit -= selectedCourse.getCredits();
            creditsTextfield.setText(String.valueOf(credit));
        }

    }
}
