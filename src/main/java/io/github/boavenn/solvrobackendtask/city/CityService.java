package io.github.boavenn.solvrobackendtask.city;

import io.github.boavenn.solvrobackendtask.city.dto.PathInfoDTO;
import io.github.boavenn.solvrobackendtask.city.dto.StopDTO;
import io.github.boavenn.solvrobackendtask.city.exception.CityNotFoundException;
import io.github.boavenn.solvrobackendtask.city.exception.StopNotFoundException;
import io.github.boavenn.solvrobackendtask.citygraph.CityGraph;
import io.github.boavenn.solvrobackendtask.citygraph.Link;
import io.github.boavenn.solvrobackendtask.citygraph.Stop;
import io.github.boavenn.solvrobackendtask.utils.Dijkstra;
import io.github.boavenn.solvrobackendtask.utils.GraphUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService
{
    private final CityRepository cityRepository;

    public List<StopDTO> getStops(String cityName) {
        List<Stop> stops = cityRepository.findByName(cityName)
                .map(city -> city.getCityGraph().stops())
                .orElse(Collections.emptyList());

        return stops.stream().map(StopDTO::from).collect(Collectors.toList());
    }

    public PathInfoDTO getPath(String cityName, String sourceName, String targetName) {
        CityGraph cityGraph = cityRepository.findByName(cityName)
                .map(City::getCityGraph)
                .orElseThrow(() -> new CityNotFoundException(cityName));
        Stop source = cityGraph.findStop(sourceName).orElseThrow(() -> new StopNotFoundException(sourceName));
        Stop target = cityGraph.findStop(targetName).orElseThrow(() -> new StopNotFoundException(targetName));

        if (source.equals(target)) {
            return new PathInfoDTO(List.of(StopDTO.from(source)), 0d);
        }

        Dijkstra<Stop, Link> dijkstra = new Dijkstra<>();
        dijkstra.process(cityGraph.toGraph(), source, Link::getDistance);

        List<StopDTO> path = GraphUtils.shortestPath(dijkstra.getPredecessors(), source, target).stream()
                .map(StopDTO::from)
                .collect(Collectors.toList());
        Double distance = dijkstra.getDistance().get(target);

        return new PathInfoDTO(path, distance);
    }
}
