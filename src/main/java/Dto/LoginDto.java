package Dto;

import annotation.NotNull;

public class LoginDto {
    @NotNull(message = "student gmail can't be null")
    private String gmail;
    @NotNull(message = "student password can't be null")
    private String password;

    public LoginDto(String gmail, String password) {
        this.gmail = gmail;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
