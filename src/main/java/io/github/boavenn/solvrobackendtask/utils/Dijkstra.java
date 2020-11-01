package io.github.boavenn.solvrobackendtask.utils;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Edge;
import io.github.boavenn.solvrobackendtask.citygraph.graph.Graph;
import io.github.boavenn.solvrobackendtask.citygraph.graph.Vertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class Dijkstra<T extends Vertex<?>, E extends Edge<T>>
{
    @AllArgsConstructor
    private class Node
    {
        private T vertex;
        private Double distance;
    }

    @Getter
    private Map<T, Double> distance;
    @Getter
    private Map<T, T> predecessors;
    private ToDoubleFunction<E> edgeWeightFun;

    public void process(Dijkstrable<T, E> dijkstrable, T source, ToDoubleFunction<E> edgeWeightFun) {
        process(dijkstrable.toGraph(), source, edgeWeightFun);
    }

    public void process(Graph<T, E> graph, T source, ToDoubleFunction<E> edgeWeightFun) {
        initialize(graph.vertices(), source, edgeWeightFun);
        HashSet<T> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.distance));

        queue.add(new Node(source, 0d));

        while (!queue.isEmpty()) {
            T vertex = queue.poll().vertex;
            visited.add(vertex);
            for (E edge : graph.adjacentEdgesOf(vertex)) {
                T target = edge.getTarget();
                if (!visited.contains(target)) {
                    if (relaxEdge(edge)) {
                        predecessors.put(target, vertex);
                    }
                    queue.add(new Node(target, distance.get(target)));
                }
            }
        }
    }

    private void initialize(List<T> vertices, T source, ToDoubleFunction<E> edgeWeightFun) {
        distance = vertices.stream().collect(Collectors.toMap(v -> v, v -> Double.MAX_VALUE));
        distance.replace(source, 0d);
        predecessors = new HashMap<>();
        this.edgeWeightFun = edgeWeightFun;
    }

    private boolean relaxEdge(E edge) {
        T source = edge.getSource();
        T target = edge.getTarget();
        double newDistance = distance.get(source) + edgeWeightFun.applyAsDouble(edge);
        if (distance.get(source) != Double.MAX_VALUE && newDistance < distance.get(target)) {
            distance.replace(target, newDistance);
            return true;
        }
        return false;
    }
}
