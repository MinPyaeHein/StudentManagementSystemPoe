package Dto;

import Model.SemesterName;
import Model.SemesterStatus;
import annotation.NotNull;

import java.time.LocalDate;

public class SemesterDto {
    private String id;
    @NotNull(message = "Name cannot be null")
    private SemesterName name;
    @NotNull(message = "start date cannot be null")
    private LocalDate start_Date;
    @NotNull(message = "end date cannot be null")
    private LocalDate end_Date;

    @NotNull(message = "status cannot be null")
    private SemesterStatus status;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SemesterName getName() {
        return name;
    }

    public void setName(SemesterName name) {
        this.name = name;
    }

    public void setStatus(SemesterStatus status) {
        this.status = status;
    }
    public SemesterStatus getStatus() {
        return status;
    }

}
