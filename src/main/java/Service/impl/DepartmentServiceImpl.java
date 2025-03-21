package Service.impl;

import Constant.Constants;
import Dao.impl.DepartmentDaoImpl;
import Dto.DepartmentDto;
import Mapper.DepartmentMapper;
import Model.Department;
import Service.DepartmentService;
import Utils.AlertUtil;
import Utils.ValidateUtail;
import Exception.*;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentDaoImpl departmentDao;
    public DepartmentServiceImpl(){
        this.departmentDao = new DepartmentDaoImpl();
    }

    @Override
    public void update(DepartmentDto departmentDto) {
        try {
          Department department=DepartmentMapper.toEntity(departmentDto);
            ValidateUtail.validate(departmentDto);
            departmentDao.update(department, Constants.FieldConstraints.ID);
        }catch(InvalidDataFormatException e){
            throw new InvalidDataFormatException(e.getMessage());
        }
    }

    @Override
    public List<Department> getAllDepartment(){
        return departmentDao.selectAll();
    }
    @Override
    public Department getDepartmentById(int departmentId) {
        return this.departmentDao.selectById(new Department(departmentId));
    }
    @Override
    public void saveDepartment(DepartmentDto departmentDto) {
        Department department= DepartmentMapper.toEntity(departmentDto);
            ValidateUtail.validate(departmentDto);
            validateExistDepartment(department);
            this.departmentDao.insert(department);
    }
    @Override
    public Department findDepartmentByName(String name) {
        return departmentDao.findDepartmentByName(name);
    }

    @Override
    public void delete(DepartmentDto departmentDto) {
        Department department = DepartmentMapper.idToEntity(departmentDto);
        department = this.departmentDao.selectById(department);
        this.departmentDao.delete(department);

    }

    private void validateExistDepartment(Department department) {
        Department duplicateDepartment = this.departmentDao.findDepartmentByName(department.getDepartment());
        if (duplicateDepartment != null) {
            throw new InvalidDataFormatException(Constants.Alerts.DUPLICATE_RECORD + department.getDepartment());
        }
    }
    public List<Department> searchDepartmentByKeyword(String keyword) {
        return departmentDao.findDepartmentByKeyword(keyword);
    }



}
