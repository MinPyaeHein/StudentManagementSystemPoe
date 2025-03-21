package Controller;

import Constant.Constants;
import Dto.SemesterDto;
import Model.Semester;
import Model.SemesterName;
import Model.SemesterStatus;
import Service.impl.SemesterServiceImpl;
import Utils.AlertUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class SemesterController {

    @FXML
    private TextField idField;
    @FXML
    private ChoiceBox<SemesterName> nameChoiceBox;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ChoiceBox<SemesterStatus> statusChoiceBox;
    @FXML
    private TableView<Semester> semesterTable;
    @FXML
    private TableColumn<Semester, Integer> idColumn;
    @FXML
    private TableColumn<Semester, String> nameColumn;
    @FXML
    private TableColumn<Semester, String> startDateColumn;
    @FXML
    private TableColumn<Semester, String> endDateColumn;
    @FXML
    private TableColumn<Semester, String> statusColumn;

    private ObservableList<Semester> semesterList = FXCollections.observableArrayList();
    private SemesterServiceImpl semesterService;

    @FXML
    public void initialize() {
        idField.setDisable(true);
        semesterService = new SemesterServiceImpl();
        nameChoiceBox.setItems(FXCollections.observableArrayList(SemesterName.values()));
        statusChoiceBox.setItems(FXCollections.observableArrayList(SemesterStatus.values()));
        semesterTable.setItems(semesterList);
        semesterTableSetUp();
        loadSemesterData();
    }

    private void semesterTableSetUp(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("start_Date"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("end_Date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void loadSemesterData() {
        semesterList.clear();
        List<Semester> semesters = semesterService.getAllSemester();
        semesterList.addAll(semesters);
    }

    @FXML
    private void addSemester() {
        try {
            SemesterDto semesterDto = new SemesterDto();
            semesterDto.setName((this.nameChoiceBox.getSelectionModel().getSelectedItem()));
            semesterDto.setStart_Date(this.startDatePicker.getValue());
            semesterDto.setEnd_Date(this.endDatePicker.getValue());
            semesterDto.setStatus((this.statusChoiceBox.getSelectionModel().getSelectedItem()));
            this.semesterService.saveSemester(semesterDto);
            loadSemesterData();
            clearForm();
        }catch(RuntimeException e){
            AlertUtil.alert(e.getMessage(), Constants.Alerts.ERROR);
        }
    }

    @FXML
    private void updateSemester() {
        try{
        if(AlertUtil.getSelectedItem(semesterTable, "course") !=null) {
            SemesterDto semesterDto = new SemesterDto();
            semesterDto.setId(idField.getText());
            semesterDto.setName((this.nameChoiceBox.getSelectionModel().getSelectedItem()));
            semesterDto.setStart_Date(this.startDatePicker.getValue());
            semesterDto.setEnd_Date(this.endDatePicker.getValue());
            semesterDto.setStatus((this.statusChoiceBox.getSelectionModel().getSelectedItem()));
            this.semesterService.update(semesterDto);
            AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS, Constants.Alerts.INFO);
            semesterTable.refresh();
            loadSemesterData();
        }
        }catch (RuntimeException e) {
            AlertUtil.alert(e.getMessage(), Constants.Alerts.ERROR);
        }
        clearForm();
}

    @FXML
    private void clearForm() {
        idField.clear();
        nameChoiceBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        statusChoiceBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Semester selected = semesterTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            idField.setText(String.valueOf(selected.getId()));
            nameChoiceBox.setValue(selected.getName());
            startDatePicker.setValue(selected.getStart_Date());
            endDatePicker.setValue(selected.getEnd_Date());
            statusChoiceBox.setValue((selected.getStatus()));
        }
    }
}