package org.template;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        getChart(restTemplate);
    }

    private static void sensorCreate(RestTemplate restTemplate) {
        Map<String, String> jsonForSensor = new HashMap<>();
        jsonForSensor.put("name", "Template sensor name");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonForSensor);

        String url = "http://localhost:8080/sensors/registration";

        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);
    }

    private static void measurementsCreate(RestTemplate restTemplate) {
        Map<String, Object> jsomForMeasurements = new HashMap<>();

        Map<String, String> jsonForSensor = new HashMap<>();
        jsonForSensor.put("name", "Template sensor name");

        for (int i = 0; i < 1000; i++) {
            Double value = Math.random() * 100 - 50;
            Boolean raining = Math.random() > 0.5;

            jsomForMeasurements.put("value", value);
            jsomForMeasurements.put("raining", raining);
            jsomForMeasurements.put("sensor", jsonForSensor);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsomForMeasurements);

            String url = "http://localhost:8080/measurements/add";

            String response = restTemplate.postForObject(url, request, String.class);
            System.out.println(response);
        }
    }

    private static void measurementsGet(RestTemplate restTemplate) {
        String url = "http://localhost:8080/measurements";

        String response = restTemplate.getForObject(url, String.class);
        System.out.println(response);
    }

    private static void getChart(RestTemplate restTemplate) {
        String url = "http://localhost:8080/measurements";
        String jsonString = restTemplate.getForObject(url, String.class);

        HashMap<String, Object> measurements = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<String, Object>>() {}.getType()
        );

        if (measurements != null) {
            List<Double> temperatures = new ArrayList<>();
            for (int i = 0; i < measurements.size(); i++) {
                temperatures.add((Double) measurements.get("value"));
            }

            List<Integer> temps = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                temps.add(i);
            }

            XYChart chart = QuickChart.getChart("Measurements", "T", "t", "t(T)",
                    temps, temperatures);

            new SwingWrapper(chart).displayChart();
        }
    }


}