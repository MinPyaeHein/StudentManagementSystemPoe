package Service.impl;

import Constant.Constants;
import Dao.impl.SemesterDaoImpl;
import Dto.SemesterDto;
import Mapper.SemesterMapper;
import Model.Semester;
import Model.SemesterStatus;
import Service.SemesterService;
import Utils.AlertUtil;
import Utils.ValidateUtail;
import Exception.*;

import java.time.LocalDate;
import java.util.List;

public class SemesterServiceImpl implements SemesterService {
private SemesterDaoImpl semesterDao;
public SemesterServiceImpl(){
    this.semesterDao = new SemesterDaoImpl();
}
    @Override
    public void saveSemester(SemesterDto semesterDto) {
        Semester semester = SemesterMapper.toEntity(semesterDto);
        try{
        ValidateUtail.validate(semesterDto);
        this.semesterDao.insert(semester);
        AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS, Constants.Alerts.INFO);
    }catch(InvalidDataFormatException exception){
        AlertUtil.alert(exception.getMessage(), Constants.Alerts.ERROR);
    }
}

    @Override
    public void update(SemesterDto semesterDto) {
    Semester semester = SemesterMapper.toEntity(semesterDto);

    try{
        ValidateUtail.validate(semesterDto);
        semesterDao.update(semester,Constants.FieldConstraints.ID);
       AlertUtil.alert(Constants.Alerts.UPDATE_SUCCESS, Constants.Alerts.INFO);
    }catch(InvalidDataFormatException exception){
        AlertUtil.alert(exception.getMessage(), Constants.Alerts.ERROR);
    }
}


    @Override
    public List<Semester> getAllSemester() {
        return this.semesterDao.selectAll();
    }

    public LocalDate getUpcomingSemesterStartDate() {
        Semester activeSemester = semesterDao.findUpcomingSemester();
        return activeSemester != null ? activeSemester.getStart_Date() : null;
    }
    public Semester getUpcomingSemester() {
        Semester upComingSemester = semesterDao.findUpcomingSemester();
        return upComingSemester;
    }

}
