package Dao.impl;
import Dao.FacultyDao;
import Model.Faculty;
import Model.Gender;
import Model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class StudentDaoImpl extends GeneralDaoImpl<Student> {
    private FacultyDao facultyDao;
    public StudentDaoImpl() {
        super(Student.class);
        this.facultyDao=new FacultyDaoImpl();
    }


    @Override
    public Student convertToObject(ResultSet rs){
        try {
        Faculty faculty=this.facultyDao.selectById(new Faculty(rs.getInt("faculty_id")));
            return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    faculty,
                    rs.getString("gender").equals("male") ? Gender.male : Gender.female
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public Student findStudentByEmail(String email){
        String query = "SELECT * FROM students WHERE email =?";
        List<Student> resultSet=executeQuerry(query, email);
        for(Student student: resultSet){
           return student;
        }
        return null;
    }
    @Override
    public void insert(Student student){
        String query = "INSERT INTO students (name, email, address, phone, gender, faculty_id) " +
                "VALUES (?, ?, ?, ?, ?::gender_enum, ?)";
        executeUpdate(query, student.getName(), student.getEmail(), student.getAddress(), student.getPhone(), student.getGender().name(), student.getFaculty().getId());
    }
    @Override
    public void update(Student student, String... conductions) {
        String query = "UPDATE students SET " +
                "name =?, email =?, address =?, phone =?, gender =?::gender_enum, faculty_id =?" +
                " WHERE id =?";
        executeUpdate(query, student.getName(), student.getEmail(), student.getAddress(), student.getPhone(), student.getGender().name(), student.getFaculty().getId(), student.getId());
    }

    public List<Student> findStudentByKeyword(String keyword) {
        String query = "SELECT s.* FROM students s, faculties f WHERE " +
                "s.faculty_id = f.id AND (" +
                "CAST(s.id AS TEXT) LIKE ? OR " +
                "LOWER(f.name) LIKE LOWER(?) OR " +
                "LOWER(s.name) LIKE LOWER(?) OR " +
                "LOWER(s.email) LIKE LOWER(?) OR " +
                "LOWER(s.address) LIKE LOWER(?) OR " +
                "LOWER(f.name) LIKE LOWER(?)" +
                ")";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query,  searchPattern,searchPattern,searchPattern,searchPattern,searchPattern,searchPattern);
    }


}
