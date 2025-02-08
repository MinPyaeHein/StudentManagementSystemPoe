package Service;

import Model.Student;

import java.util.List;

public interface StudentService {
    public void update(Student student);
    public List<Student> getAllStudent();
    public Student getStudentById(int studentId);
    public void saveStudent(Student student);
    public void delete(int id);
    public List<Student> searchStudentByKeyword(String keyword);
    public Student getStudentByEmail(String email);


}
