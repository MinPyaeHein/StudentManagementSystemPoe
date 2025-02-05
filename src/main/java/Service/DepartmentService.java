package Service;

import Model.Department;

import java.util.List;

public interface DepartmentService {
    public void update(Department department);
    public List<Department> getAllDepartment();
    public Department getDepartmentById(int departmentId);
    public void saveDepartment(Department department);
    public Department findDepartmentByName(String name);
    public void delete(int id);
    public List<Department> searchDepartmentByKeyword(String keyword);
}
