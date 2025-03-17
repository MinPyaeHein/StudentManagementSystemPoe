package Model;

import annotation.*;

import java.time.LocalDateTime;

@Table(name = "enrollments")
public class Enrollment {
    @Id(name = "id")
    private int id;
    @ManyToOne(name = "student_id")
    private Student student;
    @ManyToOne(name = "course_id")
    private Course course;
    @Column(name = "enrollment_date")
    private LocalDateTime enrollment_date;
    @Column(name = "grade")
    private String grade;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "status")
    private String status;
    @ManyToOne(name = "semester_id")
    private Semester semester;

    public Enrollment(){

    }
    public Enrollment(int id){
      this.id = id;
    }

    public Enrollment(int id,Student student, Course course, LocalDateTime enrollment_date, String grade, LocalDateTime created_at, LocalDateTime updated_at,String status,Semester semester) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.enrollment_date = enrollment_date;
        this.grade = grade;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.semester = semester;
    }

    public Enrollment(Student student, Course course, LocalDateTime enrollment_date, String grade, LocalDateTime created_at, LocalDateTime updated_at,String status,Semester semester) {
        this.student = student;
        this.course = course;
        this.enrollment_date = enrollment_date;
        this.grade = grade;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
        this.semester = semester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", student=" + student +
                ", course=" + course +
                ", enrollment_date=" + enrollment_date +
                ", grade='" + grade + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", status='" + status + '\'' +
                ", semester=" + semester +
                '}';
    }
}
