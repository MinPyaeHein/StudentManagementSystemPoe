package Model;

import annotation.*;

import java.time.LocalDateTime;

@Table(name="courses")
public class Course {

    @Id(name = "id")
    private int id;
    @NotNull(message = "Course Name cannot be null")
    @Column(name = "course_name")
    private String course_name;
    @NotNull(message = "course code cannot be null")
    @Column(name = "course_code")
    private String course_code;
    @Column(name = "description")
    private String description;
    @NotNull(message = "credits cannot be null")
    @Column(name = "credits")
    private int credits;
    @NotNull(message = "Department cannot be null")
    @ManyToOne(name = "department_id")
    private Department department;
    @NotNull(message = "teacher cannot be null")
    @ManyToOne(name="teacher_id")
    private Teacher teacher;
    @NotNull(message = "schedule cannot be null")
    @Column(name = "schedule")
    private String schedule;
    @NotNull(message = "capacity cannot be null")
    @Column(name = "capacity")
    private int capacity;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    public Course(int id){
        this.id = id;
    }

    public Course(int id, String course_name, String course_code, String description, int credits, Department department, Teacher teacher, String schedule, int capacity, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id = id;
        this.course_name = course_name;
        this.course_code = course_code;
        this.description = description;
        this.credits = credits;
        this.department = department;
        this.teacher = teacher;
        this.schedule = schedule;
        this.capacity = capacity;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Course(String course_name, String course_code, String description, int credits, Department department, Teacher teacher, String schedule, int capacity, LocalDateTime created_at, LocalDateTime updated_at) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.description = description;
        this.credits = credits;
        this.department = department;
        this.teacher = teacher;
        this.schedule = schedule;
        this.capacity = capacity;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Course(String course_name, String course_code, String description, int credits, Department department, Teacher teacher, String schedule, int capacity, LocalDateTime created_at) {
        this.course_name = course_name;
        this.course_code = course_code;
        this.description = description;
        this.credits = credits;
        this.department = department;
        this.teacher = teacher;
        this.schedule = schedule;
        this.capacity = capacity;
        this.created_at = created_at;

    }


    public Course(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }



    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
