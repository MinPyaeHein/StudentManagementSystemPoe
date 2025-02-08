package Dao.impl;

import Model.Degree;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DegreeDaoImpl extends GeneralDaoImpl<Degree> {
    public DegreeDaoImpl(){
        super(Degree.class);
    }

    @Override
    public Degree convertToObject(ResultSet rs) {
        try {
            return new Degree(
                    rs.getInt("id"),
                    rs.getString("degree")
            );
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public Degree findDegreeByName(String name) {
        String query = "SELECT * FROM degree WHERE degree =?";
        List<Degree> resultSet=executeQuerry(query, name);
        for(Degree degree: resultSet){
            return degree;
        }
        return null;
    }

    public List<Degree> findDegreeByKeyword(String keyword) {
        String query = "SELECT * FROM degree WHERE " +
                "CAST(id AS TEXT) LIKE ? OR " +
                "LOWER(degree) LIKE LOWER(?)";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query, searchPattern, searchPattern);
    }

}

