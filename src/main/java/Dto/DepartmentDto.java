package Dto;

import annotation.NotNull;

public class DepartmentDto {
    private String id;
    @NotNull(message = "Department can't be null!!")
    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




}
