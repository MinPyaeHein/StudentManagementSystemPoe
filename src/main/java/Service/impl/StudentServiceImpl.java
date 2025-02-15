package Service.impl;


import Dao.impl.StudentDaoImpl;
import Dto.StudentDto;
import Mapper.StudentMapper;
import Model.Student;
import Service.StudentService;
import Utils.AlertUtil;
import Utils.ImgUtil;
import Utils.ValidateUtail;
import Exception.InvalidDataFormatException;

import java.io.IOException;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDaoImpl studentDao;
    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public void update(StudentDto studentDto) {
        Student student= StudentMapper.toEntity(studentDto);
     try {
         ValidateUtail.validate(student);
         ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(),"student_images/");
         studentDao.update(student, "id");
         AlertUtil.alert("Successfully updated","INFORMATION");
     }catch(InvalidDataFormatException exception){
         AlertUtil.alert(exception.getMessage(),"ERROR");
     } catch (IOException e) {
         AlertUtil.alert(e.getMessage(),"ERROR");
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
    public void saveStudent(StudentDto studentDto) {
        Student student=StudentMapper.toEntity(studentDto);
        try{
            ValidateUtail.validate(student);
            validateExistStudent(student);
            ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(),"student_images/");
            this.studentDao.insert(student);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(),"ERROR");
        }
    }
    @Override
    public void delete(StudentDto studentDto) {
        Student student = StudentMapper.idToEntity(studentDto);
        student = this.studentDao.selectById( student);
        try {
            if(student!=null&& AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete student?\n"+student.getEmail()+"\n"+student.getName())){
                this.studentDao.delete(student);
            }
            ImgUtil.deleteImageWithId(student.getId(),studentDto.getImageFile(),"student_images/");
        } catch (IOException e) {
            throw new RuntimeException(e);
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