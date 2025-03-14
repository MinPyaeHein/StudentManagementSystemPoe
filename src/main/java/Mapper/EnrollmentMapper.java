package Mapper;

import Dto.EnrollmentDto;
import Model.Enrollment;
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

        enrollment.setStudent_id(Integer.parseInt(enrollmentDto.getStudent_id()));
        enrollment.setCourse_id(courseService.findCourseByName(enrollmentDto.getCourse()));
        enrollment.setStatus(enrollmentDto.getStatus());

        return enrollment;
    }

}
