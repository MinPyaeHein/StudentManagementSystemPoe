package Service.impl;

import Dao.impl.StudentDaoImpl;
import Dto.LoginDto;
import Model.Student;
import Utils.DaoUtil;
import Utils.ValidateUtail;
import Exception.*;

public class LoginService {
    private StudentDaoImpl studentDao;

    public LoginService() {
        studentDao = new StudentDaoImpl();
    }


    public void login(LoginDto loginDto) {
            ValidateUtail.validate(loginDto);
            authenticateUser(loginDto);
    }

    private void authenticateUser(LoginDto loginDto) {
        Student student = studentDao.findStudentByEmailAndPassword(loginDto.getGmail(), loginDto.getPassword());
        if (student == null ) {
            throw new UserNotFountException("Please enter correct email and password !!");
        }else{
            DaoUtil.authStudent=student;
        }
    }
}

