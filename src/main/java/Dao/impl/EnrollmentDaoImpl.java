package Dao.impl;

import Model.Course;
import Model.Enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EnrollmentDaoImpl extends GeneralDaoImpl<Enrollment> {
    private CoursesDaoImpl coursesDao;

    public EnrollmentDaoImpl() {
        super(Enrollment.class);
        this.coursesDao = new CoursesDaoImpl();
    }

    @Override
    public Enrollment convertToObject(ResultSet rs) {
        try {
            Course course = this.coursesDao.selectById(new Course(rs.getInt("course_id")));
            Timestamp createdTimestamp = rs.getTimestamp("created_at");
            LocalDateTime created_at = (createdTimestamp != null) ? createdTimestamp.toLocalDateTime() : null;
            Timestamp enrolledTimestamp = rs.getTimestamp("enrollment_date");
            LocalDateTime enrollment_date = (enrolledTimestamp != null) ? enrolledTimestamp.toLocalDateTime() : null;

            Timestamp updatedTimestamp = rs.getTimestamp("updated_at");
            LocalDateTime updated_at = (updatedTimestamp != null) ? updatedTimestamp.toLocalDateTime() : null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            return new Enrollment(
                    rs.getInt("id"),
                    rs.getInt("student_id"),
                    course,
                    enrollment_date,
                    rs.getString("grade"),
                    created_at,
                    updated_at
            );


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(Enrollment enrollments){
        String query = "INSERT INTO enrollments (student_id, course_id) " +
                "VALUES (?, ?)";
        executeUpdate(query, enrollments.getStudent_id(), enrollments.getCourse().getId());
    }


}
