package com.first.babylog.service;

import com.first.babylog.domain.User;
import com.first.babylog.dto.UserCreateRequest;
import com.first.babylog.dto.UserResponse;
import com.first.babylog.dto.UserUpdateRequest;
import com.first.babylog.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    //생성자 주입
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //유저 생성 로직
    public Long createUser(UserCreateRequest request){
        User user = new User(
                    request.getEmail(),
                    request.getPassword(),
                    request.getName()
        );
        return userRepository.save(user).getNumber();
    }

    public List<UserResponse> findAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getNumber(),
                        user.getEmail(),
                        user.getPassword(),
                        user.getName()

                ))
                .toList();
    }

    public UserResponse findById(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. Email = "+email));

        return new UserResponse(
                user.getNumber(),
                user.getEmail(),
                user.getPassword(),
                user.getName()
        );
    }

    @Transactional
    public void updateUser(String email, UserUpdateRequest request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다. email="+email));

        user.changeName(request.getName());
    }
}
