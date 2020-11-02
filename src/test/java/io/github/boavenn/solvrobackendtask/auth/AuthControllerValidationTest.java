package io.github.boavenn.solvrobackendtask.auth;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerValidationTest
{
    @Autowired private MockMvc mvc;
    @MockBean private AuthService authService;

    @Test
    public void signupShouldThrowExceptionWhenUsernameIsNull() throws Exception {
        // when
        JSONObject json = new JSONObject();
        json.put("password", "password");

        var request = post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString());

        // then
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThrowsMethodArgumentInvalidException(result, "Username cannot be"));
    }

    @Test
    public void signupShouldThrowExceptionWhenUsernameIsInvalid() throws Exception {
        // when
        JSONObject json = new JSONObject();
        json.put("username", "     x     ");
        json.put("password", "password");

        var request = post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString());

        // then
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThrowsMethodArgumentInvalidException(result, "Username length must be"));
    }

    @Test
    public void signupShouldThrowExceptionWhenPasswordIsNull() throws Exception {
        // when
        JSONObject json = new JSONObject();
        json.put("username", "username");

        var request = post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString());

        // then
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThrowsMethodArgumentInvalidException(result, "Password cannot be"));
    }

    @Test
    public void signupShouldThrowExceptionWhenPasswordIsInvalid() throws Exception {
        // when
        JSONObject json = new JSONObject();
        json.put("username", "username");
        json.put("password", "     x     ");

        var request = post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString());

        // then
        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThrowsMethodArgumentInvalidException(result, "Password length must be"));
    }

    @Test
    public void signupShouldReturnOkStatusWhenCredentialsAreValid() throws Exception {
        // given
        willDoNothing().given(authService).registerUser(any(AppUser.class));

        // when
        JSONObject json = new JSONObject();
        json.put("username", "username");
        json.put("password", "password");

        var request = post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString());

        // then
        mvc.perform(request)
                .andExpect(status().isOk());

        verify(authService, times(1)).registerUser(any(AppUser.class));
    }

    private void assertThrowsMethodArgumentInvalidException(MvcResult result, String message) {
        Exception exception = result.getResolvedException();
        assertThat(exception).isNotNull();
        assertThat(exception).isInstanceOf(MethodArgumentNotValidException.class);
        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assertThat(fieldError).isNotNull();
        assertThat(fieldError.getDefaultMessage()).contains(message);
    }
}
