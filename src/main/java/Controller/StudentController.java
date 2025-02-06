package Controller;

import Model.Gender;
import Model.Student;
import Model.Teacher;
import Service.FacultyService;
import Service.impl.FacultyServiceImpl;
import Service.StudentService;
import Utils.AlertUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Model.Faculty;
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
    private RadioButton maleField;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private RadioButton femaleField;
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
    private Button selectImageButton;
    @FXML
    private ImageView imageView;
    private File selectedImageFile;


    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    private StudentService studentService;
    private FacultyService facultyService;

    @FXML
    public void initialize() {
        idField.setDisable(true);
        this.studentService = new StudentService();
        this.facultyService = new FacultyServiceImpl();

        if (genderGroup == null) {
            genderGroup = new ToggleGroup();
            maleField.setToggleGroup(genderGroup);
            femaleField.setToggleGroup(genderGroup);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        facultyColumn.setCellValueFactory(cellData -> {
            Faculty faculty = cellData.getValue().getFaculty();
            return new SimpleStringProperty(faculty != null ? faculty.getName() : "No Faculty");
        });
        choiceBoxField.getItems().addAll(facultyService.getAllFaculty().stream().map(Faculty::getName).toList());
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
        String facultyName=this.choiceBoxField.getSelectionModel().getSelectedItem();
        Faculty faculty = facultyService.findFacultyByName(facultyName.trim());
        Gender gender;
        if (maleField.isSelected()) {
            gender = Gender.MALE;
        } else if (femaleField.isSelected()) {
            gender = Gender.FEMALE;
        } else {
            return;
        }

        this.studentService.saveStudent(new Student(name, email,address,phone,faculty,gender));
        Student student=this.studentService.getStudentByEmail(email);
        try {
            saveStudentWithImage(student.getId());
        } catch (IOException e) {
            AlertUtil.alert("Failed to save image", "ERROR");
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
            String chosed = String.valueOf(student.getFaculty().getName());
            choiceBoxField.setValue(chosed);
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
        choiceBoxField.setValue(null);
    }

    @FXML
    public void openImageFileChooser() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        selectedImageFile = fileChooser.showOpenDialog(selectImageButton.getScene().getWindow());
        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imageView.setImage(image);
        }
    }
    public void saveStudentWithImage(int studentId) throws IOException {
        if (selectedImageFile != null) {
            try {
                // Define the target directory (project root)
                Path targetDirectory = Path.of(System.getProperty("user.dir")); // Get the project root directory
                Path targetFile = targetDirectory.resolve("student_images/" + studentId + ".jpg");

                // Create the student_images directory if it doesn't exist
                Files.createDirectories(targetFile.getParent());

                // Copy the image to the target directory
                Files.copy(selectedImageFile.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);

                // Here you would save the student object to the database with the image path
                System.out.println("Image saved at: " + targetFile.toString());

                // Example: Save the student object with the image path (assuming you have a Student object)
                // student.setImagePath(targetFile.toString());
                // studentService.save(student);  // Save the student to the database

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }
    }

}
