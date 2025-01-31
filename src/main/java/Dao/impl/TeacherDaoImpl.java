package Dao.impl;
import Model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

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
                    rs.getString("address")
                    );
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
