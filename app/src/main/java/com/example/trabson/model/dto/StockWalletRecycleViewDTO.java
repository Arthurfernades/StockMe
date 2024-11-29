package com.example.trabson.model.dto;

public class StockWalletRecycleViewDTO {

    private String code, svgUrl;

    private int quantity;

    private Double AmuntPayedPrice, AmuntCurrentPrice;

    public StockWalletRecycleViewDTO() {
    }

    public StockWalletRecycleViewDTO(String code, String svgUrl, int quantity, Double amuntPayedPrice, Double amuntCurrentPrice) {
        this.code = code;
        this.svgUrl = svgUrl;
        this.quantity = quantity;
        AmuntPayedPrice = amuntPayedPrice;
        AmuntCurrentPrice = amuntCurrentPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSvgUrl() {
        return svgUrl;
    }

    public void setSvgUrl(String svgUrl) {
        this.svgUrl = svgUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getAmuntPayedPrice() {
        return AmuntPayedPrice;
    }

    public void setAmuntPayedPrice(Double amuntPayedPrice) {
        AmuntPayedPrice = amuntPayedPrice;
    }

    public Double getAmuntCurrentPrice() {
        return AmuntCurrentPrice;
    }

    public void setAmuntCurrentPrice(Double amuntCurrentPrice) {
        AmuntCurrentPrice = amuntCurrentPrice;
    }
}
