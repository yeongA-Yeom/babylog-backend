package com.first.babylog.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private int age;

    public  UserResponse(Long id, String name,String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
