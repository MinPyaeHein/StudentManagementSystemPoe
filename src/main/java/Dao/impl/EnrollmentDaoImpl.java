package Dao.impl;

import Model.Course;
import Model.Enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class EnrollmentDaoImpl extends GeneralDaoImpl<Enrollment> {

    public EnrollmentDaoImpl() {
        super(Enrollment.class);
    }

    @Override
    public Enrollment convertToObject(ResultSet rs) {
        try {
            String courseCode = rs.getString("course_code");
            String courseName = rs.getString("course_name");
            Course course = new Course();
            course.setCourse_code(courseCode);
            course.setCourse_name(courseName);


            return new Enrollment(
                    0,
                    0,
                    course,
                    null,
                    rs.getString("grade"),
                    null,
                    null
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Enrollment enrollment) {
        String query = "INSERT INTO enrollments (student_id, course_id) " +
                "VALUES (?, ?)";
        executeUpdate(query, enrollment.getStudent_id(), enrollment.getCourse().getId());
    }

    public List<Enrollment> getEnrollmentByStudentId(int studentid) {
        String sql = "SELECT c.course_code, c.course_name, e.grade FROM enrollments e " +
                "JOIN courses c ON e.course_id = c.id " +
                "WHERE e.student_id = ?";
        return executeQuerry(sql, studentid);
    }


}
