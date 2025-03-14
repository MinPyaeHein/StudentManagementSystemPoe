package Controller;

import Constant.Constants;
import Dto.StudentDto;
import Model.Gender;
import Model.Student;
import Service.FacultyService;
import Service.impl.FacultyServiceImpl;
import Service.impl.StudentServiceImpl;
import Utils.AlertUtil;
import Utils.ImgUtil;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Model.Faculty;
import java.io.IOException;
import java.util.List;

public class StudentController {

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, Integer> idColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;
    @FXML
    private TableColumn<Student, String> addressColumn;
    @FXML
    private TableColumn<Student, String> phoneColumn;
    @FXML
    private TableColumn<Student, String> facultyColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private TableColumn<Student, String> passwordColumn;

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
    private ToggleGroup genderGroup;

    @FXML
    private RadioButton maleField;

    @FXML
    private RadioButton femaleField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button selectImageButton;
    @FXML
    private TextField passwordField;




    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    private StudentServiceImpl studentService;
    private FacultyService facultyService;

    @FXML
    public void initialize() {
        genderGroup = new ToggleGroup();
        maleField.setToggleGroup(genderGroup);
        femaleField.setToggleGroup(genderGroup);

        idField.setDisable(true);
        this.studentService = new StudentServiceImpl();
        this.facultyService = new FacultyServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));


           facultyColumn.setCellValueFactory(cellData -> {
               Faculty faculty = cellData.getValue().getFaculty();
               return new SimpleStringProperty(faculty != null ? faculty.getName() : "No Faculty");
           });

        genderColumn.setCellValueFactory(cellData -> {
            Gender gender = cellData.getValue().getGender();
            String genderStr=null;
            if(gender != null){
                genderStr = gender.name();
                if(genderStr.equalsIgnoreCase("male")){
                    genderStr = "Male";
                }else if(genderStr.equalsIgnoreCase("female")){
                    genderStr = "Female";
                }
            }
            return new SimpleStringProperty(genderStr);
         });
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        choiceBoxField.getItems().add(Constants.Selections.SELECT_ITEM);
        choiceBoxField.getItems().addAll(facultyService.getAllFaculty().stream().map(Faculty::getName).toList());
        choiceBoxField.getSelectionModel().selectFirst();
        studentTable.setItems(studentList);
        loadDummyData();
    }

    private void loadDummyData() {
        studentList.clear();
        studentList.addAll(studentService.getAllStudent()) ;
    }

    @FXML
    private void addStudent() throws IOException {
       if(AlertUtil.getSelectedItem(studentTable, "student") !=null) {
           StudentDto studentDto = new StudentDto();
           studentDto.setName(nameField.getText());
           studentDto.setEmail(emailField.getText());
           studentDto.setAddress(addressField.getText());
           studentDto.setPhone(phoneField.getText());
           studentDto.setGender(maleField.isSelected() ? "male" : "female");
           studentDto.setFaculty(choiceBoxField.getSelectionModel().getSelectedItem());
           studentDto.setImageFile(ImgUtil.selectedImageFile);
           studentDto.setPassword(passwordField.getText());

           this.studentService.saveStudent(studentDto);
       }
        this.loadDummyData();
        clearFields();
    }

    @FXML
    private void deleteStudent() {
        if(AlertUtil.getSelectedItem(studentTable, "student") !=null) {
            StudentDto studentDto = new StudentDto();
            studentDto.setId(idField.getText());
            studentDto.setImageFile(ImgUtil.selectedImageFile);
            this.studentService.delete(studentDto);
        }
        loadDummyData();
        clearFields();
    }

    @FXML
    private void clearForm() {
            clearFields();
    }

    @FXML
    private void updateStudent() {
        StudentDto studentDto =new StudentDto();
            studentDto.setId(idField.getText());
            studentDto.setName(nameField.getText());
            studentDto.setEmail(emailField.getText());
            studentDto.setAddress(addressField.getText());
            studentDto.setPhone(phoneField.getText());
            studentDto.setFaculty(this.choiceBoxField.getSelectionModel().getSelectedItem());
            studentDto.setGender(maleField.isSelected() ? "male" : "female");
            studentDto.setImageFile(ImgUtil.selectedImageFile);
            studentDto.setPassword(passwordField.getText());
            this.studentService.update(studentDto);
            studentTable.refresh();
            loadDummyData();
            clearFields();
        }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Student student = studentTable.getSelectionModel().getSelectedItem();
        if (student != null) {
            idField.setText(String.valueOf(student.getId()));
            nameField.setText(student.getName());
            emailField.setText(student.getEmail());
            addressField.setText(student.getAddress());
            phoneField.setText(student.getPhone());

            if (student.getGender() != null) {
                if (student.getGender() == Gender.male) {
                    genderGroup.selectToggle(maleField);
                } else if (student.getGender() == Gender.female) {
                    genderGroup.selectToggle(femaleField);
                }
            }
            ImgUtil.displayProfileImage(student.getId(), Constants.ImagePaths.STUDENT_FOLDER,imageView);
            String chosed = String.valueOf(student.getFaculty().getName());
            passwordField.setText(student.getPassword());
            choiceBoxField.setValue(chosed);
        }
    }
    @FXML
    private void openImageFileChooser() {
       ImgUtil.openFileChooser(selectImageButton,imageView);
    }

    @FXML
    private void handleSearchAction() {
        List<Student> resultStudent=this.studentService.searchStudentByKeyword(searchField.getText());
        studentList.clear();
        studentList.addAll(resultStudent);
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        choiceBoxField.getSelectionModel().selectFirst();
        genderGroup.selectToggle(null);
        imageView.setImage(null);
        passwordField.clear();
    }
}
