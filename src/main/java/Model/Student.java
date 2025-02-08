package Model;

import annotation.*;

@Table(name="students")
    public class Student {
        @Id(name="id")
        private int id;
        @NotNull(message="Name cannot be null!!")
        @Column(name="name")
        private String name;

        @NotNull(message="Email cannot be null!!")
        @EmailValidate(message="Email is not valid!!")
        @Column(name="email")
        private String email;

        @NotNull(message="Address cannot be null!!")
        @Column(name="address")
        private String address;

        @NotNull(message="Phone Number cannot be null!!")
        @PhoneValidate(message = "Phone number is not valid!!")
        @Column(name = "phone")
        private String phone;

        @NotNull(message="Faculty cannot be null!")
        @ManyToOne(name = "faculty_id")
        private Faculty faculty;

        @NotNull(message="Gender cannot be null!!")
        @Column(name = "gender")
        private Gender gender;

        public Student(int id) {
            this.id=id;
        }

        public Student(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        public Student(int id, String name, String email,String address,String phone) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.address = address;
            this.phone = phone;
        }

        public Student( String name, String email,String address,String phone) {
            this.name = name;
            this.email = email;
            this.address = address;
            this.phone = phone;
        }
    public Student( String name, String email,String address,String phone,Faculty faculty,Gender gender) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
        this.gender = gender;
    }

    public Student(int id, String name, String email, String address, String phone, Faculty faculty) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
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
        public String getPhone() {
            return phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public Faculty getFaculty() {
            return faculty;
        }
        public void setFaculty(Faculty faculty) {
            this.faculty = faculty;
        }
        public Gender getGender() {
            return gender;
        }
        public void setGender(Gender gender) {
            this.gender = gender;
        }
        @Override
            public String toString() {
                return "Student [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address +  ", phone number=" + phone + "]";
            }


}
