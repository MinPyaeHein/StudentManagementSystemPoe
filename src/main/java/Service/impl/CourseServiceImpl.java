package Service.impl;

import Dao.impl.CoursesDaoImpl;
import Dto.CourseDto;
import Mapper.CourseMapper;
import Model.Course;
import Service.CourseService;
import Utils.AlertUtil;
import Utils.ValidateUtail;

import java.util.List;
import Exception.*;

public class CourseServiceImpl implements CourseService {
    private CoursesDaoImpl coursesDao;


    public CourseServiceImpl(){
        coursesDao = new CoursesDaoImpl();
    }
    @Override
    public void update(CourseDto courseDto) {
        Course course = CourseMapper.toEntity(courseDto);
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
    public void saveCourse(CourseDto courseDto) {
        Course course= CourseMapper.toEntity(courseDto);
        try {
            ValidateUtail.validate(course);
            validateExistCourse(course);
            if(course.getCapacity() > 100){
                throw new InvalidDataFormatException("The maximum capacity is 100");
            }
            this.coursesDao.insert(course);
            AlertUtil.alert("Successfully saved!!","INFORMATION");
        }catch (InvalidDataFormatException e){
            AlertUtil.alert(e.getMessage(),"ERROR");
        }

    }


    @Override
    public void delete(CourseDto courseDto) {
        Course course= CourseMapper.idToEntity(courseDto);
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
            Course duplicateCourseCode = this.coursesDao.findCourseByCode(course.getCourse_code());
            Course duplicateCourseName = this.coursesDao.findCourseByName(course.getCourse_name());
            if (duplicateCourseCode != null || duplicateCourseName !=null) {
                throw new InvalidDataFormatException("Duplicate course name or code found!!");
            }


        }
}
