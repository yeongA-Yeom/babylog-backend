package com.first.babylog.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponse {
    private Long number;
    private String email;
    private String password;
    private String name;
    private int age;

    public  UserResponse(Long number,String email, String password, String name){
        this.number = number;
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
