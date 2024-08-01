package org.example.routewebsite;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AirportService {

    private Map<String, Airport> airports;
    private Graph graph;

    @PostConstruct
    public void init() {
        // Get the current working directory
        String baseDir = System.getProperty("user.dir");

        // Construct the full paths to the CSV files
        Path airportFilePath = Paths.get(baseDir, "airport.csv");
        Path routeFilePath = Paths.get(baseDir, "route.csv");

        System.out.println("Airport File Path: " + airportFilePath.toString());
        System.out.println("Route File Path: " + routeFilePath.toString());

        try {
            // Parse the airport data
            this.airports = AirportParser.parseAirports(airportFilePath.toString());
            this.graph = new Graph();

            // Parse the route data and load it to the graph
            RouteParser.parseRoutes(routeFilePath.toString(), graph, airports);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getAirports()
    {
        return airports.values().stream().map(airport -> {
            Map<String, String> airportData = new HashMap<>();
            airportData.put("code", airport.getCode());
            airportData.put("name", airport.getName());
            return airportData;
        })
                .collect(Collectors.toList());
    }

    public String findShortestRoute(String departure, String arrival) {
        if (!airports.containsKey(departure) || !airports.containsKey(arrival)) {
            return "Invalid airport code.";
        }

        // Run the Dijkstra algorithm to find the shortest route
        Map<String, Integer> distances = Dijkstra.dijkstra(graph, departure);
        List<String> path = findPath(graph, departure, arrival, distances);

        // Generate the results
        if (path.isEmpty()) {
            return "No route found.";
        } else {
            StringBuilder result = new StringBuilder();
            result.append("Shortest route: ").append(String.join(" -> ",path));
            int totalDistanceKm = distances.get(arrival);
            int totalDistanceMiles = (int) Math.round(totalDistanceKm * 0.621371);
            result.append("\nTotal distance: ").append(totalDistanceKm).append(" km / ").append(totalDistanceMiles).append(" miles");
            return result.toString();
        }
    }

    private List<String> findPath(Graph graph, String start, String end, Map<String, Integer> distances) {
        List<String> path = new LinkedList<>();
        String current = end;

        // Traverse from the end to the start to construct the path
        while (!current.equals(start)) {
            path.add(0, current); // Add the current airport to the beginning of the path
            int minDistance = Integer.MAX_VALUE;
            String next = null;

            // Find the next airport in the path with the minimum distance
            for (Edge edge : graph.getEdges(current)) {
                if (distances.get(edge.destination) < minDistance) {
                    minDistance = distances.get(edge.destination);
                    next = edge.destination;
                }
            }

            // If no next airport is found, there is no path
            if (next == null) {
                System.out.println("No path exists from " + start + " to " + end);
                return new LinkedList<>();
            }
            current = next;
        }
        path.add(0, start);
        return path;
    }
}
