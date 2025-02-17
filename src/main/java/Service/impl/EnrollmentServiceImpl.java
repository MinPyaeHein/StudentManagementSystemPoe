package Service.impl;

import Dao.impl.EnrollmentDaoImpl;
import Dto.EnrollmentDto;
import Mapper.EnrollmentMapper;
import Model.Enrollment;
import Utils.AlertUtil;
import Utils.UtilConstants;
import Exception.InvalidDataFormatException;
import Utils.ValidateUtail;

public class EnrollmentServiceImpl {
private EnrollmentDaoImpl enrollmentDao=new EnrollmentDaoImpl();
    public void saveEnrollment(EnrollmentDto enrollmentDto){
        Enrollment enrollment= EnrollmentMapper.toEntity(enrollmentDto);
        try{
            ValidateUtail.validate(enrollment);
            this.enrollmentDao.insert(enrollment);
            AlertUtil.alert(UtilConstants.SAVE_SUCCESS_MESSAGE,UtilConstants.INFO_ALERT);
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),UtilConstants.ERROR_ALERT);
        }
    }
}
