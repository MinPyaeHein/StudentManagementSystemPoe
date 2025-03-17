package Dto;

import annotation.EmailValidate;
import annotation.NotNull;

public class FacultyDto {
    private String id;
    @NotNull(message="Name cannot be null!!")
    private String name;
    @NotNull(message="Email cannot be null!!")
    @EmailValidate(message="Email is not valid!!")
    private String email;
    @NotNull(message="Website Link cannot be null!!")
    private String website_link;
    @NotNull(message="Phone Number cannot be null!!")
    private String phone;

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

    public String getWebsite_link() {
        return website_link;
    }

    public void setWebsite_link(String website_link) {
        this.website_link = website_link;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



}
