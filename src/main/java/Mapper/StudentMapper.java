package Mapper;

import Dto.StudentDto;
import Model.Gender;
import Model.Student;
import Service.impl.FacultyServiceImpl;
import Exception.InvalidDataFormatException;

public class StudentMapper {
    private static FacultyServiceImpl facultyService=new FacultyServiceImpl();
    public static Student toEntity(StudentDto studentDto){
        if(studentDto==null){
            return null;
        }
        Student student=new Student();
        if(studentDto.getId()!=null){
            student.setId(Integer.parseInt(studentDto.getId()));
        }
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setAddress(studentDto.getAddress());
        student.setPhone(studentDto.getPhone());
        student.setFaculty(facultyService.findFacultyByName(studentDto.getFaculty()));
        student.setGender(Gender.valueOf(studentDto.getGender()));

        return student;
    }
    public static Student idToEntity(StudentDto studentDto){
        Student student=new Student();
        if(studentDto.getId()!=null){
            student.setId(Integer.parseInt(studentDto.getId()));
        }
        return student;
    }
}
