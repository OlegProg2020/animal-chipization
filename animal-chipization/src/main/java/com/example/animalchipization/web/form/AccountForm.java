package com.example.animalchipization.web.form;


import com.example.animalchipization.model.Account;
import com.example.animalchipization.validation.annotations.UniqueAccountEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    public Account toAccount(PasswordEncoder passwordEncoder) {
        return new Account(firstName, lastName, email, passwordEncoder.encode(password));
    }
}
