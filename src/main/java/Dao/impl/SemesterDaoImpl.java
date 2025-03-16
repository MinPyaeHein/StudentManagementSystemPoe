package Dao.impl;

import Model.Semester;
import Model.SemesterName;
import Model.SemesterStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SemesterDaoImpl extends GeneralDaoImpl<Semester>{

    public SemesterDaoImpl() {
        super(Semester.class);
    }

    @Override
    public Semester convertToObject(ResultSet rs) {


        try {
            return new Semester(
                    rs.getInt("id"),
                     SemesterName.valueOf(rs.getString("name")),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    SemesterStatus.valueOf(rs.getString("status"))
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void insert(Semester semester){
        String query = "INSERT INTO semesters (name, start_date, end_date, status) VALUES (?::semester_name, ?, ?, ?::semester_status)";
        executeUpdate(query, semester.getName().name(), semester.getStart_Date(), semester.getEnd_Date(), semester.getStatus().name());
    }

    public void update(Semester semester,String... conduction) {
        String query = "UPDATE semesters SET name = ?::semester_name, start_date = ?, end_date = ?, status = ?::semester_status WHERE id = ?";
        executeUpdate(query, semester.getName().name(), semester.getStart_Date(), semester.getEnd_Date(), semester.getStatus().name(), semester.getId());
    }

    public Semester findActiveSemester() {
        String query = "SELECT * FROM semesters WHERE status = 'active' LIMIT 1";
        List<Semester> resultSet = executeQuerry(query);

        for (Semester semester : resultSet) {
            return semester;
        }
        return null;
    }




}
