//package com.example.demo.models;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "passports")
//public class Passport {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//    private String series;
//    private String number;
//    @OneToOne(optional = true, mappedBy = "passport")
//    private User owner;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getSeries() {
//        return series;
//    }
//
//    public void setSeries(String series) {
//        this.series = series;
//    }
//
//    public String getNumber() {
//        return number;
//    }
//
//    public void setNumber(String number) {
//        this.number = number;
//    }
//
//    public User getOwner() {
//        return owner;
//    }
//
//    public void setOwner(User owner) {
//        this.owner = owner;
//    }
//}
