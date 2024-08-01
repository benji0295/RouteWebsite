package org.example.routewebsite;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Parse the route data from a CSV file
 */

public class RouteParser {
  public static void parseRoutes(String filePath, Graph graph, Map<String, Airport> airports) {

    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean isFirstLine = true;

      while ((line = bufferedReader.readLine()) != null) {
        if (isFirstLine) {
          isFirstLine = false; // Skip header row
          continue;
        }
        String[] parts = line.split(",");

        if (parts.length != 4) continue; // Ensure no data from the CSV file is missing in each row

        String source = parts[0].trim();
        String destination = parts[1].trim();

        // Check if both source airport and destination airport exist in the map
        if (airports.containsKey(source) && airports.containsKey(destination)) {
          // Calculate the distance between airports
          double distance = calculateDistance(
                airports.get(source).latitude,
                airports.get(source).longitude,
                airports.get(destination).latitude,
                airports.get(destination).longitude
          );
          // Add an edge between the source and destination in the graph
          graph.addEdge(source, destination, (int) distance);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Calculates the distance between two airports using the Haversine formula
   */

  private static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

    final int Radius = 6371; // Radius of the earth in km

    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
              * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return Radius * c; // Distance in km
  }
}
