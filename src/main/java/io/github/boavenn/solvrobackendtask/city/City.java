package io.github.boavenn.solvrobackendtask.city;

import io.github.boavenn.solvrobackendtask.citygraph.CityGraph;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class City
{
    private String name;
    private CityGraph cityGraph;
}
