package Dto;

import Model.Gender;
import annotation.EmailValidate;
import annotation.NotNull;
import annotation.PhoneValidate;

import java.io.File;

public class TeacherDto {

    private String id;
    @NotNull(message="Name cannot be null!!")
    private  String name;
    @NotNull(message="Email cannot be null!!")
    @EmailValidate(message="Email is not valid!!")
    private String email;
    @NotNull(message="Address cannot be null!!")
    private String address;
    @NotNull(message="Phone Number cannot be null!!")
    @PhoneValidate(message = "Phone number is not valid!!")
    private String phone;
    @NotNull(message = "Degree cannot be null!!")
    private String degree;
    @NotNull(message = "Department cannot be null!!")
    private String department;
    @NotNull(message="Gender cannot be null!!")
    private Gender gender;
    private File imageFile;

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }


    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
