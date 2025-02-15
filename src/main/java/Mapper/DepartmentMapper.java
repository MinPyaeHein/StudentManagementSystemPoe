package Mapper;

import Dto.DepartmentDto;
import Model.Department;

public class DepartmentMapper {

    public static Department toEntity(DepartmentDto departmentDto){
        if(departmentDto==null){
            return null;
        }
        Department department=new Department();
        if(departmentDto.getId()!=null){
            department.setId(Integer.parseInt(departmentDto.getId()));

        }
        department.setDepartment(departmentDto.getDepartment());
        return department;
    }

    public static Department idToEntity(DepartmentDto departmentDto){
        Department department=new Department();
        if(departmentDto.getId()!=null){
            department.setId(Integer.parseInt(departmentDto.getId()));

        }
        return department;
    }
}
