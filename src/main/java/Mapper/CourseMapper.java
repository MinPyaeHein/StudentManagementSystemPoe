package Mapper;

import Dto.CourseDto;
import Model.Course;
import Service.impl.DepartmentServiceImpl;
import Service.impl.TeacherServiceImpl;

public class CourseMapper {
    private static DepartmentServiceImpl departmentService=new DepartmentServiceImpl();
    private static TeacherServiceImpl teacherService=new TeacherServiceImpl();
    public static Course toEntity(CourseDto courseDto){
        if(courseDto==null){
            return null;
        }
        Course course=new Course();
        if(courseDto.getId()!=null){
            course.setId(Integer.parseInt(courseDto.getId()));
        }
        course.setCourse_name(courseDto.getCourse_name());
        course.setCourse_code(courseDto.getCourse_code());
        course.setDescription(courseDto.getDescription());
        course.setCredits(courseDto.getCredits().isEmpty() ? 0 : Integer.parseInt(courseDto.getCredits()));
        course.setCapacity(courseDto.getCapacity().isEmpty() ? 0 : Integer.parseInt(courseDto.getCapacity()));
        course.setDepartment(departmentService.findDepartmentByName(courseDto.getDepartment()));
        course.setTeacher(teacherService.findTeacherByName(courseDto.getTeacher()));
        course.setCreated_at(courseDto.getCreated_at());

        return course;

    }
}
