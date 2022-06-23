package com.project.CoronaApplication.Controller;

import com.project.CoronaApplication.Model.LocationStats;
import com.project.CoronaApplication.Service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class latestCasesStatController {

    @Autowired
    private DataService dataService;

    @GetMapping("/getLatestRecord")
    public List<LocationStats> getLatestRecord() throws IOException {
        return dataService.getData();
    }
}
