package Controller;


import Model.Department;
import Service.impl.DepartmentServiceImpl;
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

public class DepartmentController {
    @FXML
    private TableView<Department> departmentTable;
    @FXML
    private TableColumn<Department, Integer> idColumn;
    @FXML
    private TableColumn<Department, String> departmentColumn;
    @FXML
    private TextField searchField;
    @FXML
    private TextField idField;
    @FXML
    private TextField departmentField;

    private ObservableList<Department> departmentsList = FXCollections.observableArrayList();
    private DepartmentServiceImpl departmentService;

    @FXML
    public void initialize() {
        idField.setDisable(true);
        this.departmentService = new DepartmentServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentTable.setItems(departmentsList);
        loadDummyData();
    }


    private void loadDummyData() {
        departmentsList.clear();
        departmentsList.addAll(departmentService.getAllDepartment());
    }

    @FXML
    private void addDepartment() {
        String department = departmentField.getText();
        this.departmentService.saveDepartment(new Department(department));
        this.loadDummyData();
        clearFields();
    }

    @FXML
    private void deleteDepartment() {
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            this.departmentService.delete(selectedDepartment.getId());
            loadDummyData();
            clearFields();
        }
    }

    @FXML
    private void updateDepartment() {
        Department selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if(selectedDepartment == null){
            AlertUtil.alert("Please select a Department from the table to update.", "ERROR");
            clearFields();
            return;
        }
        if (selectedDepartment != null) {
            selectedDepartment.setDepartment(departmentField.getText());
            this.departmentService.update(selectedDepartment);
            departmentTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Department department = departmentTable.getSelectionModel().getSelectedItem();
        if (department != null) {
            idField.setText(String.valueOf(department.getId()));
            departmentField.setText(department.getDepartment());
        }
    }

    @FXML
    private void handleSearchAction() {
        List<Department> resultDepartment= this.departmentService.searchDepartmentByKeyword(searchField.getText());
        departmentsList.clear();
        departmentsList.addAll(resultDepartment);
    }

    private void clearFields() {
        idField.clear();
       departmentField.clear();
    }

    @FXML
    private void clearForm() {
        clearFields();
    }

}
