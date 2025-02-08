package Controller;

import Model.*;
import Service.impl.DegreeServiceImpl;
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
import Exception.*;

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
    private TableColumn<Teacher, String> genderColumn;

    @FXML
    private ChoiceBox<String> choiceBoxField;
    @FXML
    private ChoiceBox<String> degreeChoiceField;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton maleField;
    @FXML
    private RadioButton femaleField;
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
    private TeacherServiceImpl teacherService;
    private DepartmentServiceImpl departmentService;
    private DegreeServiceImpl degreeService;
    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        maleField.setToggleGroup(genderGroup);
        femaleField.setToggleGroup(genderGroup);

        idField.setDisable(true);
        this.teacherService = new TeacherServiceImpl();
        this.departmentService=new DepartmentServiceImpl();
        this.degreeService=new DegreeServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree.name"));
        degreeColumn.setCellValueFactory(cellData -> {
            Degree degree = cellData.getValue().getDegree();
            return new SimpleStringProperty(degree != null ? degree.getDegree() : "No Degree");
        });

        departmentColumn.setCellValueFactory(cellData -> {
            Department department = cellData.getValue().getDepartment();
            return new SimpleStringProperty(department != null ? department.getDepartment() : "No Department");
        });


        degreeChoiceField.getItems().add("--Please select one Degree--");
        degreeChoiceField.getItems().addAll(degreeService.getAllDegree().stream().map(Degree::getDegree).toList());
        degreeChoiceField.getSelectionModel().selectFirst();  // Select first item


        choiceBoxField.getItems().add("--Please select one Department--");
        choiceBoxField.getItems().addAll(departmentService.getAllDepartment().stream().map(Department::getDepartment).toList());
        choiceBoxField.getSelectionModel().selectFirst();  // Select first item


        genderColumn.setCellValueFactory(cellData ->{
            Gender gender = cellData.getValue().getGender();
            String genderStr = null;
            if(gender!=null) {
                genderStr = gender.name();
                if (genderStr.equalsIgnoreCase("male")) {
                    genderStr = "Male";
                } else if (genderStr.equalsIgnoreCase("femal")) {
                    genderStr = "Female";
                }
            }
            return new SimpleStringProperty(genderStr);

        });
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
        try {
            String degreeName = this.degreeChoiceField.getSelectionModel().getSelectedItem();
            String departmentName = this.choiceBoxField.getSelectionModel().getSelectedItem();
            Department department = departmentService.findDepartmentByName(departmentName);
            Degree degree = degreeService.findDegreeByName(degreeName);
            Toggle selectedToggle = genderGroup.getSelectedToggle();
            Gender gender = (selectedToggle != null) ? Gender.valueOf(((RadioButton) selectedToggle).getText().toLowerCase()) : null;
            this.teacherService.saveTeacher(new Teacher(name, email, address, phone, degree, department, gender));
        }catch (InvalidDataFormatException e) {
            AlertUtil.alert(e.getMessage(), "ERROR");
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
            return;
        }
        if (selectedTeacher != null) {
               selectedTeacher.setName(nameField.getText());
               selectedTeacher.setEmail(emailField.getText());
               selectedTeacher.setAddress(addressField.getText());
               selectedTeacher.setPhone(phoneField.getText());
               String degreeName = this.degreeChoiceField.getSelectionModel().getSelectedItem();
               Degree degree = degreeService.findDegreeByName(degreeName);
               selectedTeacher.setDegree(degree);
               String departmentName=this.choiceBoxField.getSelectionModel().getSelectedItem();
               Department department = departmentService.findDepartmentByName(departmentName);
               selectedTeacher.setDepartment(department);
               String genderStr=((RadioButton) genderGroup.getSelectedToggle()).getText().toLowerCase();
               Gender gender = Gender.valueOf(genderStr);
               selectedTeacher.setGender(gender);
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
            String degreeChosed = String.valueOf(teacher.getDegree().getDegree());
            degreeChoiceField.setValue(degreeChosed);
            String chosed = String.valueOf(teacher.getDepartment().getDepartment());
            choiceBoxField.setValue(chosed);
            if(teacher.getGender()!=null){
                if(teacher.getGender() == Gender.male){
                    genderGroup.selectToggle(maleField);
                }else if(teacher.getGender() == Gender.female){
                    genderGroup.selectToggle(femaleField);
                }
            }
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
        degreeChoiceField.getSelectionModel().selectFirst();
        choiceBoxField.getSelectionModel().selectFirst();
        genderGroup.selectToggle(null);
    }
}
