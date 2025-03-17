package Dao.impl;

import Model.*;

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
            String semesterName = rs.getString("semester_name");
            String semesterStatus = rs.getString("semester_status");
            Semester semester=new Semester();
            semester.setName(SemesterName.valueOf(semesterName));
            semester.setStatus(SemesterStatus.valueOf(semesterStatus));

            return new Enrollment(
                    0,
                    null,
                    course,
                    null,
                    rs.getString("grade"),
                    null,
                    null,
                    rs.getString("status"),
                    semester
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Enrollment enrollment) {
        String query = "INSERT INTO enrollments (student_id, course_id,status,semester_id) " +
                "VALUES (?, ?, ?,?)";
        executeUpdate(query, enrollment.getStudent().getId(), enrollment.getCourse().getId(),enrollment.getStatus(),enrollment.getSemester().getId());
    }

    public List<Enrollment> getEnrollmentByStudentId(int studentId) {
        String sql = "SELECT c.course_code, c.course_name, c.credits, e.grade, e.status, " +
                "s.name AS semester_name, s.status AS semester_status, s.start_date, s.end_date " +
                "FROM enrollments e " +
                "JOIN courses c ON e.course_id = c.id " +
                "JOIN semesters s ON e.semester_id = s.id " +
                "WHERE e.student_id = ?";

        return executeQuerry(sql, studentId);
    }







}
