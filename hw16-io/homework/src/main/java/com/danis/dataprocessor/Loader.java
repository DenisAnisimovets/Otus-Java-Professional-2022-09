package com.danis.dataprocessor;

import com.danis.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load();
}
