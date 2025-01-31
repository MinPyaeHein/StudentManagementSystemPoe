package Dao.impl;
import Model.Student;

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
                    rs.getString("address")
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

}
