package Model;

import annotation.Column;
import annotation.Id;
import annotation.NotNull;
import annotation.Table;

@Table(name = "departments")
public class Department {
    @NotNull(message = "Id can't be null!!")
    @Id(name="id")
    private int id;
    @NotNull(message = "Department can't be null!!")
    @Column(name="department")
    private String department;

    public Department(int id){
        this.id = id;
    }

    public Department(int id,String department){
        this.id = id;
        this.department = department;
    }
    public Department(String department){
        this.department = department;
    }

    public int getId(){
        return id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
