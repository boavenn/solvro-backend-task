package io.github.boavenn.solvrobackendtask.config;

import io.github.boavenn.solvrobackendtask.city.City;
import io.github.boavenn.solvrobackendtask.city.CityRepository;
import io.github.boavenn.solvrobackendtask.citygraph.CityGraphDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataLoader implements ApplicationRunner
{
    private final CityGraphDeserializer deserializer;
    private final CityRepository cityRepository;
    @Value("classpath:solvro_city.json")
    private Resource serializedGraph;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        deserializer.deserialize(serializedGraph.getFile().getPath()).ifPresent(graph -> {
            City solvroCity = new City("solvro_city", graph);
            cityRepository.save(solvroCity);
        });
    }
}
