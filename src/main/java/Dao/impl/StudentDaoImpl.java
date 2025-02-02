package Dao.impl;
import Dao.FacultyDao;
import Model.Faculty;
import Model.Student;
import Model.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class StudentDaoImpl extends GeneralDaoImpl<Student> {
    private FacultyDao facultyDao;
    public StudentDaoImpl() {

        super(Student.class);
        this.facultyDao=new FacultyDaoImpl();
    }

    @Override
    public Student convertToObject(ResultSet rs) throws SQLException {
        Faculty faculty=this.facultyDao.selectById(new Faculty(rs.getInt("faculty_id")));
        try {
            return new Student(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    faculty
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

    public List<Student> findStudentByKeyword(String keyword) {
        String query = "SELECT * FROM students WHERE " +
                "CAST(id AS TEXT) LIKE ? OR " +
                "LOWER(name) LIKE LOWER(?) OR " +
                "LOWER(email) LIKE LOWER(?) OR " +
                "LOWER(address) LIKE LOWER(?)";
        String searchPattern = "%" + keyword.toLowerCase() + "%";
        return executeQuerry(query,  searchPattern,searchPattern,searchPattern,searchPattern);
    }

}
