package Dao.impl;

import Model.Department;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DepartmentDaoImpl extends GeneralDaoImpl<Department>{
    public DepartmentDaoImpl() {
        super(Department.class);
    }

    @Override
    public Department convertToObject(ResultSet rs) {
        try {
            return new Department(
                    rs.getInt("id"),
                    rs.getString("department")
            );
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }


    public Department findDepartmentByName(String name) {
        String query = "SELECT * FROM departments WHERE department =?";
        List<Department> resultSet=executeQuerry(query, name);
        for(Department department: resultSet){
            return department;
        }
        return null;
    }

    public List<Department> findDepartmentByKeyword(String keyword) {
        String query = "SELECT * FROM departments WHERE " +
                "CAST(id AS TEXT) LIKE ? OR " +
                "LOWER(department) LIKE LOWER(?)";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query, searchPattern, searchPattern);
    }

}
