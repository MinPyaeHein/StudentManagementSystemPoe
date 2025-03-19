package Model;


import annotation.Column;
import annotation.Id;
import annotation.NotNull;
import annotation.Table;

@Table(name="degree")
public class Degree {
    @Id(name = "id")
    private int id;
    @Column(name="degree")
    private String degree;

    public Degree(){

    }
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

    public void setId(int id){
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }




}
