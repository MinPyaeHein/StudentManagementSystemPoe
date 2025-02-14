package Model;

import annotation.Column;
import annotation.Id;
import annotation.NotNull;
import annotation.Table;

import java.time.LocalDateTime;

@Table(name = "enrollments")
public class Enrollments {
    @Id(name = "id")
    private int id;
    @NotNull(message = "Student id can't be null")
    @Column(name = "student_id")
    private String student_id;
    @NotNull(message = "Course id cannot be null")
    @Column(name = "course_id")
    private Course course_id;
    @Column(name = "enrollment_date")
    private LocalDateTime enrollment_date;
    @Column(name = "grade")
    private String grade;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Enrollments(){

    }

    public Enrollments(int id, String student_id, Course course_id, LocalDateTime enrollment_date, String grade, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.enrollment_date = enrollment_date;
        this.grade = grade;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Enrollments(String student_id, Course course_id, LocalDateTime enrollment_date, String grade, LocalDateTime created_at, LocalDateTime updated_at) {
        this.student_id = student_id;
        this.course_id = course_id;
        this.enrollment_date = enrollment_date;
        this.grade = grade;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public Course getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Course course_id) {
        this.course_id = course_id;
    }

    public LocalDateTime getEnrollment_date() {
        return enrollment_date;
    }

    public void setEnrollment_date(LocalDateTime enrollment_date) {
        this.enrollment_date = enrollment_date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
