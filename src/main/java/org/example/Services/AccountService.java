package org.example.Services;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.example.Configuration.Security.JwtService;
import org.example.Constants.Roles;
import org.example.DTO.Account.AuthResponseDto;
import org.example.DTO.Account.LoginDto;
import org.example.DTO.Account.RegisterDto;
import org.example.Entities.RoleEntity;
import org.example.Entities.UserEntity;
import org.example.Entities.UserRoleEntity;
import org.example.Repositories.RoleRepository;
import org.example.Repositories.UserRepository;
import org.example.Repositories.UserRoleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

    public AuthResponseDto login(LoginDto request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var isValid = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!isValid) {
            throw new UsernameNotFoundException("User not found");
        }
        var jwtToken = jwtService.generateAccessToken(user);
        return AuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }
    public UserEntity register(RegisterDto dto) throws Exception {
        if(!dto.getPassword().equals(dto.getRepeatPassword()))throw new Exception("Не ідентичні паролі");
        UserEntity newUser = UserEntity.builder().
                name(dto.getName()).
                surname(dto.getSurname()).
                email(dto.getEmail()).
                phone(dto.getPhone()).
                password(passwordEncoder.encode(dto.getPassword())).
                build();
        userRepository.save(newUser);

        RoleEntity role = roleRepository.findByName(dto.isAdmin()?Roles.Admin:Roles.User);

        var ur = UserRoleEntity
                .builder()
                .role(role)
                .user(newUser)
                .build();

        userRoleRepository.save(ur);
        return newUser;
    }
}
