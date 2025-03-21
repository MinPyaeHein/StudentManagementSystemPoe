package Service.impl;


import Constant.Constants;
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
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private StudentDaoImpl studentDao;
    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public void update(StudentDto studentDto) {
        try {
        Student student= StudentMapper.toEntity(studentDto);
         ValidateUtail.validate(studentDto);
         ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(), Constants.ImagePaths.STUDENT_FOLDER);
         studentDao.update(student, Constants.FieldConstraints.ID);
     } catch (IOException e) {
            throw new RuntimeException(e);
     }
    }

    @Override
    public List<Student> getAllStudent(){
        return studentDao.selectAll();
    }

    @Override
    public Student getStudentById(int id) {
        try {
            Student studentRow = studentDao.selectById(new Student(id));
            if (studentRow != null) {
                return studentRow;
            }
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException(ex.getMessage());
        }
        return null;
    }

    @Override
    public void saveStudent(StudentDto studentDto) {
        try{
        Student student=StudentMapper.toEntity(studentDto);
            ValidateUtail.validate(studentDto);
            validateExistStudent(student);
            ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(),Constants.ImagePaths.STUDENT_FOLDER);
            this.studentDao.insert(student);
        }catch(InvalidDataFormatException e){
            throw new InvalidDataFormatException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(StudentDto studentDto) {
        Student student = StudentMapper.idToEntity(studentDto);
        student = this.studentDao.selectById(student);
        try {
            this.studentDao.delete(student);
            ImgUtil.deleteImageWithId(student.getId(),studentDto.getImageFile(),Constants.ImagePaths.STUDENT_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateExistStudent(Student student) {
        Student duplicateStudent = this.studentDao.findStudentByEmail(student.getEmail());
        if (duplicateStudent != null) {
            throw new InvalidDataFormatException(Constants.Alerts.DUPLICATE_RECORD + student.getEmail());
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