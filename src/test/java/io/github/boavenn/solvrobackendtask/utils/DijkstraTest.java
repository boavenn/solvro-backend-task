package io.github.boavenn.solvrobackendtask.utils;

import io.github.boavenn.solvrobackendtask._testutils.SimpleVertex;
import io.github.boavenn.solvrobackendtask._testutils.WeightedEdge;
import io.github.boavenn.solvrobackendtask.citygraph.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DijkstraTest
{
    private Dijkstra<SimpleVertex, WeightedEdge> dijkstra = new Dijkstra<>();

    @Test
    public void shouldReturnCorrectOutputForDirectedGraph() {
        // given
        Graph<SimpleVertex, WeightedEdge> graph = new Graph<>(true);
        Integer[] verticesKeys = {1, 2, 3, 4, 5};
        SimpleVertex[] vertices = Arrays.stream(verticesKeys).map(SimpleVertex::new).toArray(SimpleVertex[]::new);
        Integer[][] edges = {
                {1, 2, 10}, {2, 4, 1}, {1, 3, 5}, {2, 3, 2}, {3, 2, 3},
                {3, 4, 9}, {3, 5, 2}, {4, 5, 4}, {5, 4, 6}, {5, 1, 7}
        };
        for (int i : verticesKeys) {
            graph.addVertex(new SimpleVertex(i));
        }
        for (Integer[] i : edges) {
            graph.addEdge(new WeightedEdge(vertices[i[0] - 1], vertices[i[1] - 1], i[2]));
        }

        // when
        SimpleVertex source = vertices[0];
        dijkstra.process(graph, source, WeightedEdge::getWeight);
        Map<SimpleVertex, Double> distance = dijkstra.getDistance();
        Map<SimpleVertex, SimpleVertex> predecessors = dijkstra.getPredecessors();

        // then
        assertThat(distance.get(vertices[0])).isEqualTo(0);
        assertThat(distance.get(vertices[1])).isEqualTo(8);
        assertThat(distance.get(vertices[2])).isEqualTo(5);
        assertThat(distance.get(vertices[3])).isEqualTo(9);
        assertThat(distance.get(vertices[4])).isEqualTo(7);

        List<Integer> pathFrom1To4 = GraphUtils.shortestPath(predecessors, source, vertices[3]).stream()
                .map(SimpleVertex::getKey)
                .collect(Collectors.toList());
        assertThat(pathFrom1To4).isEqualTo(List.of(1, 3, 2, 4));
    }

    @Test
    public void shouldReturnCorrectOutputForUndirectedGraph() {
        // given
        Graph<SimpleVertex, WeightedEdge> graph = new Graph<>(false);
        Integer[] verticesKeys = {1, 2, 3, 4, 5, 6};
        SimpleVertex[] vertices = Arrays.stream(verticesKeys).map(SimpleVertex::new).toArray(SimpleVertex[]::new);
        Integer[][] edges = {
                {1, 3, 2}, {1, 6, 10}, {1, 5, 3}, {3, 5, 2},
                {5, 6, 6}, {3, 4, 1}, {3, 2, 5}, {4, 2, 1}
        };
        for (int i : verticesKeys) {
            graph.addVertex(new SimpleVertex(i));
        }
        for (Integer[] i : edges) {
            graph.addEdge(new WeightedEdge(vertices[i[0] - 1], vertices[i[1] - 1], i[2]));
        }

        // when
        SimpleVertex source = vertices[0];
        dijkstra.process(graph, source, WeightedEdge::getWeight);
        Map<SimpleVertex, Double> distance = dijkstra.getDistance();
        Map<SimpleVertex, SimpleVertex> predecessors = dijkstra.getPredecessors();

        // then
        assertThat(distance.get(vertices[0])).isEqualTo(0);
        assertThat(distance.get(vertices[1])).isEqualTo(4);
        assertThat(distance.get(vertices[2])).isEqualTo(2);
        assertThat(distance.get(vertices[3])).isEqualTo(3);
        assertThat(distance.get(vertices[4])).isEqualTo(3);
        assertThat(distance.get(vertices[5])).isEqualTo(9);

        List<Integer> pathFrom1To4 = GraphUtils.shortestPath(predecessors, source, vertices[1]).stream()
                .map(SimpleVertex::getKey)
                .collect(Collectors.toList());
        assertThat(pathFrom1To4).isEqualTo(List.of(1, 3, 4, 2));
    }
}