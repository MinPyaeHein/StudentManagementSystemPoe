package Service.impl;

import Constant.Constants;
import Dao.impl.CoursesDaoImpl;
import Dto.CourseDto;
import Mapper.CourseMapper;
import Model.Course;
import Model.Department;
import Model.Enrollment;
import Model.Teacher;
import Service.CourseService;
import Service.StudentService;
import Utils.AlertUtil;
import Utils.ValidateUtail;

import java.util.ArrayList;
import java.util.List;
import Exception.*;

public class CourseServiceImpl implements CourseService {
    private CoursesDaoImpl coursesDao;
    private EnrollmentServiceImpl enrollmentService;


    public CourseServiceImpl(){
        coursesDao = new CoursesDaoImpl();
        enrollmentService = new EnrollmentServiceImpl();
    }
    @Override
    public void update(CourseDto courseDto) {
        Course course = CourseMapper.toEntity(courseDto);
        try {
            ValidateUtail.validate(course);
            coursesDao.update(course, Constants.FieldConstraints.ID);
            AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS, Constants.Alerts.INFO);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(), Constants.Alerts.ERROR);
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
                throw new InvalidDataFormatException(Constants.FieldConstraints.MAX_CAPACITY);
            }
            this.coursesDao.insert(course);
            AlertUtil.alert(Constants.Alerts.SAVE_SUCCESS, Constants.Alerts.SAVE_SUCCESS);
        }catch (InvalidDataFormatException e){
            AlertUtil.alert(e.getMessage(), Constants.Alerts.ERROR);
        }

    }


    @Override
    public void delete(CourseDto courseDto) {
        Course course= CourseMapper.idToEntity(courseDto);
        course = this.coursesDao.selectById(course);
        if(course!=null && AlertUtil.confirmationDialog(Constants.Alerts.DELETE_CONFIRM_TITLE, Constants.Alerts.DELETE_CONFIRM_MESSAGE+"\n"+course.getCourse_name())){
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
                throw new InvalidDataFormatException(Constants.Alerts.DUPLICATE_RECORD+course.getCourse_name()+"\n"+course.getCourse_code());
            }
        }
    public Course findCourseByName(String name) {
        return coursesDao.findCourseByName(name);
    }

    public List<Course> availableCourses() {
        List<Course> courseList = this.coursesDao.selectAll();
        List<Course> availableCourseList = new ArrayList<>();
        List<Enrollment> registeredEnrollment = enrollmentService.getAllEnrolledCoursesByStudentId(StudentServiceImpl.studentId);
        List<String> registeredCourseNames = new ArrayList<>();

        for (Enrollment enrollment : registeredEnrollment) {
            registeredCourseNames.add(enrollment.getCourse().getCourse_name());
        }
        for (Course course : courseList) {
            if (!registeredCourseNames.contains(course.getCourse_name())) {
                availableCourseList.add(course);
            }
        }
        return availableCourseList;
    }

}
