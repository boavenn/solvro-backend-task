package io.github.boavenn.solvrobackendtask.security;

import io.github.boavenn.solvrobackendtask.auth.AppUser;
import io.github.boavenn.solvrobackendtask.auth.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .map(AppUser::toUser)
                .orElseThrow(() -> new UsernameNotFoundException("User with name=" + username + " does not exist"));
    }
}
