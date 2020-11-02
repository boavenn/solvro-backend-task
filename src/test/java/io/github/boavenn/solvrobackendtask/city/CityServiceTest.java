package io.github.boavenn.solvrobackendtask.city;

import io.github.boavenn.solvrobackendtask.city.dto.PathInfoDTO;
import io.github.boavenn.solvrobackendtask.city.dto.StopDTO;
import io.github.boavenn.solvrobackendtask.citygraph.CityGraph;
import io.github.boavenn.solvrobackendtask.citygraph.Link;
import io.github.boavenn.solvrobackendtask.citygraph.Stop;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CityServiceTest
{
    @Mock private CityRepository cityRepository;
    @InjectMocks private CityService cityService;

    @Test
    public void shouldReturnStopsAsDTOCorrectly() {
        // given
        CityGraph cityGraph = new CityGraph();
        cityGraph.addStop(new Stop(1, "stop 1"));
        cityGraph.addStop(new Stop(2, "stop 2"));
        cityGraph.addStop(new Stop(3, "stop 3"));
        City city = new City("city", cityGraph);
        given(cityRepository.findByName("city")).willReturn(Optional.of(city));

        // when
        List<StopDTO> dtos = cityService.getStops("city");
        List<String> dtoNames = dtos.stream().map(StopDTO::getName).collect(Collectors.toList());

        // then
        assertThat(dtoNames).containsExactlyInAnyOrder("stop 1", "stop 2", "stop 3");
    }

    @Test
    public void shouldReturnShortestPathBetweenTwoStops() {
        // given
        CityGraph cityGraph = new CityGraph();
        Stop[] stops = {new Stop(1, "stop 1"), new Stop(2, "stop 2"), new Stop(3, "stop 3")};
        Link[] links = {new Link(stops[0], stops[1], 10), new Link(stops[0], stops[2], 20)};
        for (Stop s : stops) {
            cityGraph.addStop(s);
        }
        for (Link l : links) {
            cityGraph.addLink(l);
        }
        City city = new City("city", cityGraph);
        given(cityRepository.findByName("city")).willReturn(Optional.of(city));

        // when
        PathInfoDTO pathInfo = cityService.getPath("city", "stop 2", "stop 3");
        List<String> stopsNames = pathInfo.getStops().stream().map(StopDTO::getName).collect(Collectors.toList());

        // then
        assertThat(stopsNames).isEqualTo(List.of("stop 2", "stop 1", "stop 3"));
        assertThat(pathInfo.getDistance()).isEqualTo(30);
    }

    @Test
    public void shouldThrowExceptionWhenCityWithGivenNameIsNotFound() {
        // given
        given(cityRepository.findByName("city")).willReturn(Optional.empty());

        // then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> cityService.getPath("city", "x", "y"))
                .withMessageContaining("Cannot find city with name");
    }

    @Test
    public void shouldThrowExceptionWhenStopWithGivenNameIsNotFound() {
        // given
        CityGraph cityGraph = Mockito.mock(CityGraph.class);
        given(cityGraph.findStop(anyString())).willReturn(Optional.empty());
        City city = new City("city", cityGraph);
        given(cityRepository.findByName("city")).willReturn(Optional.of(city));

        // then
        assertThatExceptionOfType(NoSuchElementException.class)
                .isThrownBy(() -> cityService.getPath("city", "x", "y"))
                .withMessageContaining("Cannot find stop with name");
    }
}