package com.example.trabson.model.dto;

import com.example.trabson.model.Result;

import java.util.List;

public class StockInfoResponseDTO {

    private List<Result> results;

    private String requestedAt, took;

    public StockInfoResponseDTO() {
    }

    public StockInfoResponseDTO(List<Result> results, String requestedAt, String took) {
        this.results = results;
        this.requestedAt = requestedAt;
        this.took = took;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(String requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getTook() {
        return took;
    }

    public void setTook(String took) {
        this.took = took;
    }
}
