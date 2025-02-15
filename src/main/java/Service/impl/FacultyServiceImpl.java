package Service.impl;

import Dao.impl.FacultyDaoImpl;
import Dto.FacultyDto;
import Mapper.FacultyMapper;
import Model.Faculty;
import Service.FacultyService;
import Utils.AlertUtil;
import Utils.UtilConstants;
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
            AlertUtil.alert(UtilConstants.SAVE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),UtilConstants.ERROR_ALERT);
        }
    }

    @Override
    public void update(FacultyDto facultyDto) {
        Faculty faculty= FacultyMapper.toEntity(facultyDto);
        try {
            ValidateUtail.validate(faculty);
            facultyDao.update(faculty, UtilConstants.ID_FIELD);
            AlertUtil.alert(UtilConstants.UPDATE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),UtilConstants.ERROR_ALERT);
        }
    }

    @Override
    public void delete(FacultyDto facultyDto){
        Faculty faculty = FacultyMapper.idToEntity(facultyDto);
        faculty = this.facultyDao.selectById(faculty);
        if(faculty != null &&
                AlertUtil.confirmationDialog(UtilConstants.DELETE_CONFIRM_TITLE,UtilConstants.DELETE_CONFIRM_MESSAGE+"\n"+faculty.getEmail())){
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
            throw new InvalidDataFormatException(UtilConstants.DUPLICATE_RECORD_ERROR + faculty.getEmail());
        }
    }
    @Override
    public List<Faculty> searchFacultyByKeyword(String keyword) {
        return facultyDao.findFacultyByKeyword(keyword);
    }



}