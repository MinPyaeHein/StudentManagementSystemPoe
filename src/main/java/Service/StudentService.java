package Service;


import Dao.impl.StudentDaoImpl;
import Model.Student;
import Exception.ExceptionHandler;
import Exception.CannotProcessException;
import Model.Teacher;
import Utils.AlertUtil;

import java.util.List;

public class StudentService {
    private StudentDaoImpl studentDao;
    public StudentService() {
        this.studentDao = new StudentDaoImpl();
    }
    public void update(Student student) {
     try {
         validateFields(student);
         studentDao.update(student, "id");
         AlertUtil.alert("Successfully updated","INFORMATION");
     }catch(CannotProcessException exception){
         AlertUtil.alert(exception.getMessage(),"ERROR");
     }
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
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(CannotProcessException e){
            ExceptionHandler.showError(e.getMessage());
        }
    }
    public void delete(int id) {
        Student student=new Student(id);
        student=this.studentDao.selectById(student);
        if(student!=null &&
                AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete teacher?\n"+student.getEmail())){
            this.studentDao.delete(student);
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

    public List<Student> searchStudentByName(String name){

        return studentDao.findStudentByName(name.toLowerCase());
    }

}