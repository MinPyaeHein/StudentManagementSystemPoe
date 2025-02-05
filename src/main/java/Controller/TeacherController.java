package Controller;

import Model.Department;
import Model.Teacher;
import Service.impl.DepartmentServiceImpl;
import Service.impl.TeacherServiceImpl;
import Utils.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Teacher,String>  departmentColumn;
    @FXML
    private TableColumn<Teacher,String>  degreeColumn;
    @FXML
    private ChoiceBox<String> choiceBoxField;



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
    @FXML
    private TextField degreeField;


    private final ObservableList<Teacher> teacherList = FXCollections.observableArrayList();
    private TeacherServiceImpl teacherService;
    private DepartmentServiceImpl departmentService;
    @FXML
    public void initialize() {
        idField.setDisable(true);
        this.teacherService = new TeacherServiceImpl();
        this.departmentService=new DepartmentServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department.name"));
        departmentColumn.setCellValueFactory(cellData -> {
            Department department = cellData.getValue().getDepartment();
            return new SimpleStringProperty(department != null ? department.getDepartment() : "No Department");
        });
        choiceBoxField.getItems().addAll(departmentService.getAllDepartment().stream().map(Department::getDepartment).toList());
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
        String degree = degreeField.getText();
        String departmentName=this.choiceBoxField.getSelectionModel().getSelectedItem();
        try {
            Department department= departmentService.findDepartmentByName(departmentName);
            this.teacherService.saveTeacher(new Teacher(name, email,address,phone,degree,department));
        }catch(NullPointerException e){
            AlertUtil.alert(e.getMessage(),"ERROR" );
        }
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
        if(selectedTeacher == null){
            AlertUtil.alert("Please select a teacher from the table to update.", "ERROR");
            clearFields();
        }
        if (selectedTeacher != null) {
               selectedTeacher.setName(nameField.getText());
               selectedTeacher.setEmail(emailField.getText());
               selectedTeacher.setAddress(addressField.getText());
               selectedTeacher.setPhone(phoneField.getText());
               selectedTeacher.setDegree(degreeField.getText());
               String departmentName=this.choiceBoxField.getSelectionModel().getSelectedItem();
               Department department = departmentService.findDepartmentByName(departmentName);
            selectedTeacher.setDepartment(department);
               this.teacherService.update(selectedTeacher);
               teacherTable.refresh();
               loadDummyData();
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
            degreeField.setText(teacher.getDegree());
            String chosed = String.valueOf(teacher.getDepartment().getDepartment());
            choiceBoxField.setValue(chosed);
        }
    }
    @FXML
    private void handleSearchAction() {
        List<Teacher> resultTeacher=this.teacherService.searchTeacherByKeyword(searchField.getText());
        teacherList.clear();
        teacherList.addAll(resultTeacher);
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        addressField.clear();
        phoneField.clear();
        degreeField.clear();
        choiceBoxField.setValue(null);
    }
}
