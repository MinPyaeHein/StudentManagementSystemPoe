package Service;



import Dto.CourseDto;
import Model.Course;

import java.util.List;

public interface CourseService {
    public void update(CourseDto courseDto);
    public List<Course> getAllCourses();
    public Course getCoursebyCode(String course_code);
    public void saveCourse(CourseDto courseDto);
    public void delete(CourseDto courseDto);
    public List<Course> searchCourseByKeyword(String keyword);
    public Course getCourseId(int courseId);
}
