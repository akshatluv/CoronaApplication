package com.project.CoronaApplication.Service;

import com.project.CoronaApplication.Model.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class DataService {

    public static String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    RestTemplate rest = new RestTemplate();
    List<LocationStats> allStats = new LinkedList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public List<LocationStats> getData() throws IOException {
        List<LocationStats> currentStat = new LinkedList<>();
        ResponseEntity<String> forEntity = rest.getForEntity(DATA_URL, String.class);
        StringReader readerForBody = new StringReader(forEntity.getBody());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerForBody);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            locationStat.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
            currentStat.add(locationStat);
        }
        this.allStats= currentStat;
        return allStats;
    }

}
