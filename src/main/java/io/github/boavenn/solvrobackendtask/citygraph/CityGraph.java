package io.github.boavenn.solvrobackendtask.citygraph;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Graph;
import io.github.boavenn.solvrobackendtask.utils.Dijkstrable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class CityGraph implements Dijkstrable<Stop, Link>
{
    private Graph<Stop, Link> graph = new Graph<>(false);
    private Map<String, Stop> stopsByName = new HashMap<>(); // makes sense only if name is a natural id of a stop

    public void addStop(Stop stop) {
        graph.addVertex(stop);
        stopsByName.put(stop.getName(), stop);
    }

    public void addLink(Link link) {
        graph.addEdge(link);
    }

    public List<Stop> stops() {
        return graph.vertices();
    }

    public Optional<Stop> findStop(String name) {
        return Optional.ofNullable(stopsByName.get(name));
    }

    public Map<Integer, Stop> stopsById() {
        return graph.vertices().stream().collect(Collectors.toMap(Stop::getId, s -> s));
    }

    public List<Link> links() {
        return graph.edges();
    }

    @Override
    public Graph<Stop, Link> toGraph() {
        return graph;
    }
}
