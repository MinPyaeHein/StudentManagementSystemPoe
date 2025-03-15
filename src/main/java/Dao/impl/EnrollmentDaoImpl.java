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
            int credits = rs.getInt("credits");
            Course course = new Course();
            course.setCourse_code(courseCode);
            course.setCourse_name(courseName);
            course.setCredits(credits);
            Semester semester = new Semester();



            return new Enrollment(
                    0,
                    0,
                    course,
                    null,
                    rs.getString("grade"),
                    null,
                    null,
                    rs.getString("status")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Enrollment enrollment) {
        String query = "INSERT INTO enrollments (student_id, course_id,status) " +
                "VALUES (?, ?, ?)";
        executeUpdate(query, enrollment.getStudent_id(), enrollment.getCourse().getId(),enrollment.getStatus());
    }
    public List<Enrollment> getEnrollmentByStudentIdAndSemesterId(int studentId,int semesterId) {
        String sql = "SELECT c.course_code, c.course_name, c.credits, e.grade, e.status, s.name AS semester_name, s.start_date, s.end_date " +
                "FROM enrollments e " +
                "JOIN courses c ON e.course_id = c.id " +
                "JOIN semester s ON e.semester_id = s.id " +
                "WHERE e.student_id = ? AND e.semester_id = ?";

        return executeQuerry(sql, studentId, semesterId);
    }




}
