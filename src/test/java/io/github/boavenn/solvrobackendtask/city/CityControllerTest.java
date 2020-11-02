package io.github.boavenn.solvrobackendtask.city;

import io.github.boavenn.solvrobackendtask.citygraph.CityGraph;
import io.github.boavenn.solvrobackendtask.citygraph.Link;
import io.github.boavenn.solvrobackendtask.citygraph.Stop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@ActiveProfiles("test")
class CityControllerTest
{
    @Autowired private MockMvc mvc;
    @Autowired private CityRepository cityRepository;

    @Test
    public void getStopsShouldReturnAllStopsAsDTOs() throws Exception {
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
        City city = new City(CityController.DEFAULT_CITY, cityGraph);
        cityRepository.save(city);

        // when
        var request = get("/stops");

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("[0].name").value("stop 1"))
                .andExpect(jsonPath("[1].name").value("stop 2"))
                .andExpect(jsonPath("[2].name").value("stop 3"));
    }

    @Test
    public void getPathShouldReturnShortestPathBetweenTwoStops() throws Exception {
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
        City city = new City(CityController.DEFAULT_CITY, cityGraph);
        cityRepository.save(city);

        // when
        var request = get("/path?source=stop 2&target=stop 3");

        // then
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stops[0].name").value("stop 2"))
                .andExpect(jsonPath("$.stops[1].name").value("stop 1"))
                .andExpect(jsonPath("$.stops[2].name").value("stop 3"))
                .andExpect(jsonPath("$.distance").value(30));
    }
}