package io.github.boavenn.solvrobackendtask.citygraph.graph;

public interface Edge<T extends Vertex<?>>
{
    T getSource();
    T getTarget();
    /**
     * @return new Edge where source and target are switched
     */
    Edge<T> reversed();
}
