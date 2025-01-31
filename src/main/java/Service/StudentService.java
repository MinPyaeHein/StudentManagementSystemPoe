package Service;


import Dao.impl.StudentDaoImpl;
import Model.Student;
import Exception.ExceptionHandler;
import Exception.CannotProcessException;
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
        try{
            validateFields(student);
            validateExistStudent(student);
            this.studentDao.insert(student);
        }catch(CannotProcessException e){
            ExceptionHandler.showError(e.getMessage());
        }
    }
    public void delete(int id) {
        Student book=new Student(id);
        book=this.studentDao.selectById(book);
        if(book!=null) {
            this.studentDao.delete(book);
            return true;
        }else {
            return false;
        }
    }

    private void validateFields(Student student) {
        if (student.getName().isEmpty() || student.getEmail().isEmpty() || student.getAddress().isEmpty()) {
            throw new CannotProcessException("Please enter all required information!");
        }
    }
    private void validateExistStudent(Student student) {
        Student duplicateStudent = this.studentDao.findStudentByEmail(student.getEmail());
        if (duplicateStudent != null) {
            throw new CannotProcessException("Duplicate student found!!! " + student.getEmail());
        }
    }

}