package Service.impl;


import Dao.impl.StudentDaoImpl;
import Model.Student;
import Service.StudentService;
import Utils.AlertUtil;
import Utils.ValidateUtail;
import Exception.InvalidDataFormatException;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDaoImpl studentDao;
    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public void update(Student student) {
     try {
         ValidateUtail.validate(student);
         studentDao.update(student, "id");
         AlertUtil.alert("Successfully updated","INFORMATION");
     }catch(InvalidDataFormatException exception){
         AlertUtil.alert(exception.getMessage(),"ERROR");
     }
    }
    @Override
    public List<Student> getAllStudent(){
        return studentDao.selectAll();
    }
    @Override
    public Student getStudentById(int studentId) {
        return this.studentDao.selectById(new Student(studentId));
    }

    @Override
    public void saveStudent(Student student) {
        try{
            ValidateUtail.validate(student);
            validateExistStudent(student);
            this.studentDao.insert(student);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }
    @Override
    public void delete(int id) {
        Student student = new Student(id);
        System.out.println("Student OBJECT:" + student);
        student = this.studentDao.selectById( student);
        if(student!=null&& AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete student?\n"+student.getName())){
            this.studentDao.delete(student);
        }
    }

    private void validateExistStudent(Student student) {
        System.out.println("Arrived to validate duplicate:" + student);
        Student duplicateStudent = this.studentDao.findStudentByEmail(student.getEmail());
        if (duplicateStudent != null) {
            throw new InvalidDataFormatException("Duplicate student found!!! " + student.getEmail());
        }
    }
    @Override
    public Student getStudentByEmail(String email) {
        return this.studentDao.findStudentByEmail(email);
    }
    @Override
    public List<Student> searchStudentByKeyword(String keyword) {
        return studentDao.findStudentByKeyword(keyword);
    }

}