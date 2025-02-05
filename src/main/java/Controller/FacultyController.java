package Controller;

import Model.Faculty;
import Service.impl.FacultyServiceImpl;
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

public class FacultyController {

    @FXML
    private TableView<Faculty> facultyTable;
    @FXML
    private TableColumn<Faculty, Integer> idColumn;
    @FXML
    private TableColumn<Faculty, String> nameColumn;
    @FXML
    private TableColumn<Faculty, String> emailColumn;
    @FXML
    private TableColumn<Faculty, String> websiteLinkColumn;
    @FXML
    private TableColumn<Faculty, String> phoneColumn;

    @FXML
    private TextField searchField;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField websiteField;
    @FXML
    private TextField phoneField;

    private ObservableList<Faculty> facultyList = FXCollections.observableArrayList();
    private FacultyServiceImpl facultyService;

    @FXML
    public void initialize() {
        idField.setDisable(true);
        this.facultyService = new FacultyServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        websiteLinkColumn.setCellValueFactory(new PropertyValueFactory<>("website_link"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        facultyTable.setItems(facultyList);
        loadDummyData();
    }

    private void loadDummyData() {
        facultyList.clear();
        facultyList.addAll(facultyService.getAllFaculty());
    }

    @FXML
    private void addFaculty() {
        String name = nameField.getText();
        String email = emailField.getText();
        String websiteLink = websiteField.getText();
        String phone = phoneField.getText();
        this.facultyService.saveFaculty(new Faculty(name, email,websiteLink,phone));
        this.loadDummyData();
        clearFields();
    }
    @FXML
    private void clearForm() {
        clearFields();
    }

    @FXML
    private void deleteFaculty() {
        Faculty selectedTeacher = facultyTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            this.facultyService.delete(selectedTeacher.getId());
            loadDummyData();
            clearFields();
        }
    }

    @FXML
    private void updateFaculty() {
        Faculty selectedFaculty = facultyTable.getSelectionModel().getSelectedItem();
        if(selectedFaculty == null){
            AlertUtil.alert("Please select a faculty from the table to update.", "ERROR");
            clearFields();
            return;
        }
        if (selectedFaculty != null) {
            selectedFaculty.setName(nameField.getText());
            selectedFaculty.setEmail(emailField.getText());
            selectedFaculty.setWebsite_link(websiteField.getText());
            selectedFaculty.setPhone(phoneField.getText());
            this.facultyService.update(selectedFaculty);
            facultyTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Faculty faculty = facultyTable.getSelectionModel().getSelectedItem();
        if (faculty != null) {
            idField.setText(String.valueOf(faculty.getId()));
            nameField.setText(faculty.getName());
            emailField.setText(faculty.getEmail());
            websiteField.setText(faculty.getWebsite_link());
            phoneField.setText(faculty.getPhone());
        }
    }

    @FXML
    private void handleSearchAction() {
        List<Faculty> resultFaculty= this.facultyService.searchFacultyByKeyword(searchField.getText());
        facultyList.clear();
        facultyList.addAll(resultFaculty);
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        websiteField.clear();
        phoneField.clear();
    }
}


