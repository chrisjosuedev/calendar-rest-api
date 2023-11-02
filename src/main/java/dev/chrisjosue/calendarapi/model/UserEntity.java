package dev.chrisjosue.calendarapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity implements UserDetails {
    @Id
    private String id;

    @NotBlank(message = "Name is required.")
    @Length(min = 8, message = "Name must be greater than 8.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Email is invalid.")
    private String email;

    @JsonIgnore
    @NotBlank(message = "Password is required.")
    @Length(min = 8, message = "Password must be greater than 8.")
    private String password;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
