package com.mine.App.model;

import java.util.List;
import java.util.Map;

public class Pie {

    private List<Map<String,Object>> elements;

    public Pie() {
    }

    public Pie(List<Map<String, Object>> elements) {
        this.elements = elements;
    }

    public List<Map<String, Object>> getElements() {
        return elements;
    }

    public void setElements(List<Map<String, Object>> elements) {
        this.elements = elements;
    }
}
