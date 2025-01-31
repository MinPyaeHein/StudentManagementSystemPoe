package Controller;

import Model.Teacher;
import Service.TeacherService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;

    private final ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    private TeacherService teacherService;
    @FXML
    public void initialize() {
        this.teacherService = new TeacherService();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));


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
        this.teacherService.saveTeacher(new Teacher( name, email,address));
        this.loadDummyData();
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
            this.teacherService.update(selectedTeacher);
            teacherTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Teacher teacher = teacherTable.getSelectionModel().getSelectedItem();
        if (teacher != null) {
            nameField.setText(teacher.getName());
            emailField.setText(teacher.getEmail());
            addressField.setText(teacher.getAddress());
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        addressField.clear();
    }
}
