package Dto;

import java.time.LocalDate;

public class SemesterDto {
    private String id;
    private String name;
    private LocalDate start_Date;
    private LocalDate end_Date;
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
