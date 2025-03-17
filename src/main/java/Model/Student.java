package Model;

import annotation.*;

@Table(name="students")
    public class Student {
        @Id(name="id")
        private int id;
        @Column(name="name")
        private String name;
        @Column(name="email")
        private String email;
        @Column(name="address")
        private String address;
        @Column(name = "phone")
        private String phone;
        @ManyToOne(name = "faculty_id")
        private Faculty faculty;
        @Column(name = "gender")
        private Gender gender;
        @Column(name = "password")
        private String password;
        public Student() {
        }
        public Student(int id) {
            this.id=id;
        }

        public Student(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

    public Student( String name, String email,String address,String phone,Faculty faculty,Gender gender,String password) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
        this.gender = gender;
        this.password =password;
    }

    public Student(int id, String name, String email, String address, String phone, Faculty faculty,Gender gender,String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.faculty = faculty;
        this.gender = gender;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                return "Student [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address +  ", phone number=" + phone + "Password=" + password +"]";
            }


}
