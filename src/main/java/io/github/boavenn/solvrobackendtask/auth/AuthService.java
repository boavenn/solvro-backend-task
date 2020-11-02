package io.github.boavenn.solvrobackendtask.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService
{
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(AppUser credentials) {
        String username = credentials.getUsername();
        if (appUserRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("This username is already taken");
        }

        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(credentials.getPassword()));
        appUserRepository.save(user);
    }
}
