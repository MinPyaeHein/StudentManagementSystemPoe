package Dto;

import annotation.NotNull;

import java.time.LocalDate;

public class SemesterDto {
    private String id;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "start date cannot be null")
    private LocalDate start_Date;
    @NotNull(message = "end date cannot be null")
    private LocalDate end_Date;
    @NotNull(message = "status cannot be null")
    private String status;

    public LocalDate getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(LocalDate end_Date) {
        this.end_Date = end_Date;
    }

    public LocalDate getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(LocalDate start_Date) {
        this.start_Date = start_Date;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }






    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
