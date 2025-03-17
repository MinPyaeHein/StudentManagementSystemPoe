package Dto;

import annotation.EmailValidate;
import annotation.NotNull;
import annotation.PhoneValidate;

import java.io.File;

public class StudentDto {
    private String id;
    @NotNull(message="Name cannot be null!!")
    private String name;
    @NotNull(message="Email cannot be null!!")
    @EmailValidate(message="Email is not valid!!")
    private String email;
    @NotNull(message="Address cannot be null!!")
    private String address;
    @NotNull(message="gender cannot be null!!")
    private String gender;
    @NotNull(message="Phone Number cannot be null!!")
    @PhoneValidate(message = "Phone number is not valid!!")
    private String phone;
    @NotNull(message="Faculty cannot be null!")
    private String faculty;
    private File imageFile;
    @NotNull(message = "Password cannot be null!")
    private String password;

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
