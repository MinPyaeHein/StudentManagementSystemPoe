package Dao.impl;
import Model.Student;
import Model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class StudentDaoImpl extends GeneralDaoImpl<Student> {

    public StudentDaoImpl() {
        super(Student.class);
    }

    @Override
    public Student convertToObject(ResultSet rs) {
        try {
            return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("phone")
            );
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
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

    public List<Student> findStudentByName(String name) {
        String query = "SELECT * FROM students WHERE LOWER(name) LIKE LOWER(?)";
        return executeQuerry(query,  name.toLowerCase() + "%");
    }

}
