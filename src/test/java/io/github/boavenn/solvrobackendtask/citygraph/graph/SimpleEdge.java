package io.github.boavenn.solvrobackendtask.citygraph.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
class SimpleEdge implements Edge<SimpleVertex>
{
    private SimpleVertex source;
    private SimpleVertex target;

    @Override
    public SimpleEdge reversed() {
        return new SimpleEdge(target, source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleEdge that = (SimpleEdge) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(target, that.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target);
    }
}
