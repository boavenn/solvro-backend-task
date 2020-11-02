package io.github.boavenn.solvrobackendtask.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
public class AppUser implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appuser_generator")
    @SequenceGenerator(name = "appuser_generator", sequenceName = "appuser_seq")
    private Long id;

    @NotNull(message = "Username cannot be blank")
    @Length(min = 3, max = 20, message = "Username length must be between {min} and {max} characters long")
    private String username;

    @NotNull(message = "Password cannot be blank")
    @Length(min = 8, max = 70, message = "Password length must be between {min} and {max} characters long")
    private String password;

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    public User toUser() {
        return new User(username, password, Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return id != null && Objects.equals(id, appUser.id);
    }

    @Override
    public int hashCode() {
        return 43;
    }
}
