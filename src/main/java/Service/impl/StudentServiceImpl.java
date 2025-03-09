package Service.impl;


import Dao.impl.StudentDaoImpl;
import Dto.StudentDto;
import Mapper.StudentMapper;
import Model.Student;
import Service.StudentService;
import Utils.AlertUtil;
import Utils.ImgUtil;
import Utils.UtilConstants;
import Utils.ValidateUtail;
import Exception.InvalidDataFormatException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StudentServiceImpl implements StudentService {
    private StudentDaoImpl studentDao;
    public static int studentId;
    public static String studentName;
    public StudentServiceImpl() {
        this.studentDao = new StudentDaoImpl();
    }

    @Override
    public void update(StudentDto studentDto) {
        Student student= StudentMapper.toEntity(studentDto);
     try {
         ValidateUtail.validate(student);
         ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(),UtilConstants.STUDENT_IMAGE_PATH);
         studentDao.update(student, UtilConstants.ID_FIELD);
         AlertUtil.alert(UtilConstants.UPDATE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
     }catch(InvalidDataFormatException exception){
         AlertUtil.alert(exception.getMessage(),UtilConstants.ERROR_ALERT);
     } catch (IOException e) {
         AlertUtil.alert(e.getMessage(),UtilConstants.ERROR_ALERT);
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
                studentId = studentRow.getId();
                studentName = studentRow.getName();
                return studentRow;
            }
        } catch (IndexOutOfBoundsException ex) {
            AlertUtil.alert("Student id does not Exist", UtilConstants.ERROR_ALERT);
        }
        return null;
    }

    @Override
    public void saveStudent(StudentDto studentDto) {
        Student student=StudentMapper.toEntity(studentDto);
        try{
            ValidateUtail.validate(student);
            validateExistStudent(student);
            this.studentDao.insert(student);
            ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(),UtilConstants.STUDENT_IMAGE_PATH);
            AlertUtil.alert(UtilConstants.SAVE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),UtilConstants.ERROR_ALERT);
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(),UtilConstants.ERROR_ALERT);
        }
    }

    @Override
    public void delete(StudentDto studentDto) {
        Student student = StudentMapper.idToEntity(studentDto);
        student = this.studentDao.selectById( student);
        try {
            if(student!=null&& AlertUtil.confirmationDialog(UtilConstants.DELETE_CONFIRM_TITLE,UtilConstants.DELETE_CONFIRM_MESSAGE+"\n"+student.getEmail()+"\n"+student.getName())){
                this.studentDao.delete(student);
            }
            ImgUtil.deleteImageWithId(student.getId(),studentDto.getImageFile(),UtilConstants.STUDENT_IMAGE_PATH);
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(),UtilConstants.ERROR_ALERT);
        }

    }

    private void validateExistStudent(Student student) {
        Student duplicateStudent = this.studentDao.findStudentByEmail(student.getEmail());
        if (duplicateStudent != null) {
            throw new InvalidDataFormatException(UtilConstants.DUPLICATE_RECORD_ERROR + student.getEmail());
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