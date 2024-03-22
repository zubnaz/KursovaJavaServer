package org.example.Inirializer;

import lombok.AllArgsConstructor;
import org.example.Constants.Roles;
import org.example.Entities.RoleEntity;
import org.example.Entities.UserEntity;
import org.example.Entities.UserRoleEntity;
import org.example.Repositories.RoleRepository;
import org.example.Repositories.UserRepository;
import org.example.Repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@AllArgsConstructor
public class DataInitializationConfig {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    public CommandLineRunner initData() {
        return args -> {
            seedRole();
            seedUser();
        };
    }

    private void seedRole() {
        if(roleRepository.count() == 0) {
            RoleEntity admin = RoleEntity
                    .builder()
                    .name(Roles.Admin)
                    .build();
            roleRepository.save(admin);
            RoleEntity user = RoleEntity
                    .builder()
                    .name(Roles.User)
                    .build();
            roleRepository.save(user);
        }
    }

    private void seedUser() {
        if(userRepository.count() == 0) {
            var user = UserEntity
                    .builder()
                    .email("admin@gmail.com")
                    .name("Микола")
                    .surname("Підкаблучник")
                    .phone("+380 97 67 56 464")
                    .password(passwordEncoder.encode("123456"))
                    .build();
            userRepository.save(user);
            var role = roleRepository.findByName(Roles.Admin);
            var ur = UserRoleEntity
                    .builder()
                    .role(role)
                    .user(user)
                    .build();
            userRoleRepository.save(ur);
        }
    }
}
