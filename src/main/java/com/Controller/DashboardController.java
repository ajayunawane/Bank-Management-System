package com.Controller;

import com.Service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin("*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public Map<String, Object> getDashboardData() {

        return dashboardService.getDashboardData();
    }
}