package com.example.animalchipization.dto;

import com.example.animalchipization.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@JsonDeserialize(builder = AccountDto.Builder.class)
@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Schema(name = "Account", description = "User account entity")
public class AccountDto {

    @Setter
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "User account identifier",
            example = "1", minimum = "1")
    private Long id;
    @NotBlank
    @Schema(description = "User first name", requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1)
    private String firstName;
    @NotBlank
    @Schema(description = "User last name", requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1)
    private String lastName;
    @NotBlank
    @Email
    @Schema(description = "User email", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Schema(description = "User password", accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
    @Schema(description = "User role", defaultValue = "USER")
    private Role role;

    private AccountDto(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Role role;

        @JsonIgnore
        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

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
