package Controller;

import Model.Gender;
import Model.Student;
import Service.FacultyService;
import Service.impl.FacultyServiceImpl;
import Service.impl.StudentServiceImpl;
import Utils.AlertUtil;
import javafx.scene.image.ImageView;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Model.Faculty;
import Exception.*;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
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
    private File selectedImageFile;

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

        choiceBoxField.getItems().add("----Plese Select One Faculty ----");
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
    private void addStudent() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address =addressField.getText();
        String phone = phoneField.getText();
        try {
            Toggle selectedToggle = genderGroup.getSelectedToggle();
            Gender gender = (selectedToggle != null) ? Gender.valueOf(((RadioButton) selectedToggle).getText().toLowerCase()) : null;
            String facultyName=this.choiceBoxField.getSelectionModel().getSelectedItem();
            Faculty faculty = facultyService.findFacultyByName(facultyName);
            this.studentService.saveStudent(new Student(name, email,address,phone,faculty,gender));
            Student student=this.studentService.getStudentByEmail(email);
            saveImageWithStudentId(student.getId());
        }catch (InvalidDataFormatException e) {
            AlertUtil.alert(e.getMessage(), "ERROR");
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(), "ERROR");
        }
        this.loadDummyData();
        clearFields();
    }

    @FXML
    private void deleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
           this.studentService.delete(selectedStudent.getId());
           loadDummyData();
           clearFields();
        }
    }
    @FXML
    private void clearForm() {
            clearFields();
    }

    @FXML
    private void updateStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedStudent);
        if(selectedStudent == null){
            AlertUtil.alert("Please select a Student from the table to update.", "ERROR");
            clearFields();
            return;
        }
        if (selectedStudent != null) {
            selectedStudent.setId(Integer.parseInt(idField.getText()));
            selectedStudent.setName(nameField.getText());
            selectedStudent.setEmail(emailField.getText());
            selectedStudent.setAddress(addressField.getText());
            selectedStudent.setPhone(phoneField.getText());
            String facultyName=this.choiceBoxField.getSelectionModel().getSelectedItem();
            Faculty faculty = facultyService.findFacultyByName(facultyName);
            selectedStudent.setFaculty(faculty);
            String genderStr=((RadioButton) genderGroup.getSelectedToggle()).getText().toLowerCase();
            Gender gender = Gender.valueOf(genderStr);
            selectedStudent.setGender(gender);
            this.studentService.update(selectedStudent);
            studentTable.refresh();
            loadDummyData();
            clearFields();
        }
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
            displayProfileImage(student.getId());
            String chosed = String.valueOf(student.getFaculty().getName());
            choiceBoxField.setValue(chosed);
        }
    }
    @FXML
    private void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedImageFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    public void saveImageWithStudentId(int studentId) throws IOException {
        if (selectedImageFile != null) {
            try {
                Path targetDirectory = Path.of(System.getProperty("user.dir"));
                System.out.println("Target directory: " + targetDirectory.toString());
                Path targetFile = targetDirectory.resolve("student_images/" + studentId + ".jpg");
                Files.createDirectories(targetFile.getParent());
                Files.copy(selectedImageFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image saved at: " + targetFile.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    }

    private void displayProfileImage(int studentId){
        try {
            String imagePath = System.getProperty("user.dir") + "/student_images/" + studentId + ".jpg";
            Image image = new Image("file:" + imagePath);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
