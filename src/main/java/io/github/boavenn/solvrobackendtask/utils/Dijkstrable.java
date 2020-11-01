package io.github.boavenn.solvrobackendtask.utils;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Edge;
import io.github.boavenn.solvrobackendtask.citygraph.graph.Graph;
import io.github.boavenn.solvrobackendtask.citygraph.graph.Vertex;

public interface Dijkstrable<T extends Vertex<?>, E extends Edge<T>>
{
    Graph<T, E> toGraph();
}
