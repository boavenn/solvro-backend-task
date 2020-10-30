package io.github.boavenn.solvrobackendtask.citygraph.graph;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class GraphTest
{
    @Test
    void shouldAddVerticesCorrectly() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(true);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }

        // when
        List<Integer> keys = graph.vertices().stream().map(SimpleVertex::getKey).collect(Collectors.toList());

        // then
        assertThat(keys).hasSize(3);
        assertThat(keys).containsExactlyInAnyOrder(1, 2, 3);
        assertThat(graph.contains(vertices[0])).isTrue();
        assertThat(graph.contains(vertices[1])).isTrue();
        assertThat(graph.contains(vertices[2])).isTrue();
    }

    @Test
    void shouldAddEdgesCorrectlyWhenGraphIsDirected() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(true);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // when
        List<SimpleEdge> actualEdges = graph.edges();

        // then
        assertThat(actualEdges).hasSize(3);
        assertThat(actualEdges).containsExactlyInAnyOrder(edges[0], edges[1], edges[2]);
        assertThat(graph.contains(edges[0])).isTrue();
        assertThat(graph.contains(edges[1])).isTrue();
        assertThat(graph.contains(edges[2])).isTrue();
    }

    @Test
    void shouldAddEdgesCorrectlyWhenGraphIsNotDirected() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(false);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // when
        List<SimpleEdge> actualEdges = graph.edges();

        // then
        assertThat(actualEdges).hasSize(6);
        assertThat(actualEdges).containsExactlyInAnyOrder(
                edges[0], edges[1], edges[2],
                edges[0].reversed(), edges[1].reversed(), edges[2].reversed());
        assertThat(graph.contains(edges[0])).isTrue();
        assertThat(graph.contains(edges[1])).isTrue();
        assertThat(graph.contains(edges[2])).isTrue();
        assertThat(graph.contains(edges[0].reversed())).isTrue();
        assertThat(graph.contains(edges[1].reversed())).isTrue();
        assertThat(graph.contains(edges[2].reversed())).isTrue();
    }

    @Test
    void shouldAddMissingVerticesWhenAddingEdges() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(false);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2)
        };
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // when
        List<Integer> keys = graph.vertices().stream().map(SimpleVertex::getKey).collect(Collectors.toList());
        List<SimpleEdge> actualEdges = graph.edges();

        // then
        assertThat(keys).hasSize(2);
        assertThat(keys).containsExactlyInAnyOrder(1, 2);
        assertThat(graph.contains(vertices[0])).isTrue();
        assertThat(graph.contains(vertices[1])).isTrue();
        assertThat(actualEdges).hasSize(2);
        assertThat(actualEdges).containsExactlyInAnyOrder(edges[0], edges[0].reversed());
        assertThat(graph.contains(edges[0])).isTrue();
        assertThat(graph.contains(edges[0].reversed())).isTrue();
    }

    @Test
    void shouldRemoveVerticesCorrectly() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(false);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // when
        graph.removeVertex(vertices[0]);
        graph.removeVertex(vertices[2]);

        List<Integer> keys = graph.vertices().stream().map(SimpleVertex::getKey).collect(Collectors.toList());
        List<SimpleEdge> actualEdges = graph.edges();

        // then
        assertThat(keys).hasSize(1);
        assertThat(keys).containsExactlyInAnyOrder(2);
        assertThat(graph.contains(vertices[0])).isFalse();
        assertThat(graph.contains(vertices[1])).isTrue();
        assertThat(graph.contains(vertices[2])).isFalse();
        assertThat(actualEdges).isEmpty();
        assertThat(graph.contains(edges[0])).isFalse();
        assertThat(graph.contains(edges[0].reversed())).isFalse();
        assertThat(graph.contains(edges[1])).isFalse();
        assertThat(graph.contains(edges[1].reversed())).isFalse();
        assertThat(graph.contains(edges[2])).isFalse();
        assertThat(graph.contains(edges[2].reversed())).isFalse();
    }

    @Test
    void shouldRemoveEdgesCorrectly() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(false);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // when
        graph.removeEdge(edges[0]);
        graph.removeEdge(edges[2]);

        List<Integer> keys = graph.vertices().stream().map(SimpleVertex::getKey).collect(Collectors.toList());
        List<SimpleEdge> actualEdges = graph.edges();

        // then
        assertThat(keys).hasSize(3);
        assertThat(keys).containsExactlyInAnyOrder(1, 2, 3);
        assertThat(graph.contains(vertices[0])).isTrue();
        assertThat(graph.contains(vertices[1])).isTrue();
        assertThat(graph.contains(vertices[2])).isTrue();
        assertThat(actualEdges).hasSize(2);
        assertThat(graph.contains(edges[0])).isFalse();
        assertThat(graph.contains(edges[0].reversed())).isFalse();
        assertThat(graph.contains(edges[1])).isTrue();
        assertThat(graph.contains(edges[1].reversed())).isTrue();
        assertThat(graph.contains(edges[2])).isFalse();
        assertThat(graph.contains(edges[2].reversed())).isFalse();
    }

    @Test
    void shouldReturnAdjacentVerticesCorrectlyWhenGraphIsDirected() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(true);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // then
        assertThat(graph.adjacentVerticesOf(vertices[0])).hasSize(1);
        assertThat(graph.adjacentVerticesOf(vertices[0])).contains(vertices[1]);
        assertThat(graph.adjacentVerticesOf(vertices[1])).hasSize(1);
        assertThat(graph.adjacentVerticesOf(vertices[1])).contains(vertices[2]);
        assertThat(graph.adjacentVerticesOf(vertices[2])).hasSize(1);
        assertThat(graph.adjacentVerticesOf(vertices[2])).contains(vertices[0]);
    }

    @Test
    void shouldReturnAdjacentVerticesCorrectlyWhenGraphIsNotDirected() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(false);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // then
        assertThat(graph.adjacentVerticesOf(vertices[0])).hasSize(2);
        assertThat(graph.adjacentVerticesOf(vertices[0])).containsExactlyInAnyOrder(vertices[1], vertices[2]);
        assertThat(graph.adjacentVerticesOf(vertices[1])).hasSize(2);
        assertThat(graph.adjacentVerticesOf(vertices[1])).containsExactlyInAnyOrder(vertices[0], vertices[2]);
        assertThat(graph.adjacentVerticesOf(vertices[2])).hasSize(2);
        assertThat(graph.adjacentVerticesOf(vertices[2])).containsExactlyInAnyOrder(vertices[0], vertices[1]);
    }

    @Test
    void shouldReturnAdjacentEdgesCorrectlyWhenGraphIsDirected() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(true);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // then
        assertThat(graph.adjacentEdgesOf(vertices[0])).hasSize(1);
        assertThat(graph.adjacentEdgesOf(vertices[0])).contains(edges[0]);
        assertThat(graph.adjacentEdgesOf(vertices[1])).hasSize(1);
        assertThat(graph.adjacentEdgesOf(vertices[1])).contains(edges[1]);
        assertThat(graph.adjacentEdgesOf(vertices[2])).hasSize(1);
        assertThat(graph.adjacentEdgesOf(vertices[2])).contains(edges[2]);
    }

    @Test
    void shouldReturnAdjacentEdgesCorrectlyWhenGraphIsNotDirected() {
        // given
        Graph<SimpleVertex, SimpleEdge> graph = new Graph<>(false);
        SimpleVertex[] vertices = {
                new SimpleVertex(1),
                new SimpleVertex(2),
                new SimpleVertex(3)
        };
        for (var v : vertices) {
            graph.addVertex(v);
        }
        SimpleEdge[] edges = {
                new SimpleEdge(vertices[0], vertices[1]),
                new SimpleEdge(vertices[1], vertices[2]),
                new SimpleEdge(vertices[2], vertices[0])
        };
        for (var e : edges) {
            graph.addEdge(e);
        }

        // then
        assertThat(graph.adjacentEdgesOf(vertices[0])).hasSize(2);
        assertThat(graph.adjacentEdgesOf(vertices[0])).containsExactlyInAnyOrder(edges[0], edges[2].reversed());
        assertThat(graph.adjacentEdgesOf(vertices[1])).hasSize(2);
        assertThat(graph.adjacentEdgesOf(vertices[1])).containsExactlyInAnyOrder(edges[1], edges[0].reversed());
        assertThat(graph.adjacentEdgesOf(vertices[2])).hasSize(2);
        assertThat(graph.adjacentEdgesOf(vertices[2])).containsExactlyInAnyOrder(edges[2], edges[1].reversed());
    }
}