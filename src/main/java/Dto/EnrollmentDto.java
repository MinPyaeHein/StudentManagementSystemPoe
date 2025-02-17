package Dto;

import java.util.List;

public class EnrollmentDto {
    private String id;
    private String student_id;
    private String course;

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
