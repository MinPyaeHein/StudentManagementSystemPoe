package Mapper;

import Dto.SemesterDto;
import Model.Semester;
import Model.SemesterName;
import Model.SemesterStatus;

public class SemesterMapper {

    public static Semester toEntity(SemesterDto semesterDto){
        if(semesterDto == null){
            return null;
        }
        Semester semester = new Semester();
        if(semesterDto.getId()!=null){
            semester.setId(Integer.parseInt(semesterDto.getId()));
        }
        semester.setName(SemesterName.valueOf(semesterDto.getName()));
        semester.setStart_Date(semesterDto.getStart_Date());
        semester.setEnd_Date(semesterDto.getEnd_Date());
        semester.setStatus(SemesterStatus.valueOf(semesterDto.getStatus()));
        return semester;
    }
}
