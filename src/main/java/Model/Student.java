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

        public Student(int id) {
            this.id=id;
        }

        public Student(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }
        public Student(int id, String name, String email,String address) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.address = address;
        }

        public Student( String name, String email, String address) {
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
        @Override
        public String toString() {
            return "Student [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + "]";
        }

    }
