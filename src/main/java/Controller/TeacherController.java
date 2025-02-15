package Controller;

import Dto.TeacherDto;
import Model.*;
import Service.impl.DegreeServiceImpl;
import Service.impl.DepartmentServiceImpl;
import Service.impl.TeacherServiceImpl;
import Utils.AlertUtil;
import Utils.ImgUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Exception.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static Utils.AlertUtil.getSelectedItem;

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
    @FXML
    private ImageView imageView;
    @FXML
    private Button selectImageButton;



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
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));

        departmentColumn.setCellValueFactory(cellData -> {
            Department department = cellData.getValue().getDepartment();
            return new SimpleStringProperty(department != null ? department.getDepartment() : "No Department");
        });
        degreeColumn.setCellValueFactory(cellData -> {
            Degree degree = cellData.getValue().getDegree();
            return new SimpleStringProperty(degree != null ? degree.getDegree() : "No Degree");
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
        TeacherDto teacherDto=new TeacherDto();
        teacherDto.setName(nameField.getText());
        teacherDto.setEmail(emailField.getText());
        teacherDto.setAddress(addressField.getText());
        teacherDto.setPhone(phoneField.getText());
        teacherDto.setDegree(degreeChoiceField.getSelectionModel().getSelectedItem());
        teacherDto.setDepartment(choiceBoxField.getSelectionModel().getSelectedItem());
        teacherDto.setGender(maleField.isSelected() ? "male" : "female");
        teacherDto.setImageFile(ImgUtil.selectedImageFile);
        this.teacherService.saveTeacher(teacherDto);

        this.loadDummyData();
        clearFields();
    }



    @FXML
    private void cleanForm(){
        clearFields();
    }

    @FXML
    private void deleteTeacher() {
        AlertUtil.getSelectedItem(teacherTable, "teacher");
        TeacherDto teacherDto=new TeacherDto();
        teacherDto.setId(idField.getText());
        teacherDto.setImageFile(ImgUtil.selectedImageFile);
         this.teacherService.delete(teacherDto);
            loadDummyData();
            clearFields();
    }

    @FXML
    private void updateTeacher() {
        AlertUtil.getSelectedItem(teacherTable, "teacher");

        TeacherDto teacherDto=new TeacherDto();
        teacherDto.setId(idField.getText());
        teacherDto.setName(nameField.getText());
        teacherDto.setEmail(emailField.getText());
        teacherDto.setAddress(addressField.getText());
        teacherDto.setPhone(phoneField.getText());
        teacherDto.setDegree(this.degreeChoiceField.getSelectionModel().getSelectedItem());
        teacherDto.setDepartment(this.choiceBoxField.getSelectionModel().getSelectedItem());
        teacherDto.setGender(maleField.isSelected() ? "male" : "female");
        teacherDto.setImageFile(ImgUtil.selectedImageFile);
        this.teacherService.update(teacherDto);
        teacherTable.refresh();
        loadDummyData();
        clearFields();
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
            ImgUtil.displayProfileImage(teacher.getId(),"/teachers_images/",imageView);
        }
    }

    @FXML
    private void openImageFileChooser() {
        ImgUtil.openFileChooser(selectImageButton,imageView);
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
        imageView.setImage(null);
    }
}
