package io.github.boavenn.solvrobackendtask.citygraph;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Graph;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CityGraph
{
    private Graph<Stop, Link> graph = new Graph<>(false);

    public void addStop(Stop stop) {
        graph.addVertex(stop);
    }

    public void addLink(Link link) {
        graph.addEdge(link);
    }

    public List<Stop> stops() {
        return graph.vertices();
    }

    public Map<Integer, Stop> stopsById() {
        return graph.vertices().stream().collect(Collectors.toMap(Stop::getId, s -> s));
    }

    public List<Link> links() {
        return graph.edges();
    }
}
