package Service;


import Dao.impl.StudentDaoImpl;
import Model.Student;

import java.util.List;

public class StudentService {
    private StudentDaoImpl studentDao;
    public StudentService() {
        this.studentDao = new StudentDaoImpl();
    }
    public void update(Student student) {
        studentDao.update(student,"id");
    }
    public List<Student> getAllStudent(){
        return studentDao.selectAll();
    }
    public Student getStudentById(int studentId) {
        return this.studentDao.selectById(new Student(studentId));
    }
    public void saveStudent(Student student) {
        this.studentDao.insert(student);
    }
    public boolean delete(int id) {
        Student student=new Student(id);
        student=this.studentDao.selectById(student);
        if(student!=null) {
            this.studentDao.delete(student);
            return true;
        }else {
            return false;
        }
    }
}