package io.github.boavenn.solvrobackendtask.citygraph;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Vertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Stop implements Vertex<Integer>
{
    private Integer id;
    private String name;

    @Override
    public Integer getKey() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stop stop = (Stop) o;
        return Objects.equals(id, stop.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
