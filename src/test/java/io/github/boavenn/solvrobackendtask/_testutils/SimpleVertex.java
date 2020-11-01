package io.github.boavenn.solvrobackendtask._testutils;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Vertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class SimpleVertex implements Vertex<Integer>
{
    private Integer key;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleVertex that = (SimpleVertex) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
