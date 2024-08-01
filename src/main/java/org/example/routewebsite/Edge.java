package org.example.routewebsite;
/**
 * Designates edges with a destination and a distance (weight)
 */

public class Edge {
  String destination;
  int weight;

  public Edge(String destination, int weight) {
    this.destination = destination;
    this.weight = weight;
  }
}
