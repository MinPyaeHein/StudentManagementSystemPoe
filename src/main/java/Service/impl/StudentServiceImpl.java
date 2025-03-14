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
         ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(), Constants.ImagePaths.STUDENT_FOLDER);
         studentDao.update(student, Constants.FieldConstraints.ID);
         AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS,Constants.Alerts.INFO);
     }catch(InvalidDataFormatException exception){
         AlertUtil.alert(exception.getMessage(),Constants.Alerts.ERROR);
     } catch (IOException e) {
         AlertUtil.alert(e.getMessage(),Constants.Alerts.ERROR);
     }
    }

    @Override
    public List<Student> getAllStudent(){
        return studentDao.selectAll();
    }

    @Override
    public Student getStudentById(int studentId) {
        Student student = this.studentDao.selectById(new Student(studentId));
        return student;
    }


    @Override
    public void saveStudent(StudentDto studentDto) {
        Student student=StudentMapper.toEntity(studentDto);
        try{
            ValidateUtail.validate(student);
            validateExistStudent(student);
            ImgUtil.saveImageWithId(student.getId(),studentDto.getImageFile(),Constants.ImagePaths.STUDENT_FOLDER);
            this.studentDao.insert(student);
            AlertUtil.alert(Constants.Alerts.SAVE_SUCCESS,Constants.Alerts.INFO);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),Constants.Alerts.ERROR);
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(),Constants.Alerts.ERROR);
        }
    }
    @Override
    public void delete(StudentDto studentDto) {
        Student student = StudentMapper.idToEntity(studentDto);
        student = this.studentDao.selectById( student);
        try {
            if(student!=null&& AlertUtil.confirmationDialog(Constants.Alerts.DELETE_CONFIRM_TITLE,Constants.Alerts.DELETE_CONFIRM_MESSAGE+"\n"+student.getEmail()+"\n"+student.getName())){
                this.studentDao.delete(student);
            }
            ImgUtil.deleteImageWithId(student.getId(),studentDto.getImageFile(),Constants.ImagePaths.STUDENT_FOLDER);
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(),Constants.Alerts.ERROR);
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