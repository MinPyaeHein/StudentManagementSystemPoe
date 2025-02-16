package Dao.impl;

import Model.Course;
import Model.Department;
import Model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CoursesDaoImpl extends GeneralDaoImpl<Course> {
    private DepartmentDaoImpl departmentDao;
    private TeacherDaoImpl teacherDao;
    public CoursesDaoImpl() {
        super(Course.class);
        this.departmentDao = new DepartmentDaoImpl();
        this.teacherDao = new TeacherDaoImpl();
    }

    @Override
    public Course convertToObject(ResultSet rs) {

        try {
            Department department = this.departmentDao.selectById(new Department(rs.getInt("department_id")));
            Teacher teacher = this.teacherDao.selectById(new Teacher(rs.getInt("teacher_id")));
            Timestamp createdTimestamp = rs.getTimestamp("created_at");
            LocalDateTime created_at = (createdTimestamp != null) ? createdTimestamp.toLocalDateTime() : null;

            Timestamp updatedTimestamp = rs.getTimestamp("updated_at");
            LocalDateTime updated_at = (updatedTimestamp != null) ? updatedTimestamp.toLocalDateTime() : null;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");



            return new Course(
                    rs.getInt("id"),
                    rs.getString("course_name"),
                    rs.getString("course_code"),
                    rs.getString("description"),
                    rs.getInt("credits"),
                    department,
                    teacher,
                    rs.getInt("capacity"),
                    created_at,
                    updated_at


            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Course findCourseByCode(String code) {
        String query = "SELECT * FROM courses WHERE course_code =?";
            List<Course> resultSet=executeQuerry(query, code);
            for(Course course: resultSet){
                return course;
            }
            return null;
    }
    public Course findCourseByName(String name) {
        String query = "SELECT * FROM courses WHERE course_name =?";
        List<Course> resultSet=executeQuerry(query, name);
        for(Course course: resultSet){
            return course;
        }
        return null;
    }

    public List<Course> findCourseByKeyword(String keyword) {
        String query = "SELECT c.id, c.course_name, c.course_code, c.description, c.credits, " +
                "c.department_id, c.teacher_id, c.capacity, c.created_at, c.updated_at " +
                "FROM courses c, departments d, teachers t " +
                "WHERE c.department_id = d.id AND c.teacher_id = t.id " +
                "AND (" +
                "CAST(c.id AS TEXT) LIKE ? OR " +
                "LOWER(c.course_name) LIKE LOWER(?) OR " +
                "LOWER(c.course_code) LIKE LOWER(?) OR " +
                "LOWER(c.description) LIKE LOWER(?) OR " +
                "LOWER(d.department) LIKE LOWER(?) OR " +
                "LOWER(t.name) LIKE LOWER(?) OR " +
                "CAST(c.capacity AS TEXT) LIKE ? OR " +
                "CAST(c.created_at AS TEXT) LIKE ? OR " +
                "CAST(c.updated_at AS TEXT) LIKE ? OR " +
                "CAST(c.credits AS TEXT) LIKE ? " +
                ")";

        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query, searchPattern, searchPattern, searchPattern, searchPattern,
                searchPattern, searchPattern, searchPattern, searchPattern, searchPattern, searchPattern);

    }


}
