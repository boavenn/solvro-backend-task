package io.github.boavenn.solvrobackendtask.citygraph;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CityGraphDeserializer
{
    @NoArgsConstructor
    @Setter
    @Getter
    private static class SerializedStop
    {
        private Integer id;
        @JsonProperty("stop_name")
        private String name;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    private static class SerializedLink
    {
        private Integer source;
        private Integer target;
        private Integer distance;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    private static class SerializedCityGraph
    {
        private boolean directed;
        private boolean multigraph;
        @JsonIgnore
        private Object graph;
        private List<SerializedLink> links = new LinkedList<>();
        private List<SerializedStop> nodes = new LinkedList<>();
    }

    private final ObjectMapper mapper;

    public Optional<CityGraph> deserialize(String filename) {
        SerializedCityGraph serializedGraph = readSerializedGraph(filename);
        if (serializedGraph == null) {
            return Optional.empty();
        }

        CityGraph graph = new CityGraph();
        Map<Integer, Stop> stops = new HashMap<>();

        serializedGraph.getNodes().forEach(s -> {
            Stop stop = new Stop(s.id, s.name);
            graph.addStop(stop);
            stops.put(stop.getId(), stop);
        });

        serializedGraph.getLinks().forEach(l -> {
            Link link = new Link(stops.get(l.source), stops.get(l.target), l.distance);
            graph.addLink(link);
        });

        return Optional.of(graph);
    }

    private SerializedCityGraph readSerializedGraph(String filename) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            return mapper.readValue(reader, SerializedCityGraph.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
