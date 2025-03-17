package Service.impl;

import Constant.Constants;
import Dao.impl.DegreeDaoImpl;
import Dto.DegreeDto;
import Mapper.DegreeMapper;
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
    public void update(DegreeDto degreeDto) {

        Degree degree = DegreeMapper.toEntity(degreeDto);
        try {
            ValidateUtail.validate(degreeDto);
            degreeDao.update(degree, "id");
            AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS,Constants.Alerts.INFO);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),Constants.Alerts.ERROR);
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
    public void saveDegree(DegreeDto degreeDto) {
        Degree degree= DegreeMapper.toEntity(degreeDto);
        try{
            ValidateUtail.validate(degreeDto);
            validateExistDepartment(degree);
            this.degreeDao.insert(degree);
            AlertUtil.alert(Constants.Alerts.SAVE_SUCCESS,Constants.Alerts.INFO);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),Constants.Alerts.ERROR);
        }
    }

    @Override
    public Degree findDegreeByName(String name) {
        return degreeDao.findDegreeByName(name);
    }

    @Override
    public void delete(DegreeDto degreeDto) {
        Degree degree = DegreeMapper.idToEntity(degreeDto);
        degree = this.degreeDao.selectById(degree);
        if(degree!=null&& AlertUtil.confirmationDialog(Constants.Alerts.DELETE_CONFIRM_TITLE,Constants.Alerts.DELETE_CONFIRM_MESSAGE+"\n"+degree.getDegree())){
            this.degreeDao.delete(degree);
        }
    }

    @Override
    public List<Degree> searchDegreeByKeyword(String keyword) {
        return this.degreeDao.findDegreeByKeyword(keyword);
    }

    private void validateExistDepartment(Degree degree) {
        Degree duplicateDegree= this.degreeDao.findDegreeByName(degree.getDegree());
        if (duplicateDegree != null) {
            throw new InvalidDataFormatException("Duplicate degree found!!! " + degree.getDegree());
        }
    }
}