package Service;

import Dao.impl.TeacherDaoImpl;
import Model.Teacher;

import java.util.List;

public class TeacherService {
    private TeacherDaoImpl teacherDao;

    public TeacherService(){
        this.teacherDao = new TeacherDaoImpl();
    }

    public void update(Teacher teacher){
        teacherDao.update(teacher,"id");
    }

    public List<Teacher> getAllTeacher(){
        return teacherDao.selectAll();
    }

    public Teacher getTeacherById(int teacherId) {
        return this.teacherDao.selectById(new Teacher(teacherId));
    }

    public void saveTeacher(Teacher teacher){
        this.teacherDao.insert(teacher);
    }

    public boolean delete(int id){
        Teacher teacher = new Teacher(id);
        teacher = this.teacherDao.selectById(teacher);
        if(teacher != null){
            this.teacherDao.delete(teacher);
            return  true;
        }else{
            return false;
        }
    }
}
