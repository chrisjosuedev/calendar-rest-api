package dev.chrisjosue.calendarapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Pattern(regexp = "[A-Za-z ]*", message = "Numbers and Special Characters not allowed in Name Field.")
    @NotBlank(message = "Name Field is required.")
    @Length(min = 8, message = "Minimum Name Field Length must be greater than 8.")
    private String name;

    @NotBlank(message = "Email Field is required.")
    @Email(message = "Email Field is invalid.")
    private String email;

    @NotBlank(message = "Password Field is required.")
    @Length(min = 8, message = "Minimum Name Field Length must be greater than 8.")
    private String password;
}
