package Dao.impl;
import Dao.FacultyDao;
import Model.Faculty;
import Model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class FacultyDaoImpl extends GeneralDaoImpl<Faculty> implements FacultyDao {

    public FacultyDaoImpl() {
        super(Faculty.class);
    }

    @Override
    public Faculty convertToObject(ResultSet rs) {
        try {
            return new Faculty(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("website_link"),
                    rs.getString("phone")
            );
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
    @Override
    public Faculty findFacultyByEmail(String email){
        String query = "SELECT * FROM faculties WHERE email =?";
        List<Faculty> resultSet=executeQuerry(query, email);
        for(Faculty student: resultSet){
           return student;
        }
        return null;
    }
    @Override
    public Faculty findFacultyByName(String name) {
        String query = "SELECT * FROM faculties WHERE name =?";
        List<Faculty> resultSet=executeQuerry(query, name);
        for(Faculty student: resultSet){
            return student;
        }
        return null;
    }
    @Override
    public List<Faculty> findFacultyByKeyword(String keyword) {
        String query = "SELECT * FROM faculties WHERE " +
                "CAST(id AS TEXT) LIKE ? OR " +
                "LOWER(name) LIKE LOWER(?) OR " +
                "LOWER(email) LIKE LOWER(?) OR " +
                "LOWER(address) LIKE LOWER(?)";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query,  searchPattern,searchPattern,searchPattern,searchPattern);
    }


}
