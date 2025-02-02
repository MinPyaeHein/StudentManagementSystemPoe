package Service.impl;


import Dao.FacultyDao;
import Dao.impl.FacultyDaoImpl;
import Model.Faculty;
import Service.FacultyService;

import java.util.List;

public class FacultyServiceImpl implements FacultyService {
    private FacultyDao facultyDao;
    public FacultyServiceImpl() {
        this.facultyDao= new FacultyDaoImpl();
    }
    @Override
    public List<Faculty> getAllFaculty(){
        return facultyDao.selectAll();
    }

    @Override
    public Faculty findFacultyByName(String name) {
        return facultyDao.findFacultyByName(name);
    }
}