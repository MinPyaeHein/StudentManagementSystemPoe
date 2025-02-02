package Controller;

import Model.Teacher;
import Service.TeacherService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class TeacherController {

    @FXML
    private TableView<Teacher> teacherTable;
    @FXML
    private TableColumn<Teacher, Integer> idColumn;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TableColumn<Teacher, String> emailColumn;
    @FXML
    private TableColumn<Teacher,String>  addressColumn;
    @FXML
    private TableColumn<Teacher,String>  phoneColumn;


    @FXML
    private TextField searchField;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;

    private final ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    private TeacherService teacherService;
    @FXML
    public void initialize() {
        idField.setDisable(true);
        this.teacherService = new TeacherService();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        teacherTable.setItems(teacherList);
        loadDummyData();
    }

    private void loadDummyData() {
        teacherList.clear();
        teacherList.addAll(teacherService.getAllTeacher());
    }

    @FXML
    private void addTeacher() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        this.teacherService.saveTeacher(new Teacher(name, email,address,phone));
        this.loadDummyData();
        clearFields();
    }
    @FXML
    private void cleanForm(){
        clearFields();
    }

    @FXML
    private void deleteTeacher() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            this.teacherService.delete(selectedTeacher.getId());
            loadDummyData();
            clearFields();
        }
    }

    @FXML
    private void updateTeacher() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            selectedTeacher.setName(nameField.getText());
            selectedTeacher.setEmail(emailField.getText());
            selectedTeacher.setAddress(addressField.getText());
            selectedTeacher.setAddress(phoneField.getText());
            this.teacherService.update(selectedTeacher);
            teacherTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Teacher teacher = teacherTable.getSelectionModel().getSelectedItem();
        if (teacher != null) {
            idField.setText(String.valueOf(teacher.getId()));
            nameField.setText(teacher.getName());
            emailField.setText(teacher.getEmail());
            addressField.setText(teacher.getAddress());
            phoneField.setText(teacher.getPhone());
        }
    }
    @FXML
    private void handleSearchAction() {
        List<Teacher> resultTeacher=this.teacherService.searchTeachersByName(searchField.getText());
        teacherList.clear();
        teacherList.addAll(resultTeacher);
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        addressField.clear();
        phoneField.clear();
    }
}
