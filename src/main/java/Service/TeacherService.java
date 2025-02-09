package Service;

import Dao.impl.TeacherDaoImpl;
import Model.Teacher;
import Exception .*;
import Utils.AlertUtil;
import Utils.ValidateUtail;

import java.util.List;

public class TeacherService {
    private TeacherDaoImpl teacherDao;

    public TeacherService(){
        this.teacherDao = new TeacherDaoImpl();
    }

    public void update(Teacher teacher){
        try{
            ValidateUtail.validate(teacher);
            teacherDao.update(teacher,"id");
            AlertUtil.alert("Successfully updated","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    public List<Teacher> getAllTeacher(){
        return teacherDao.selectAll();
    }

    public Teacher getTeacherById(int teacherId) {
        return this.teacherDao.selectById(new Teacher(teacherId));
    }

    public void saveTeacher(Teacher teacher){
        try{
            ValidateUtail.validate(teacher);
            checkDuplicateTeacher(teacher);
            this.teacherDao.insert(teacher);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(UserAlreadyExist exception){
            AlertUtil.alert(exception.getMessage() + teacher.getEmail(),"ERROR");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    public void delete(int id){
        Teacher teacher = new Teacher(id);
        teacher = this.teacherDao.selectById(teacher);
        if(teacher != null &&
                AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete teacher?\n"+teacher.getEmail())){
            this.teacherDao.delete(teacher);
        }
    }
    public void checkDuplicateTeacher(Teacher teacher){
        Teacher selectedTeacher=this.teacherDao.findTeacherByEmail(teacher.getEmail());
        if(selectedTeacher!=null){
            throw new UserAlreadyExist("Duplicate teacher found!!! " + teacher.getEmail());
        }
    }
    public List<Teacher> searchTeacherByKeyword(String keyword) {
        return teacherDao.findTeacherByKeyword(keyword);
    }



}
