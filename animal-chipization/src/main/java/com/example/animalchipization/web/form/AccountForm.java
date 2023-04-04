package com.example.animalchipization.web.form;


import com.example.animalchipization.model.Account;
import com.example.animalchipization.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountForm {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    private Role role = Role.USER;

    public Account toAccount() {
        return new Account(firstName, lastName, email, password);
    }
}
