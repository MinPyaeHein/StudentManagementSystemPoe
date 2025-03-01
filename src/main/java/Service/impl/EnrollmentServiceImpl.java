package Service.impl;

import Dao.impl.EnrollmentDaoImpl;
import Dto.EnrollmentDto;
import Mapper.EnrollmentMapper;
import Model.Enrollment;
import Utils.AlertUtil;
import Utils.UtilConstants;
import Exception.InvalidDataFormatException;

import java.util.List;

public class EnrollmentServiceImpl {
    private EnrollmentDaoImpl enrollmentDao = new EnrollmentDaoImpl();


    public void saveEnrollment(List<EnrollmentDto> enrollmentDtos) {
        if (!enrollmentDtos.isEmpty()) {
            for (EnrollmentDto enrollmentDto : enrollmentDtos) {
                Enrollment enrollments = EnrollmentMapper.toEntity(enrollmentDto);
                this.enrollmentDao.insert(enrollments);
            }
            AlertUtil.alert(UtilConstants.SAVE_SUCCESS_MESSAGE, UtilConstants.INFO_ALERT);
        } else {
            AlertUtil.alert(UtilConstants.NO_SELECTED_ENROLLMENT_ERROR, UtilConstants.ERROR_ALERT);
        }
    }
}
