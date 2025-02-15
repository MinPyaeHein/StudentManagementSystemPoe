package Service.impl;

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
        Department department=DepartmentMapper.toEntity(departmentDto);
        try {
            ValidateUtail.validate(department);
            departmentDao.update(department, "id");
            AlertUtil.alert("Successfully updated","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
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
        try{
            ValidateUtail.validate(department);
            validateExistDepartment(department);
            this.departmentDao.insert(department);
            AlertUtil.alert("Successfully Saved!!","INFORMATION");
        }catch(InvalidDataFormatException exception){
            AlertUtil.alert(exception.getMessage(),"ERROR");
        }
    }
    @Override
    public Department findDepartmentByName(String name) {
        return departmentDao.findDepartmentByName(name);
    }

    @Override
    public void delete(DepartmentDto departmentDto) {
        Department department = DepartmentMapper.idToEntity(departmentDto);
        department = this.departmentDao.selectById(department);
        if(department!=null&& AlertUtil.confirmationDialog("Delete Confirmation","Are you sure  to Delete department?\n"+department.getDepartment())){
            this.departmentDao.delete(department);
        }
    }

    private void validateExistDepartment(Department department) {
        System.out.println("Arrived to validate duplicate:" + department);
        Department duplicateDepartment = this.departmentDao.findDepartmentByName(department.getDepartment());
        if (duplicateDepartment != null) {
            throw new InvalidDataFormatException("Duplicate dep found!!! " + department.getDepartment());
        }
    }
    public List<Department> searchDepartmentByKeyword(String keyword) {
        return departmentDao.findDepartmentByKeyword(keyword);
    }



}
