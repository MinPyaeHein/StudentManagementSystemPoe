package Mapper;

import Dto.DegreeDto;
import Model.Degree;

public class DegreeMapper {

    public static Degree toEntity(DegreeDto degreeDto){
        if(degreeDto==null){
            return null;
        }

        Degree degree=new Degree();
        if(degreeDto.getId()!=null){
            degree.setId(Integer.parseInt(degreeDto.getId()));
        }
        degree.setDegree(degreeDto.getDegree());
        return degree;
    }

    public static Degree idToEntity(DegreeDto degreeDto){
        Degree degree=new Degree();
        if(degreeDto.getId()!=null){
            degree.setId(Integer.parseInt(degreeDto.getId()));
        }
        return degree;
    }
}
