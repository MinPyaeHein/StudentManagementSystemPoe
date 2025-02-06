package Model;

import annotation.*;

@Table(name = "faculties")
public class Faculty {
    @Id(name="id")
    private int id;
    @NotNull(message="Name cannot be null!!")
    @Column(name="name")
    private String name;

    @NotNull(message="Email cannot be null!!")
    @EmailValidate(message="Email is not valid!!")
    @Column(name="email")
    private String email;

    @NotNull(message="Website Link cannot be null!!")
    @Column(name="website_link")
    private String website_link;

    @NotNull(message="Phone Number cannot be null!!")
    @Column(name="phone")
    private String phone;

    public Faculty( String name, String email, String website_link, String phone) {
        this.name = name;
        this.email = email;
        this.website_link = website_link;
        this.phone = phone;
    }

    public Faculty(int id, String name, String email, String website_link, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.website_link = website_link;
        this.phone = phone;
    }
    public Faculty(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", website_link='" + website_link + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
