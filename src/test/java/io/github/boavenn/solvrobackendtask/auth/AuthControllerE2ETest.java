package io.github.boavenn.solvrobackendtask.auth;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class AuthControllerE2ETest
{
    @Autowired private MockMvc mvc;
    @Autowired private AppUserRepository appUserRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Test
    public void signupShouldRegisterNewUserIfCredentialsAreValid() throws Exception {
        // given
        JSONObject json = new JSONObject();
        json.put("username", "username");
        json.put("password", "password");

        // when
        var request = post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString());

        // then
        mvc.perform(request)
                .andExpect(status().isOk());

        AppUser user = appUserRepository.findByUsername("username").orElse(null);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(passwordEncoder.matches("password", user.getPassword())).isTrue();
    }
}
