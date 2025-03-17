package Dto;

import annotation.NotNull;

public class DegreeDto {
    private String id;
    @NotNull(message = "Degree can't be Null!!")
    private String degree;

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



}
