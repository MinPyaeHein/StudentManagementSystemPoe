package Dao;

import Model.Faculty;

import java.util.List;

public interface FacultyDao extends GeneralDao<Faculty> {
    public Faculty findFacultyByEmail(String email);
    public List<Faculty> findFacultyByKeyword(String keyword);
    public Faculty findFacultyByName(String name);
}
