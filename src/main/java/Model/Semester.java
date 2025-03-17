package Model;

import annotation.Column;
import annotation.Id;
import annotation.NotNull;
import annotation.Table;

import java.time.LocalDate;

@Table(name = "semesters")
public class Semester {
    @Id(name = "id")
    private int id;
    @Column(name = "name")
    private SemesterName name;
    @Column(name = "start_date")
    private LocalDate start_Date;
    @Column(name = "end_date")
    private LocalDate end_Date;
    @Column(name = "status")
    private SemesterStatus status;

    public Semester(int id) {
     this.id = id;
    }
    public Semester() {
    }

    public Semester(int id, SemesterName name, LocalDate start_Date, LocalDate end_Date, SemesterStatus status) {
        this.id = id;
        this.name = name;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.status = status;
    }
    public Semester( SemesterName name, LocalDate start_Date, LocalDate end_Date, SemesterStatus status) {
        this.name = name;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.status = status;
    }


    public SemesterStatus getStatus() {
        return status;
    }

    public void setStatus(SemesterStatus status) {
        this.status = status;
    }

    public SemesterName getName() {
        return name;
    }

    public void setName(SemesterName name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart_Date() {
        return start_Date;
    }
    public void setEnd_Date(LocalDate end_Date) {
        this.end_Date = end_Date;
    }

    public void setStart_Date(LocalDate start_Date) {
        this.start_Date = start_Date;
    }

    public LocalDate getEnd_Date() {
        return end_Date;
    }

}
