package Dto;


import annotation.NotNull;

public class EnrollmentDto {
    private String id;
    @NotNull(message = "Student id can't be null")
    private String studentId;
    @NotNull(message = "Course id cannot be null")
    private String course;
    @NotNull(message = "status cannot be null")
    private String status;
    @NotNull(message = "semester  cannot be null")
    private String semesterId;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(String semesterId) {
        this.semesterId = semesterId;
    }
}
