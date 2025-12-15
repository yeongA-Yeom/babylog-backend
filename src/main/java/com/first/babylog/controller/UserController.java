package com.first.babylog.controller;

import com.first.babylog.domain.User;
import com.first.babylog.dto.UserCreateRequest;
import com.first.babylog.dto.UserResponse;
import com.first.babylog.dto.UserUpdateRequest;
import com.first.babylog.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // API 전용 컨트롤러->반환값을 JSON으로 자동 변환
@RequestMapping("/users")// 공통 URL
public class UserController {

    private final UserService userService;

    //생성자 주입
    public UserController(UserService userService){

        this.userService = userService;
    }

    //유저 생성 API
    @PostMapping
    public Long createUser(@RequestBody UserCreateRequest request)
    {
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findOne(@PathVariable Long id){
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public void updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
            ){
                userService.updateUser(id, request);
            }

}
