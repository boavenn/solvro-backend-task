package io.github.boavenn.solvrobackendtask.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest
{
    @Mock private AppUserRepository appUserRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private AuthService authService;

    @Test
    public void shouldThrowExceptionWhenUsernameIsAlreadyTaken() {
        // given
        given(appUserRepository.existsByUsername(anyString())).willReturn(true);

        AppUser credentials = new AppUser();
        credentials.setUsername("taken_username");
        credentials.setPassword("password");

        // then
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> authService.registerUser(credentials))
                .withMessageContaining("username is already taken");
    }

    @Test
    public void shouldRegisterUserWhenCredentialsAreValid() {
        // given
        given(appUserRepository.save(any(AppUser.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(passwordEncoder.encode(any())).willAnswer(invocation -> invocation.getArgument(0) + "encoded");
        given(appUserRepository.existsByUsername(anyString())).willReturn(false);

        AppUser credentials = new AppUser();
        credentials.setUsername("username");
        credentials.setPassword("password");

        // when
        authService.registerUser(credentials);

        // then
        var userCaptor = ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository).save(userCaptor.capture());
        AppUser user = userCaptor.getValue();
        assertThat(user.getPassword()).isEqualTo("passwordencoded");
        assertThat(user.getUsername()).isEqualTo("username");
    }
}