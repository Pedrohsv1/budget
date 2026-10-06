package com.pedro.budget.seed;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pedro.budget.dto.SignUpRequest;
import com.pedro.budget.entity.User;
import com.pedro.budget.entity.UserRole;
import com.pedro.budget.mapper.SignMapper;
import com.pedro.budget.repository.UserRepository;

@Component
public class AdminSeed implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${api.admin.password}")
    private String password;

    @Value("${api.admin.username}")
    private String username;

    UserRepository userRepository;
    SignMapper signMapper;

    public AdminSeed(UserRepository userRepository, SignMapper signMapper) {
        this.userRepository = userRepository;
        this.signMapper = signMapper;
    }

    private void createAdministrator() {
        SignUpRequest request = new SignUpRequest("ADMIN", username, password);

        String hashedPassword = new BCryptPasswordEncoder().encode(password);

        userRepository.save(new User(request.getUsername(), hashedPassword, UserRole.ADMIN));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.createAdministrator();
    }
}
