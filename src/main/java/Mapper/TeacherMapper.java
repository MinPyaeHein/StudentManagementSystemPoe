package Mapper;

import Dto.TeacherDto;
import Model.Gender;
import Model.Teacher;
import Service.impl.DegreeServiceImpl;
import Service.impl.DepartmentServiceImpl;

public class TeacherMapper {
    private static DepartmentServiceImpl departmentService = new DepartmentServiceImpl();
    private static DegreeServiceImpl degreeService=new DegreeServiceImpl();


    public static Teacher toEntity(TeacherDto teacherDto) {
        if (teacherDto == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        if (teacherDto.getId() != null) {
            teacher.setId(Integer.parseInt(teacherDto.getId()));
        }

        teacher.setName(teacherDto.getName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setAddress(teacherDto.getAddress());
        teacher.setPhone(teacherDto.getPhone());
        teacher.setDegree(degreeService.findDegreeByName(teacherDto.getDegree()));
        teacher.setDepartment(departmentService.findDepartmentByName(teacherDto.getDepartment()));
        teacher.setGender(Gender.valueOf(teacherDto.getGender()));

        return teacher;
    }
    }



