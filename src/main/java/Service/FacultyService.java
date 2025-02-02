package Service;

import Model.Faculty;

import java.util.List;

public interface FacultyService {
    public List<Faculty> getAllFaculty();
    public Faculty findFacultyByName(String name);
}
