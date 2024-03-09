package org.example.DTO.Account;

import lombok.Data;

@Data
public class RegisterDto {
    private String Name;
    private String Surname;
    private String email;
    private String phoneNumber;
    private String password;
    private String repeatPassword;
}
