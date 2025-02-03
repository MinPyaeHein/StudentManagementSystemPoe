package Dao.impl;
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
    public List<Teacher> findTeacherByKeyword(String keyword) {
        String query = "SELECT * FROM teachers WHERE " +
                "CAST(id AS TEXT) LIKE ? OR " +
                "LOWER(name) LIKE LOWER(?) OR " +
                "LOWER(email) LIKE LOWER(?) OR " +
                "LOWER(address) LIKE LOWER(?) OR " +
                "CAST(phone AS TEXT) LIKE ?";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query,  searchPattern,searchPattern,searchPattern,searchPattern,searchPattern);
    }

}
