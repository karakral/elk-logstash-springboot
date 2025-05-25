package com.arman.elk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TotalHits {

    private int value;
    private String relation;

    // Getters and Setters
    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }
    public String getRelation() { return relation; }
    public void setRelation(String relation) { this.relation = relation; }
}