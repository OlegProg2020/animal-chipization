package com.example.animalchipization.web.dto;

import com.example.animalchipization.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonDeserialize(builder = AccountDto.Builder.class)
@Getter
@NoArgsConstructor
public class AccountDto {

    @Setter
    @Min(1)
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @JsonIgnore
    @NotBlank
    private String password;
    private Role role;

    public static Builder builder() {
        return new Builder();
    }

    private AccountDto(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Role role;

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public AccountDto build() {
            return new AccountDto(this);
        }
    }

}
