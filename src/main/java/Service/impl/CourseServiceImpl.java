package Service.impl;
import Constant.Constants;
import Dao.impl.CoursesDaoImpl;
import Dto.CourseDto;
import Mapper.CourseMapper;
import Model.Course;
import Model.Student;
import Service.CourseService;
import Utils.ValidateUtail;
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
        try {
            Course course = CourseMapper.toEntity(courseDto);
            ValidateUtail.validate(courseDto);
            coursesDao.update(course, Constants.FieldConstraints.ID);
        }catch (InvalidDataFormatException e){
            throw new InvalidDataFormatException(e.getMessage());
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
           Course course = CourseMapper.toEntity(courseDto);
            ValidateUtail.validate(courseDto);
            validateExistCourse(course);
            if (course.getCapacity() > 100) {
                throw new InvalidDataFormatException(Constants.FieldConstraints.MAX_CAPACITY);
            }
            this.coursesDao.insert(course);
    }


    @Override
    public void delete(CourseDto courseDto) {
        Course course= CourseMapper.idToEntity(courseDto);
        course = this.coursesDao.selectById(course);
        this.coursesDao.delete(course);
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

    public List<Course> availableCoursesByStudent(Student student) {
        return coursesDao.findCoursesNotRegisteredByStudent(student.getId());
    }


}
