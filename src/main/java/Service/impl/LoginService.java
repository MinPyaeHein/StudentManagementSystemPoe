package Service.impl;

import Dao.impl.StudentDaoImpl;
import Model.Login;
import Model.Student;
import Utils.AlertUtil;
import Utils.UtilConstants;
import Utils.ValidateUtail;
import Exception.InvalidDataFormatException;

public class LoginService {
    private StudentDaoImpl studentDao;

    public LoginService() {
        studentDao = new StudentDaoImpl();
    }


    public Student submit(Login login) {
        try {
            ValidateUtail.validate(login);
            int studentId = Integer.parseInt(login.getStudentId());

            Student student = getValidInputField(studentId, login.getPassword());
            return student;
        } catch (InvalidDataFormatException exception) {
            AlertUtil.alert(exception.getMessage(), UtilConstants.ERROR_ALERT);
            return null;
        }
    }

    private Student getValidInputField(int studentId, String password) {
        Student student = studentDao.findStudentById(studentId);

        if (student == null || studentId != student.getId()) {
            AlertUtil.alert(UtilConstants.STUDENT_ID_CANNOT_FOUND, UtilConstants.ERROR_ALERT);
            return null;
        }

        if (!password.equals(student.getPassword())) {
            AlertUtil.alert(UtilConstants.INCORRECT_PASSWORD, UtilConstants.ERROR_ALERT);
            return null;
        }

        return student;
    }

}

