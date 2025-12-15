package com.first.babylog;

import com.first.babylog.domain.User;
import com.first.babylog.dto.UserCreateRequest;
import com.first.babylog.repository.UserRepository;
import com.first.babylog.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataRunner {

    @Bean
    CommandLineRunner init(UserService userService) {
        return args -> {
            UserCreateRequest request = new UserCreateRequest();
            request.setEmail("test3@babylog.com");
            request.setName("테스트3");

            userService.createUser(request);

            System.out.println("Service를 통한 유저 저장 완료.");

            System.out.println(userService.findAll());
        };
    }
}
