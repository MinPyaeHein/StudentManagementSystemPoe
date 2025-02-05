package Service;

import Model.Teacher;

import java.util.List;

public interface TeacherService {
    public void update(Teacher teacher);
    public List<Teacher> getAllTeacher();
    public Teacher getTeacherById(int teacherId);
    public void saveTeacher(Teacher teacher);
    public void delete(int id);
    public List<Teacher> searchTeacherByKeyword(String keyword);
}
