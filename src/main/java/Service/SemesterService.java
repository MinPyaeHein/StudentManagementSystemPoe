package Service;


import Dto.SemesterDto;
import Model.Semester;
import Model.SemesterName;

import java.util.List;

public interface SemesterService {
    public void saveSemester(SemesterDto semesterDto);
    public void update(SemesterDto semesterDto);
    public List<Semester> getAllSemester();
}
