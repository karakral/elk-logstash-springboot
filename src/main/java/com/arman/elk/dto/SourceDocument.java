package com.arman.elk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SourceDocument {

    private String traceId;
    private String request;
    private String action;
    private String serviceId;
    private String serviceFaName;
    private String status;
    private Long timestamp; // Assuming timestamp is a long
    private String dateTime; // Optional, as it's not in all your examples
    private String nationalId; // Optional


    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceFaName() {
        return serviceFaName;
    }

    public void setServiceFaName(String serviceFaName) {
        this.serviceFaName = serviceFaName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    @Override
    public String toString() {
        return "SourceDocument{" +
                "traceId='" + traceId + '\'' +
                ", action='" + action + '\'' +
                ", status='" + status + '\'' +
                // ... other fields
                '}';
    }
}