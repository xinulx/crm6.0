package com.mine.App.model;

import java.util.HashMap;
import java.util.Map;

public class ElementValue {

    private int value;
    private String label;
    private String tip;
    private String onClick;

    public ElementValue() {
    }

    public Map<String,Object> encode(){
        Map<String,Object> valueMap = new HashMap<>();
        valueMap.put("value",value);
        valueMap.put("label",label);
        valueMap.put("tip",tip);
        valueMap.put("on-click",onClick);
        return valueMap;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getOnClick() {
        return onClick;
    }

    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }
}
