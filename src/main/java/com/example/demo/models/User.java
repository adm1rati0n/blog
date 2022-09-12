package com.example.demo.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле не может быть пустым")
    @Size(min = 2, max = 255, message = "Размер данного поля должен быть в диапазоне от 2 до 255")
    @NotBlank
    private String surname, name, middlename;
    @NotEmpty(message = "Поле не может быть пустым")
    @Past(message = "Дата должна быть меньше текущей")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    @Positive
    private Integer followers;

    public User() {
    }

    public User(String surname, String name, String middlename, Date dateOfBirth, Integer followers){
        this.surname = surname;
        this.name = name;
        this.middlename = middlename;
        this.dateOfBirth = dateOfBirth;
        this.followers = followers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getDateOfBirth() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        return sdfDate.format(dateOfBirth);
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }
}
