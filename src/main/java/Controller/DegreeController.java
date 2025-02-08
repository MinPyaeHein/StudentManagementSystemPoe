package Controller;

import Model.Degree;
import Service.impl.DegreeServiceImpl;
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

public class DegreeController {

    @FXML
    private TableView<Degree> degreeTable;
    @FXML
    private TableColumn<Degree, Integer> idColumn;
    @FXML
    private TableColumn<Degree, String> degreeColumn;
    @FXML
    private TextField searchField;
    @FXML
    private TextField idField;
    @FXML
    private TextField degreeField;

    private ObservableList<Degree> degreeList = FXCollections.observableArrayList();
    private DegreeServiceImpl degreeService;

    @FXML
    public void initialize() {
        idField.setDisable(true);
        this.degreeService = new DegreeServiceImpl();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
        degreeTable.setItems(degreeList);
        loadDummyData();
    }


    private void loadDummyData() {
        degreeList.clear();
        degreeList.addAll(degreeService.getAllDegree());
    }

    @FXML
    private void addDegree() {
        String degree = degreeField.getText();
        this.degreeService.saveDegree(new Degree(degree));
        this.loadDummyData();
        clearFields();
    }

    @FXML
    private void deleteDegree() {
        Degree selectedDegree = degreeTable.getSelectionModel().getSelectedItem();
        if (selectedDegree != null) {
            this.degreeService.delete(selectedDegree.getId());
            loadDummyData();
            clearFields();
        }
    }

    @FXML
    private void updateDegree() {
        Degree selectedDegree = degreeTable.getSelectionModel().getSelectedItem();
        if(selectedDegree == null){
            AlertUtil.alert("Please select a Degree from the table to update.", "ERROR");
            clearFields();
            return;
        }
        if (selectedDegree != null) {
            selectedDegree.setDegree(degreeField.getText());
            this.degreeService.update(selectedDegree);
            degreeTable.refresh();
            clearFields();
        }
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Degree degree = degreeTable.getSelectionModel().getSelectedItem();
        if (degree != null) {
            idField.setText(String.valueOf(degree.getId()));
            degreeField.setText(degree.getDegree());
        }
    }

    @FXML
    private void handleSearchAction() {
        List<Degree> resultDegree= this.degreeService.searchDegreeByKeyword(searchField.getText());
        degreeList.clear();
        degreeList.addAll(resultDegree);
    }

    private void clearFields() {
        idField.clear();
        degreeField.clear();
    }

    @FXML
    private void clearForm() {
        clearFields();
    }


}
