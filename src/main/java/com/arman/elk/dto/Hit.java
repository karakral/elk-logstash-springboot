package com.arman.elk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hit {

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_score")
    private Double score;

    @JsonProperty("_source")
    private SourceDocument source;

    // Getters and Setters
    public String getIndex() { return index; }
    public void setIndex(String index) { this.index = index; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public SourceDocument getSource() { return source; }
    public void setSource(SourceDocument source) { this.source = source; }
}