package io.github.boavenn.solvrobackendtask.config;

import io.github.boavenn.solvrobackendtask.city.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DataLoaderTest
{
    @Autowired private CityRepository cityRepository;

    @Test
    void shouldLoadInitialDataToContextCorrectly() {
        assertThat(cityRepository.findByName("solvro_city")).isNotEmpty();
    }
}