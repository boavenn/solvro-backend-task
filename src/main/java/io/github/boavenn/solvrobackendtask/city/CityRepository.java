package io.github.boavenn.solvrobackendtask.city;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CityRepository
{
    private Map<String, City> cities = new HashMap<>();

    public void save(City city) {
        cities.putIfAbsent(city.getName(), city);
    }

    public Optional<City> findByName(String name) {
        return Optional.ofNullable(cities.get(name));
    }
}
