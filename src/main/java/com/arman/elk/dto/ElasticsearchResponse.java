package com.arman.elk.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticsearchResponse {

    private int took;

    @JsonProperty("timed_out")
    private boolean timedOut;

    @JsonProperty("_shards")
    private ShardsInfo shards; // You can define ShardsInfo similarly if needed

    private HitsWrapper hits;

    // Getters and Setters
    public int getTook() { return took; }
    public void setTook(int took) { this.took = took; }
    public boolean isTimedOut() { return timedOut; }
    public void setTimedOut(boolean timedOut) { this.timedOut = timedOut; }
    public ShardsInfo getShards() { return shards; }
    public void setShards(ShardsInfo shards) { this.shards = shards; }
    public HitsWrapper getHits() { return hits; }
    public void setHits(HitsWrapper hits) { this.hits = hits; }
}