package Service.impl;

import Dao.impl.DegreeDaoImpl;
import Model.Degree;
import Service.DegreeService;
import Utils.AlertUtil;
import Utils.ValidateUtail;
import Exception.*;
import java.util.List;

public class DegreeServiceImpl implements DegreeService {
    private DegreeDaoImpl degreeDao;

    public DegreeServiceImpl() {
        this.degreeDao = new DegreeDaoImpl();
    }

    @Override
    public void update(Degree degree) {
        try {

            ValidateUtail.validate(degree);
            degreeDao.update(degree, "id");
            AlertUtil.alert("Successfully updated","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    @Override
    public List<Degree> getAllDegree() {
        return degreeDao.selectAll();
    }

    @Override
    public Degree getDegreeById(int degreeId) {
        return this.degreeDao.selectById(new Degree(degreeId));

    }

    @Override
    public void saveDegree(Degree degree) {
        try{
            ValidateUtail.validate(degree);
            validateExistDepartment(degree);
            this.degreeDao.insert(degree);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(InvalidDataFormatException exception){
            exception.printStackTrace();
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    @Override
    public Degree findDegreeByName(String name) {
        return degreeDao.findDegreeByName(name);
    }

    @Override
    public void delete(int id) {
        Degree degree = new Degree(id);
        degree = this.degreeDao.selectById(degree);
        if(degree!=null&& AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete this degree?\n"+degree.getDegree())){
            this.degreeDao.delete(degree);
        }
    }

    @Override
    public List<Degree> searchDegreeByKeyword(String keyword) {
        return List.of();
    }

    private void validateExistDepartment(Degree degree) {
        Degree duplicateDegree= this.degreeDao.findDegreeByName(degree.getDegree());
        if (duplicateDegree != null) {
            throw new InvalidDataFormatException("Duplicate dep found!!! " + degree.getDegree());
        }
    }
}