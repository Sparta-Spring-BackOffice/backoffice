package com.example.backoffice.dashboard.controller;

import com.example.backoffice.dashboard.dto.ChartsStatsResponse;
import com.example.backoffice.dashboard.dto.LateOrderListResponse;
import com.example.backoffice.dashboard.dto.SummaryStatsResponse;
import com.example.backoffice.dashboard.dto.WidgetsStatsResponse;
import com.example.backoffice.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/admin/dashboards/summary")
    public ResponseEntity<SummaryStatsResponse> summaryStats() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.summaryStats());
    }

    @GetMapping("/admin/dashboards/widgets")
    public ResponseEntity<WidgetsStatsResponse> widgetsStats() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.widgetsStats());
    }

    @GetMapping("/admin/dashboards/charts")
    public ResponseEntity<ChartsStatsResponse> chartsStats() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.chartsStats());
    }

    @GetMapping("/admin/dashboards/lateorderlist")
    public ResponseEntity<List<LateOrderListResponse>> lateOrderList() {
        return ResponseEntity.status(HttpStatus.OK).body(dashboardService.lateOrderList());
    }
}
