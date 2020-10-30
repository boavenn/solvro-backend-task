package io.github.boavenn.solvrobackendtask.citygraph.graph;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Graph<T extends Vertex<?>, E extends Edge<T>>
{
    private Map<Object, T> vertices = new HashMap<>();
    private Map<Object, List<E>> adjList = new HashMap<>();
    @Getter
    private final boolean directed;

    public boolean contains(T vertex) {
        return vertices.containsKey(vertex.getKey());
    }

    @SuppressWarnings("unchecked")
    public boolean contains(E edge) {
        T source = edge.getSource();
        T target = edge.getTarget();
        if (directed) {
            return adjacentEdgesOf(source).contains(edge);
        } else {
            E reversed = (E) edge.reversed();
            return adjacentEdgesOf(source).contains(edge) || adjacentEdgesOf(target).contains(reversed);
        }
    }

    public List<T> vertices() {
        return new LinkedList<>(vertices.values());
    }

    public List<E> edges() {
        return adjList.values().stream().flatMap(List::stream).collect(Collectors.toList());
    }

    public List<T> adjacentVerticesOf(T vertex) {
        return adjacentEdgesOf(vertex).stream()
                .map(Edge::getTarget)
                .collect(Collectors.toList());
    }

    public List<E> adjacentEdgesOf(T vertex) {
        return contains(vertex) ? adjList.get(vertex.getKey()) : Collections.emptyList();
    }

    public void addVertex(T vertex) {
        Object key = vertex.getKey();
        vertices.putIfAbsent(key, vertex);
        adjList.putIfAbsent(key, new LinkedList<>());
    }

    @SuppressWarnings("unchecked")
    public void addEdge(E edge) {
        T source = edge.getSource();
        T target = edge.getTarget();

        if (!vertices.containsKey(source.getKey())) {
            addVertex(source);
        }
        if (!vertices.containsKey(target.getKey())) {
            addVertex(target);
        }

        adjList.get(source.getKey()).add(edge);
        if (!directed) {
            adjList.get(target.getKey()).add((E) edge.reversed());
        }
    }

    public void removeVertex(T vertex) {
        if (!contains(vertex)) {
            return;
        }
        adjList.values().forEach(edges -> edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex)));
        Object key = vertex.getKey();
        vertices.remove(key);
        adjList.remove(key);
    }

    public void removeEdge(E edge) {
        T source = edge.getSource();
        T target = edge.getTarget();
        adjList.get(source.getKey()).removeIf(e -> e.getTarget().equals(target));
        if (!directed) {
            adjList.get(target.getKey()).removeIf(e -> e.getTarget().equals(source));
        }
    }
}
