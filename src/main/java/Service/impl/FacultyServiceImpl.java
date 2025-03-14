package Service.impl;

import Constant.Constants;
import Dao.impl.FacultyDaoImpl;
import Dto.FacultyDto;
import Mapper.FacultyMapper;
import Model.Faculty;
import Service.FacultyService;
import Utils.AlertUtil;
import Utils.ValidateUtail;
import Exception.InvalidDataFormatException;


import java.util.List;

public class FacultyServiceImpl  implements FacultyService {
    private FacultyDaoImpl facultyDao;
    public FacultyServiceImpl() {
        this.facultyDao= new FacultyDaoImpl();
    }

    @Override
    public void saveFaculty(FacultyDto facultyDto) {
        Faculty faculty= FacultyMapper.toEntity(facultyDto);
        try{
            ValidateUtail.validate(faculty);
            validateExistFaculty(faculty);
            this.facultyDao.insert(faculty);
            AlertUtil.alert(Constants.Alerts.SAVE_SUCCESS,Constants.Alerts.INFO);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),Constants.Alerts.ERROR);
        }
    }

    @Override
    public void update(FacultyDto facultyDto) {
        Faculty faculty= FacultyMapper.toEntity(facultyDto);
        try {
            ValidateUtail.validate(faculty);
            facultyDao.update(faculty, Constants.FieldConstraints.ID);
            AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS,Constants.Alerts.INFO);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),Constants.Alerts.ERROR);
        }
    }

    @Override
    public void delete(FacultyDto facultyDto){
        Faculty faculty = FacultyMapper.idToEntity(facultyDto);
        faculty = this.facultyDao.selectById(faculty);
        if(faculty != null &&
                AlertUtil.confirmationDialog(Constants.Alerts.DELETE_CONFIRM_TITLE,Constants.Alerts.DELETE_CONFIRM_MESSAGE+"\n"+faculty.getEmail())){
               this.facultyDao.delete(faculty);
        }
    }

    @Override
    public List<Faculty> getAllFaculty(){
        return facultyDao.selectAll();
    }

    @Override
    public Faculty findFacultyByName(String name) {
        return facultyDao.findFacultyByName(name);
    }

    private void validateExistFaculty(Faculty faculty) {
        Faculty duplicateFaculty = this.facultyDao.findFacultyByEmail(faculty.getEmail());
        if (duplicateFaculty != null) {
            throw new InvalidDataFormatException(Constants.Alerts.DUPLICATE_RECORD + faculty.getEmail());
        }
    }
    @Override
    public List<Faculty> searchFacultyByKeyword(String keyword) {
        return facultyDao.findFacultyByKeyword(keyword);
    }



}