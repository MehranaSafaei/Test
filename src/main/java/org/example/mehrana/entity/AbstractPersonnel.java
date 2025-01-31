package org.example.mehrana.entity;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public class AbstractPersonnel {
    private Long id;
    private String username;
    private String password;
    private Long nationalCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Username",nullable = false,updatable = true, unique = true)
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "Password",nullable = false,updatable = true, unique = true)
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

    @Override
    public String toString() {
        return "AbstractPersonnel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nationalCode=" + nationalCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPersonnel that = (AbstractPersonnel) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(password, that.password ) && Objects.equals(nationalCode, that.nationalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, nationalCode);
    }
}
