package Model;


import annotation.Column;
import annotation.Id;
import annotation.Table;

@Table(name = "teachers")
public class Teacher {
    @Id(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;

    public Teacher(int id){
        this.id = id;
    }
    public Teacher(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public Teacher(int id, String name, String email,String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public Teacher(String name, String email,String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
