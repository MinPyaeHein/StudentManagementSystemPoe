package Service.impl;

import Dao.impl.FacultyDaoImpl;
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
    public void saveFaculty(Faculty faculty) {
        try{
            ValidateUtail.validate(faculty);
            validateExistFaculty(faculty);
            this.facultyDao.insert(faculty);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    @Override
    public void update(Faculty faculty) {
        try {
            ValidateUtail.validate(faculty);
            facultyDao.update(faculty, "id");
            AlertUtil.alert("Successfully updated","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    @Override
    public void delete(int id){
        Faculty faculty = new Faculty(id);
        faculty = this.facultyDao.selectById(faculty);
        if(faculty != null &&
                AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete faculty?\n"+faculty.getEmail())){
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
            throw new InvalidDataFormatException("Duplicate faculty found!!! " + faculty.getEmail());
        }
    }
    @Override
    public List<Faculty> searchFacultyByKeyword(String keyword) {
        return facultyDao.findFacultyByKeyword(keyword);
    }



}