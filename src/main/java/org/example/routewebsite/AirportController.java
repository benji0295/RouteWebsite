package org.example.routewebsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirportController {

    @Autowired
    private AirportService airportService;

    @GetMapping("/shortest-route")
    public String getShortestRoute(@RequestParam String departure, @RequestParam String arrival) {
        return airportService.findShortestRoute(departure, arrival);
    }
}
