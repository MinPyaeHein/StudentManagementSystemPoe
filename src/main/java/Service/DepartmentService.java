package Service;

import Dto.DepartmentDto;
import Model.Department;

import java.util.List;

public interface DepartmentService {
    public void update(DepartmentDto departmentDto);
    public List<Department> getAllDepartment();
    public Department getDepartmentById(int departmentId);
    public void saveDepartment(DepartmentDto departmentDto);
    public Department findDepartmentByName(String name);
    public void delete(DepartmentDto departmentDto);
    public List<Department> searchDepartmentByKeyword(String keyword);
}
