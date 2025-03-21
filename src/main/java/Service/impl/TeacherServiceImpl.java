package Service.impl;

import Constant.Constants;
import Dao.impl.TeacherDaoImpl;
import Dto.TeacherDto;
import Mapper.TeacherMapper;
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
        try {
        Teacher teacher = TeacherMapper.toEntity(teacherDto);
            teacherDao.update(teacher,"id");
            ImgUtil.saveImageWithId(teacher.getId(), teacherDto.getImageFile(), Constants.ImagePaths.TEACHER_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        try{
        Teacher teacher = TeacherMapper.toEntity(teacherDto);
            ValidateUtail.validate(teacherDto);
            checkDuplicateTeacher(teacher);
            ImgUtil.saveImageWithId(teacher.getId(), teacherDto.getImageFile(), Constants.ImagePaths.TEACHER_FOLDER);
            this.teacherDao.insert(teacher);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(TeacherDto teacherDto){
        try {
        Teacher teacher = TeacherMapper.idToEntity(teacherDto);
        teacher =  this.teacherDao.selectById(teacher);
        this.teacherDao.delete(teacher);
        ImgUtil.deleteImageWithId(teacher.getId(),teacherDto.getImageFile(),Constants.ImagePaths.TEACHER_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private void checkDuplicateTeacher(Teacher teacher){
        Teacher selectedTeacher=this.teacherDao.findTeacherByEmail(teacher.getEmail());
        if(selectedTeacher!=null){
            throw new UserAlreadyExist(Constants.Alerts.DUPLICATE_RECORD + teacher.getEmail());
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
