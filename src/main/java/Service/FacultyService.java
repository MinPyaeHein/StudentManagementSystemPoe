package Service;

import Model.Faculty;

import java.util.List;

public interface FacultyService {
    public void saveFaculty(Faculty faculty);
    public void update(Faculty faculty);
    public void delete(int id);
    public List<Faculty> getAllFaculty();
    public Faculty findFacultyByName(String name);
    public List<Faculty> searchFacultyByKeyword(String keyword);

}
