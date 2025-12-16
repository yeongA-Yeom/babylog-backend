package com.first.babylog.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Number; // 자동 생성 값

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String name;



    private LocalDateTime createdAt;

    protected User(){}

    public User(String email,String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }

    public Long getNumber(){
        return Number;
    }

    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    public String getName(){
        return name;
    }

    public void changeName(String name){
        this.name=name;
    }
}


