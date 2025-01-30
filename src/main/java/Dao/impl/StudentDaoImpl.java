package Dao.impl;
import Model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;


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

}
