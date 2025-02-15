package Service.impl;

import Dao.impl.TeacherDaoImpl;
import Dto.TeacherDto;
import Mapper.TeacherMapper;
import Model.Faculty;
import Model.Teacher;
import Exception .*;
import Service.TeacherService;
import Utils.AlertUtil;
import Utils.ImgUtil;
import Utils.ValidateUtail;

import java.io.IOException;
import java.util.List;

public class TeacherServiceImpl implements TeacherService {
    private TeacherDaoImpl teacherDao;

    public TeacherServiceImpl(){
        this.teacherDao = new TeacherDaoImpl();
    }

    @Override
    public void update(TeacherDto teacherDto){
        Teacher teacher = TeacherMapper.toEntity(teacherDto);
        try{
            ValidateUtail.validate(teacher);
            teacherDao.update(teacher,"id");
            ImgUtil.saveImageWithId(teacher.getId(),teacherDto.getImageFile(),"teachers_images/");
            AlertUtil.alert("Successfully updated","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        } catch (IOException e) {
            AlertUtil.alert(e.getMessage(),"ERROR");
        }
    }
    @Override
    public List<Teacher> getAllTeacher(){
        return teacherDao.selectAll();
    }
    @Override
    public Teacher getTeacherById(int teacherId) {
        return this.teacherDao.selectById(new Teacher(teacherId));
    }
    @Override
    public void saveTeacher(TeacherDto teacherDto){
        Teacher teacher = TeacherMapper.toEntity(teacherDto);
        try{
            ValidateUtail.validate(teacher);
            checkDuplicateTeacher(teacher);
            ImgUtil.saveImageWithId(teacher.getId(), teacherDto.getImageFile(), "teachers_images/");
            this.teacherDao.insert(teacher);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(UserAlreadyExist exception){
            AlertUtil.alert(exception.getMessage() + teacher.getEmail(),"ERROR");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }catch (IOException e) {
            AlertUtil.alert(e.getMessage(),"ERROR");
        }
    }
    @Override
    public void delete(TeacherDto teacherDto){
        Teacher teacher = TeacherMapper.idToEntity(teacherDto);
        teacher =  this.teacherDao.selectById(teacher);
        try {
            if(teacher != null &&
                    AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete teacher?\n"+teacher.getEmail())){
                this.teacherDao.delete(teacher);
            }
            ImgUtil.deleteImageWithId(teacher.getId(),teacherDto.getImageFile(),"teachers_images/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void checkDuplicateTeacher(Teacher teacher){
        Teacher selectedTeacher=this.teacherDao.findTeacherByEmail(teacher.getEmail());
        if(selectedTeacher!=null){
            throw new UserAlreadyExist("Duplicate teacher found!!! " + teacher.getEmail());
        }
    }
    @Override
    public List<Teacher> searchTeacherByKeyword(String keyword) {
        return teacherDao.findTeacherByKeyword(keyword);
    }
    @Override
    public Teacher getTeacherByEmail(String email){
        return this.teacherDao.findTeacherByEmail(email);
    }
    public Teacher findTeacherByName(String name) {
        return teacherDao.findTeacherByName(name);
    }




}
