package Dao.impl;
import Model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeacherDaoImpl extends GeneralDaoImpl<Teacher> {
    private DepartmentDaoImpl departmentDao;
    private DegreeDaoImpl degreeDao;
    public TeacherDaoImpl() {
        super(Teacher.class);
        this.departmentDao = new DepartmentDaoImpl();
        this.degreeDao = new DegreeDaoImpl();
    }

    @Override
    public Teacher convertToObject(ResultSet rs) {
        try {
            Department department=this.departmentDao.selectById(new Department(rs.getInt("department_id")));
            Degree degree = this.degreeDao.selectById(new Degree(rs.getInt("degree_id")));

            return new Teacher(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    degree,
                    department

                    );
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Teacher teacher){
        String query = "INSERT INTO teachers (name, email, address, phone,degree_id,department_id, gender) " +
                "VALUES (?, ?, ?, ?, ?, ? , ?::gender_enum)";
        executeUpdate(query, teacher.getName(), teacher.getEmail(), teacher.getAddress(), teacher.getPhone(),teacher.getDegree().getId(),teacher.getDepartment().getId(), teacher.getGender().name());
    }

    public void update(Teacher teacher, String... conductions) {
        String query = "UPDATE teachers SET " +
                "name =?, email =?, address =?, phone =?, degree_id=?, department_id=?,  gender =?::gender_enum" +
                " WHERE id =?";
        executeUpdate(query, teacher.getName(), teacher.getEmail(), teacher.getAddress(), teacher.getPhone(),teacher.getDegree().getId(),teacher.getDepartment().getId(), teacher.getGender().name(),teacher.getId());

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
                "CAST(phone AS TEXT) LIKE ? OR " +
                "LOWER(degree AS TEXT) LIKE LOWER(?) OR " +
                "CAST(department_id AS TEXT) LIKE LOWER(?)";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query,  searchPattern,searchPattern,searchPattern,searchPattern,searchPattern,searchPattern,searchPattern);
    }

}
