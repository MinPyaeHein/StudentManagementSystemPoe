package Service;

import Dto.FacultyDto;
import Model.Faculty;

import java.util.List;

public interface FacultyService {
    public void saveFaculty(FacultyDto facultyDto);
    public void update(FacultyDto facultyDto);
    public void delete(FacultyDto facultyDto);
    public List<Faculty> getAllFaculty();
    public Faculty findFacultyByName(String name);
    public List<Faculty> searchFacultyByKeyword(String keyword);

}
