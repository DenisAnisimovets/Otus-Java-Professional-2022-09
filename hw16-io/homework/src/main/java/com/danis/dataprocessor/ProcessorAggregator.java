package com.danis.dataprocessor;

import com.danis.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> result = new TreeMap<>();
        data.forEach(it -> result.compute(it.getName(), (v, k) -> k == null ? it.getValue() : k + it.getValue()));
        return result;
    }
}
