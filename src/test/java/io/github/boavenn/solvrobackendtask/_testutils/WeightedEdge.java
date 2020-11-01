package io.github.boavenn.solvrobackendtask._testutils;

import lombok.Getter;

@Getter
public class WeightedEdge extends SimpleEdge
{
    private Integer weight;

    public WeightedEdge(SimpleVertex source, SimpleVertex target, Integer weight) {
        super(source, target);
        this.weight = weight;
    }
}
