package Mapper;

import Dto.EnrollmentDto;
import Model.Enrollment;
import Model.Semester;
import Model.Student;
import Service.impl.CourseServiceImpl;

public class EnrollmentMapper {
   private static CourseServiceImpl courseService=new CourseServiceImpl();
    public static Enrollment toEntity(EnrollmentDto enrollmentDto) {
        if (enrollmentDto == null) {
            return null;
        }
        Enrollment enrollment = new Enrollment();
        if (enrollmentDto.getId() != null) {
            enrollment.setId(Integer.parseInt(enrollmentDto.getId()));
        }

        enrollment.setStudent(new Student(Integer.parseInt(enrollmentDto.getStudentId())));
        enrollment.setCourse(courseService.findCourseByName(enrollmentDto.getCourse()));
        enrollment.setSemester(new Semester(Integer.parseInt(enrollmentDto.getSemesterId())));
        enrollment.setStatus(enrollmentDto.getStatus());

        return enrollment;
    }

}
