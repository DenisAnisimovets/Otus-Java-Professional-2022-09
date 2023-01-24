package com.danis.dataprocessor;

import com.danis.model.Measurement;
import com.google.gson.Gson;
import jakarta.json.Json;
import jakarta.json.JsonReader;

import java.util.Arrays;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;


    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> measurements;
        Gson gson = new Gson();
        try (JsonReader reader = Json.createReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName))) {
            String jsonString = reader.read().toString();
            measurements = Arrays.asList(gson.fromJson(jsonString, Measurement[].class));
        }

        return measurements;
    }
}
