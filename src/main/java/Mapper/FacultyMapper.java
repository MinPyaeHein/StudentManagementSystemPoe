package Mapper;

import Dto.FacultyDto;
import Model.Faculty;

public class FacultyMapper {

    public static Faculty toEntity(FacultyDto facultyDto){
        if(facultyDto==null){
            return null;
        }

        Faculty faculty=new Faculty();
        if(facultyDto.getId()!=null){
            faculty.setId(Integer.parseInt(facultyDto.getId()));
        }
        faculty.setName(facultyDto.getName());
        faculty.setEmail(facultyDto.getEmail());
        faculty.setWebsite_link(facultyDto.getWebsite_link());
        faculty.setPhone(facultyDto.getPhone());
        return faculty;
    }
}
