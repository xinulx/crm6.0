package com.mine.App.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieElement {

    private String type;
    private String[] colours;
    private float alpha;
    private int border;
    private int startAngle;
    private int fontSize;
    private String text;
    private List<Map<String,Object>> values;

    public PieElement() {
    }

    public Map<String,Object> encode(){
        Map<String,Object> elementMap = new HashMap<>();
        elementMap.put("type",type);
        elementMap.put("colours",colours);
        elementMap.put("alpha",alpha);
        elementMap.put("border",border);
        elementMap.put("start-angle",startAngle);
        elementMap.put("font-size",fontSize);
        elementMap.put("text",text);
        elementMap.put("values",values);
        return elementMap;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getColours() {
        return colours;
    }

    public void setColours(String[] colours) {
        this.colours = colours;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Map<String, Object>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, Object>> values) {
        this.values = values;
    }
}
