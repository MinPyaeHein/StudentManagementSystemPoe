package Service;

import Dto.StudentDto;
import Model.Student;

import java.util.List;

public interface StudentService {
    public void update(StudentDto studentDto);
    public List<Student> getAllStudent();
    public Student getStudentById(int id);
    public void saveStudent(StudentDto studentDto);
    public void delete(StudentDto id);
    public List<Student> searchStudentByKeyword(String keyword);
    public Student getStudentByEmail(String email);


}
