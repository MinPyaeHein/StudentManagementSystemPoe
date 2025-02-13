package Service.impl;

import Dao.impl.CoursesDaoImpl;
import Model.Course;
import Service.CourseService;
import Utils.AlertUtil;
import Utils.ValidateUtail;

import java.time.LocalDateTime;
import java.util.List;
import Exception.*;

public class CourseServiceImpl implements CourseService {
    private CoursesDaoImpl coursesDao;


    public CourseServiceImpl(){
        coursesDao = new CoursesDaoImpl();
    }
    @Override
    public void update(Course course) {
        try {
            ValidateUtail.validate(course);
            coursesDao.update(course, "id");
            AlertUtil.alert("Successfully updated","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }

    @Override
    public List<Course> getAllCourses() {
        return coursesDao.selectAll();
    }

    @Override
    public Course getCourseId(int courseId) {
        return this.coursesDao.selectById(new Course(courseId));
    }

    @Override
    public void saveCourse(Course course) {
        try {
            ValidateUtail.validate(course);
            validateExistCourse(course);
            this.coursesDao.insert(course);
            AlertUtil.alert("Successfully saved!!","INFORMATION");
        }catch (InvalidDataFormatException e){
            AlertUtil.alert(e.getMessage(),"ERROR");
        }

    }


    @Override
    public void delete(int id) {
        Course course=new Course(id);
        course = this.coursesDao.selectById(course);
        if(course!=null && AlertUtil.confirmationDialog("Delete Confirmation","Are you sure you want to delete this course?\n"+course.getCourse_name())){
            this.coursesDao.delete(course);
        }

    }

    @Override
    public List<Course> searchCourseByKeyword(String keyword) {
        return this.coursesDao.findCourseByKeyword(keyword);
    }

    @Override
    public Course getCoursebyCode(String course_code) {
        return coursesDao.findCourseByCode(course_code);
    }

    private void validateExistCourse(Course course){

            Course duplicateCourse = this.coursesDao.findCourseByCode(course.getCourse_code());
            if (duplicateCourse != null) {
                AlertUtil.alert("Duplicate course code found!! ", "ERROR");
            }


        }
}
