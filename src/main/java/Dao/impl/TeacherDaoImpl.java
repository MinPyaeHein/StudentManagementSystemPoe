package Dao.impl;
import Model.Student;
import Model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDaoImpl extends GeneralDaoImpl<Teacher> {

    public TeacherDaoImpl() {
        super(Teacher.class);
    }

    @Override
    public Teacher convertToObject(ResultSet rs) {
        try {
            return new Teacher(
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
    public Teacher findTeacherByEmail(String email){
        String query = "SELECT * FROM teachers WHERE email =?";
        List<Teacher> resultSet=executeQuerry(query, email);
        for(Teacher teacher: resultSet){
            return teacher;
        }
        return null;
    }
    public List<Teacher> findTeachersByName(String name) {
        String query = "SELECT * FROM teachers WHERE LOWER(name) LIKE LOWER(?)";
        return executeQuerry(query,  name.toLowerCase() + "%");
    }

}
