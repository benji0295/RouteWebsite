package org.example.routewebsite;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a graph using an adjacency list
 */

public class Graph {
  private Map<String, List<Edge>> adjList;

  public Graph() {
    adjList = new HashMap<>();
  }

  /**
   * Adds an edge to the graph
   */

  public void addEdge(String source, String destination, int weight) {
    adjList.computeIfAbsent(source, k -> new LinkedList<>()).add(new Edge(destination, weight));
    adjList.computeIfAbsent(destination, k -> new LinkedList<>()).add(new Edge(source, weight));
  }

  /**
   * Returns the edges connected to a given node
   */

  public List<Edge> getEdges(String node) {
    return adjList.getOrDefault(node, new LinkedList<>());
  }

  /**
   * Returns the adjacency list of the graph
   */

  public Map<String, List<Edge>> getAdjList() {
    return adjList;
  }
}
