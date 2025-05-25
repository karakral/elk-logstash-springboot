package com.arman.elk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HitsWrapper {

    private TotalHits total;

    @JsonProperty("max_score")
    private Double maxScore;

    private List<Hit> hits;

    // Getters and Setters
    public TotalHits getTotal() { return total; }
    public void setTotal(TotalHits total) { this.total = total; }
    public Double getMaxScore() { return maxScore; }
    public void setMaxScore(Double maxScore) { this.maxScore = maxScore; }
    public List<Hit> getHits() { return hits; }
    public void setHits(List<Hit> hits) { this.hits = hits; }
}