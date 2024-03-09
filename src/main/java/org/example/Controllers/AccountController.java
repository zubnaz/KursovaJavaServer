package org.example.Controllers;

import lombok.RequiredArgsConstructor;
import org.example.DTO.Account.AuthResponseDto;
import org.example.DTO.Account.LoginDto;
import org.example.DTO.Account.RegisterDto;
import org.example.Entities.RoleEntity;
import org.example.Entities.UserEntity;
import org.example.Entities.UserRoleEntity;
import org.example.Repositories.RoleRepository;
import org.example.Repositories.UserRepository;
import org.example.Repositories.UserRoleRepository;
import org.example.Services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    private final PasswordEncoder passwordEncoder;
    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto dto) {
        try {
            var auth = service.login(dto);
            return ResponseEntity.ok(auth);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("register")
    public ResponseEntity<UserEntity> register(@RequestBody RegisterDto dto) {
        try {
            var result = service.register(dto);
            return ResponseEntity.ok(result);
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
