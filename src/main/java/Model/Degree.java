package Model;


import annotation.Column;
import annotation.Id;
import annotation.NotNull;
import annotation.Table;

@Table(name="degree")
public class Degree {
    @NotNull(message = "Id can't be Null!!")
    @Id(name = "id")
    private int id;
    @NotNull(message = "Degree can't be Null!!")
    @Column(name="degree")
    private String degree;


    public Degree(int id){
        this.id = id;
    }

    public Degree(int id,String degree){
        this.id = id;
        this.degree = degree;
    }
    public Degree(String degree){
        this.degree = degree;
    }

    public int getId(){
        return id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }




}
