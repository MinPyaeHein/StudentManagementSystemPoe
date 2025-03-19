package Controller;

import Constant.Constants;
import Dto.DegreeDto;
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
        try {
        DegreeDto degreeDto=new DegreeDto();
            degreeDto.setDegree(degreeField.getText());
            this.degreeService.saveDegree(degreeDto);
            this.loadDummyData();
            clearFields();
        }catch(RuntimeException e){
            AlertUtil.alert(e.getMessage(), Constants.Alerts.ERROR);
        }
    }

    @FXML
    private void deleteDegree() {
        DegreeDto degreeDto=new DegreeDto();
        degreeDto.setId(idField.getText());
            this.degreeService.delete(degreeDto);
            loadDummyData();
            clearFields();
        }


    @FXML
    private void updateDegree() {
    DegreeDto degreeDto=new DegreeDto();
    degreeDto.setId(idField.getText());
    degreeDto.setDegree(degreeField.getText());
    this.degreeService.update(degreeDto);
    degreeTable.refresh();
    this.loadDummyData();
    clearFields();
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
