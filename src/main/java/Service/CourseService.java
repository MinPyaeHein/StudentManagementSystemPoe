package Service;



import Model.Course;

import java.util.List;

public interface CourseService {
    public void update(Course course);
    public List<Course> getAllCourses();
    public Course getCoursebyCode(String course_code);
    public void saveCourse(Course course);
    public void delete(int id);
    public List<Course> searchCourseByKeyword(String keyword);
    public Course getCourseId(int courseId);
}
