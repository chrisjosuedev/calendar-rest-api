package dev.chrisjosue.calendarapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Name is required.")
    @Length(min = 8, message = "Minimum Name Length must be greater than 8.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Length(min = 8, message = "Minimum Name Length must be greater than 8.")
    private String password;
}
