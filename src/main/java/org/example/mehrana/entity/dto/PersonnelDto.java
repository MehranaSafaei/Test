package org.example.mehrana.entity.dto;

public class PersonnelDto {

    private long id;
    private String username;
    private String password;
    private Long nationalCode;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Long getNationalCode() {
        return nationalCode;
    }
    public void setNationalCode(Long nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {this.id = id;}
}
