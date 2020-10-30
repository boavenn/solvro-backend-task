package io.github.boavenn.solvrobackendtask.citygraph;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class CityGraphDeserializerTest
{
    private final String testPath = "src/test/resources/";
    private final CityGraphDeserializer deserializer;

    CityGraphDeserializerTest() {
        ObjectMapper mapper = new ObjectMapper();
        deserializer = new CityGraphDeserializer(mapper);
    }

    @Test
    public void shouldDeserializeGraphCorrectly() {
        // given
        String filename = "shouldDeserializeGraphCorrectly.json";
        CityGraph graph = deserializer.deserialize(testPath + filename).orElseThrow();

        // when
        Map<Integer, Stop> stops = graph.stopsById();
        List<Link> links = graph.links();

        // then
        assertThat(stops).hasSize(3);
        assertThat(stops).containsKeys(1, 2, 3);
        assertThat(links).hasSize(6);

        Link[] expectedLinks = {
                new Link(stops.get(1), stops.get(2), 5),
                new Link(stops.get(2), stops.get(3), 10),
                new Link(stops.get(1), stops.get(2), 5)
        };
        assertThat(links).contains(expectedLinks[0]);
        assertThat(links).contains(expectedLinks[0].reversed());
        assertThat(links).contains(expectedLinks[1]);
        assertThat(links).contains(expectedLinks[1].reversed());
        assertThat(links).contains(expectedLinks[2]);
        assertThat(links).contains(expectedLinks[2].reversed());
    }

    @Test
    public void shouldDeserializeEmptyGraphCorrectly() {
        // given
        String filename = "shouldDeserializeEmptyGraphCorrectly.json";
        CityGraph graph = deserializer.deserialize(testPath + filename).orElseThrow();

        // when
        Map<Integer, Stop> stops = graph.stopsById();
        List<Link> links = graph.links();

        // then
        assertThat(stops).isEmpty();
        assertThat(links).isEmpty();
    }

    @Test
    public void shouldReturnEmptyOptionalIfDeserializingIsUnsuccessful() {
        // given
        Optional<CityGraph> graphOpt = deserializer.deserialize("?");

        // then
        assertThat(graphOpt).isEmpty();
    }
}