package Service.impl;

import Dao.impl.CoursesDaoImpl;
import Dto.CourseDto;
import Mapper.CourseMapper;
import Model.Course;
import Model.Department;
import Model.Teacher;
import Service.CourseService;
import Utils.AlertUtil;
import Utils.UtilConstants;
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
            coursesDao.update(course, UtilConstants.ID_FIELD);
            AlertUtil.alert(UtilConstants.UPDATE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),UtilConstants.ERROR_ALERT);
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
                throw new InvalidDataFormatException(UtilConstants.CAPACITY_FIELD);
            }
            this.coursesDao.insert(course);
            AlertUtil.alert(UtilConstants.SAVE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
        }catch (InvalidDataFormatException e){
            AlertUtil.alert(e.getMessage(),UtilConstants.ERROR_ALERT);
        }

    }


    @Override
    public void delete(CourseDto courseDto) {
        Course course= CourseMapper.idToEntity(courseDto);
        course = this.coursesDao.selectById(course);
        if(course!=null && AlertUtil.confirmationDialog(UtilConstants.DELETE_CONFIRM_TITLE,UtilConstants.DELETE_CONFIRM_MESSAGE+"\n"+course.getCourse_name())){
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
                throw new InvalidDataFormatException(UtilConstants.DUPLICATE_RECORD_ERROR+course.getCourse_name()+"\n"+course.getCourse_code());
            }
        }
    public Course findCourseByName(String name) {
        return coursesDao.findCourseByName(name);
    }


}
