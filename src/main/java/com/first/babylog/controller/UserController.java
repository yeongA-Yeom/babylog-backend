package com.first.babylog.controller;

import com.first.babylog.dto.UserCreateRequest;
import com.first.babylog.dto.UserResponse;
import com.first.babylog.dto.UserUpdateRequest;
import com.first.babylog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> signUp(@RequestBody UserCreateRequest request){
        try {
            userService.signUp(request);
            return ResponseEntity.ok().build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public List<UserResponse> findAll(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse findOne(@PathVariable String id){
        return userService.findByLoginId(id);
    }

    @PutMapping("/{id}")
    public void updateUser(
            @PathVariable String id,
            @RequestBody UserUpdateRequest request
            ){
                userService.updateUser(id, request);
            }

}
