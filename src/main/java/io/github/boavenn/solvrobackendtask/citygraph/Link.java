package io.github.boavenn.solvrobackendtask.citygraph;

import io.github.boavenn.solvrobackendtask.citygraph.graph.Edge;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public class Link implements Edge<Stop>
{
    private Stop source;
    private Stop target;
    private Integer distance;

    @Override
    public Link reversed() {
        return new Link(target, source, distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(source, link.source) &&
                Objects.equals(target, link.target) &&
                Objects.equals(distance, link.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, distance);
    }
}
