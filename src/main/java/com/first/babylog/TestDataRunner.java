package com.first.babylog;

import com.first.babylog.domain.User;
import com.first.babylog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestDataRunner {

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            User user = new User("test@babylog.com", "테스트유저");
            userRepository.save(user);

            System.out.println("유저 저장 완료!");
        };
    }
}
