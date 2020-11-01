package io.github.boavenn.solvrobackendtask.utils;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Vertex;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class GraphUtils
{
    public static <T extends Vertex<?>> List<T> shortestPath(Map<T, T> predecessors, T source, T target) {
        List<T> path = new LinkedList<>();
        while (!target.equals(source)) {
            path.add(target);
            target = predecessors.get(target);
        }
        path.add(target);
        Collections.reverse(path);
        return path;
    }
}
