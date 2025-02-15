package Service;

import Dto.DegreeDto;
import Model.Degree;

import java.util.List;


public interface DegreeService {
    public void update(DegreeDto degreeDto);
    public List<Degree> getAllDegree();
    public Degree getDegreeById(int degreeId);
    public void saveDegree(DegreeDto degreeDto);
    public Degree findDegreeByName(String name);
    public void delete(DegreeDto degreeDto);
    public List<Degree> searchDegreeByKeyword(String keyword);
}
