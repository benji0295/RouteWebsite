package org.example.routewebsite;
import java.util.*;

/**
 * Implements Dijkstra's algorithm to find the shortest path in the graph
 */

public class Dijkstra {
  public static Map<String, Integer> dijkstra(Graph graph, String source) {
    // Initialize maps to track distances and visited vertices
    Map<String, Integer> distances = new HashMap<>();
    Map<String, Boolean> visited = new HashMap<>();

    for (String airport : graph.getAdjList().keySet()) {
      distances.put(airport, Integer.MAX_VALUE); // Set edge weight to infinity
      visited.put(airport, false); // Set visited false to all vertices
    }

    distances.put(source, 0);

    for (int i = 0; i < graph.getAdjList().size(); i++) {
      // Find the unvisited vertex with the smallest distance
      String u = findMinDistance(distances, visited);
      if (u == null) break; // If no more nodes are reachable

      // Mark the vertex as visited
      visited.put(u, true);

      // Uodate the distance for each adjacent vertex
      for (Edge edge : graph.getEdges(u)) {
        String v = edge.destination;

        // If the vertex has not been visited and the new distance is smaller, update it
        if (!visited.get(v) && (distances.get(u) + edge.weight < distances.get(v))) {
          distances.put(v, distances.get(u) + edge.weight);
        }
      }
    }
    return distances;
  }
  /**
   * Finds the vertex with the minimum distance that has not been visited
   */

  private static String findMinDistance(Map<String, Integer> distances, Map<String, Boolean> visited) {
    int minDistance = Integer.MAX_VALUE;
    String minDistanceVertex = null;

    // Iterate through all vertices to find the minimum distance unvisted vertex
    for (Map.Entry<String, Integer> entry : distances.entrySet()) {
      if (!visited.get(entry.getKey()) && entry.getValue() < minDistance) { // Check if we visited the vertex
        minDistance = entry.getValue();
        minDistanceVertex = entry.getKey();
      }
    }
    return minDistanceVertex;
  }
}
