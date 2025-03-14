package Service.impl;

import Constant.Constants;
import Dao.impl.EnrollmentDaoImpl;
import Dto.EnrollmentDto;
import Mapper.EnrollmentMapper;
import Model.*;
import Utils.AlertUtil;
import java.util.List;

public class EnrollmentServiceImpl {
    private EnrollmentDaoImpl enrollmentDao = new EnrollmentDaoImpl();


    public void saveEnrollment(List<EnrollmentDto> enrollmentDtos) {
        if (!enrollmentDtos.isEmpty()) {
            for (EnrollmentDto enrollmentDto : enrollmentDtos) {
                Enrollment enrollments = EnrollmentMapper.toEntity(enrollmentDto);
                this.enrollmentDao.insert(enrollments);
            }
            AlertUtil.alert(Constants.Alerts.SAVE_SUCCESS,Constants.Alerts.INFO);
        } else {
            AlertUtil.alert(Constants.Alerts.NO_SELECTED_ENROLLMENT, Constants.Alerts.ERROR);
        }
    }

    public List<Enrollment> getAllEnrolledCourses(int studentId) {
        List<Enrollment> enrollments = enrollmentDao.getEnrollmentByStudentId(studentId);
        return enrollments;
    }

}
