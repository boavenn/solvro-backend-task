package io.github.boavenn.solvrobackendtask.city.dto;

import io.github.boavenn.solvrobackendtask.citygraph.Stop;
import lombok.Getter;

@Getter
public class StopDTO
{
    private String name;

    public static StopDTO from(Stop stop) {
        return new StopDTO(stop);
    }

    private StopDTO(Stop stop) {
        name = stop.getName();
    }
}
