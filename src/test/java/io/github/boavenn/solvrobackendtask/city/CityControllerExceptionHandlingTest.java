package io.github.boavenn.solvrobackendtask.city;

import io.github.boavenn.solvrobackendtask.citygraph.CityGraph;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@WithMockUser
@AutoConfigureMockMvc
public class CityControllerExceptionHandlingTest
{
    @Autowired private MockMvc mvc;
    @MockBean private CityRepository cityRepository;

    @Test
    public void shouldReturnCorrectResponseWhenCityWithGivenNameDoesNotExist() throws Exception {
        // given
        given(cityRepository.findByName(CityController.DEFAULT_CITY)).willReturn(Optional.empty());

        // when
        var request = get("/path?source=x&target=y");

        // then
        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(".status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath(".error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath(".path").value("/path"))
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    assertThat(body.toLowerCase()).contains("cannot find city with name");
                });
    }

    @Test
    public void shouldReturnCorrectResponseWhenStopWithGivenNameDoesNotExist() throws Exception {
        // given
        given(cityRepository.findByName(CityController.DEFAULT_CITY))
                .willReturn(Optional.of(new City(CityController.DEFAULT_CITY, new CityGraph())));

        // when
        var request = get("/path?source=x&target=y");

        // then
        mvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(".status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath(".error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath(".path").value("/path"))
                .andExpect(result -> {
                    String body = result.getResponse().getContentAsString();
                    assertThat(body.toLowerCase()).contains("cannot find stop with name");
                });
    }
}
