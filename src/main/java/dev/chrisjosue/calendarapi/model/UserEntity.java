package dev.chrisjosue.calendarapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    private String id;

    @NotBlank(message = "Name is required.")
    @Length(min = 8, message = "Name must be greater than 8.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Length(min = 8, message = "Password must be greater than 8.")
    private String password;
}
