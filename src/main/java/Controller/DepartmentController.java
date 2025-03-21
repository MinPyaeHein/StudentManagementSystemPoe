package Controller;


import Constant.Constants;
import Dto.DepartmentDto;
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
        try{
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartment(departmentField.getText());
        this.departmentService.saveDepartment(departmentDto);
        this.loadDummyData();
        clearFields();
    }catch(RuntimeException e){
        AlertUtil.alert(e.getMessage(), Constants.Alerts.ERROR);
    }
}

    @FXML
    private void deleteDepartment() {
        if(AlertUtil.getSelectedItem(departmentTable, "department") !=null) {
            DepartmentDto departmentDto = new DepartmentDto();
            departmentDto.setId(idField.getText());
            if(AlertUtil.confirmationDialog(Constants.Alerts.DELETE_CONFIRM_TITLE,Constants.Alerts.DELETE_CONFIRM_MESSAGE)) {
                this.departmentService.delete(departmentDto);
            }
            this.loadDummyData();
            clearFields();
        }

    }

    @FXML
    private void updateDepartment() {
        try {
            if (AlertUtil.getSelectedItem(departmentTable, "department") != null) {
                DepartmentDto departmentDto = new DepartmentDto();
                departmentDto.setId(idField.getText());
                departmentDto.setDepartment(departmentField.getText());
                this.departmentService.update(departmentDto);
                AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS, Constants.Alerts.INFO);
                departmentTable.refresh();
                this.loadDummyData();
            }
        }catch (RuntimeException e) {
            AlertUtil.alert(e.getMessage(), Constants.Alerts.ERROR);
        }
        clearFields();
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
