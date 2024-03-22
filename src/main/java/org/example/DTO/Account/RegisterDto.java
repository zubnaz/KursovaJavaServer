package org.example.DTO.Account;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private String repeatPassword;
    private boolean isAdmin;
}
