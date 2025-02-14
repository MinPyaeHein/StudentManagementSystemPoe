package Service;


import Dto.TeacherDto;
import Model.Teacher;

import java.util.List;

public interface TeacherService {
    public void update(Teacher teacher);
    public List<Teacher> getAllTeacher();
    public Teacher getTeacherById(int teacherId);
    public void saveTeacher(TeacherDto teacherDto);
    public void delete(int id);
    public List<Teacher> searchTeacherByKeyword(String keyword);
    public Teacher getTeacherByEmail(String email);
}
