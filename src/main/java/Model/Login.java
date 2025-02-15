package Model;

import annotation.NotNull;

public class Login {
    private String studentName;
    @NotNull(message = "student id can't be null")
    private String studentId;
    @NotNull(message = "student password can't be null")
    private String password;

    public Login(String studentName,String studentId, String password) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
}
