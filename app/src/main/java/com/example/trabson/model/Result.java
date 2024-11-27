package com.example.trabson.model;

public class Result {

    private String currency, shortName, longName, regularMarketTime, fiftyTwoWeekRange, symbol, logoUrl;

    private double regularMarketChange, regularMarketChangePercent, regularMarketPrice,
            regularMarketDayHigh, regularMarketDayLow, regularMarketVolume, regularMarketPreviousClose,
            regularMarketOpen, fiftyTwoWeekLow, fiftyTwoWeekHigh, priceEarnings, earningsPerShare;

    public Result() {
    }

    public Result(String currency, String shortName, String longName, String regularMarketTime, String fiftyTwoWeekRange,
                  String symbol, String logoUrl, double regularMarketChange, double regularMarketChangePercent,
                  double regularMarketPrice, double regularMarketDayHigh, double regularMarketDayLow,
                  double regularMarketVolume, double regularMarketPreviousClose, double regularMarketOpen,
                  double fiftyTwoWeekLow, double fiftyTwoWeekHigh, double priceEarnings, double earningsPerShare) {
        this.currency = currency;
        this.shortName = shortName;
        this.longName = longName;
        this.regularMarketTime = regularMarketTime;
        this.fiftyTwoWeekRange = fiftyTwoWeekRange;
        this.symbol = symbol;
        this.logoUrl = logoUrl;
        this.regularMarketChange = regularMarketChange;
        this.regularMarketChangePercent = regularMarketChangePercent;
        this.regularMarketPrice = regularMarketPrice;
        this.regularMarketDayHigh = regularMarketDayHigh;
        this.regularMarketDayLow = regularMarketDayLow;
        this.regularMarketVolume = regularMarketVolume;
        this.regularMarketPreviousClose = regularMarketPreviousClose;
        this.regularMarketOpen = regularMarketOpen;
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
        this.priceEarnings = priceEarnings;
        this.earningsPerShare = earningsPerShare;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getRegularMarketTime() {
        return regularMarketTime;
    }

    public void setRegularMarketTime(String regularMarketTime) {
        this.regularMarketTime = regularMarketTime;
    }

    public String getFiftyTwoWeekRange() {
        return fiftyTwoWeekRange;
    }

    public void setFiftyTwoWeekRange(String fiftyTwoWeekRange) {
        this.fiftyTwoWeekRange = fiftyTwoWeekRange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void getLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public double getRegularMarketChange() {
        return regularMarketChange;
    }

    public void setRegularMarketChange(double regularMarketChange) {
        this.regularMarketChange = regularMarketChange;
    }

    public double getRegularMarketChangePercent() {
        return regularMarketChangePercent;
    }

    public void setRegularMarketChangePercent(double regularMarketChangePercent) {
        this.regularMarketChangePercent = regularMarketChangePercent;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    public double getRegularMarketDayHigh() {
        return regularMarketDayHigh;
    }

    public void setRegularMarketDayHigh(double regularMarketDayHigh) {
        this.regularMarketDayHigh = regularMarketDayHigh;
    }

    public double getRegularMarketDayLow() {
        return regularMarketDayLow;
    }

    public void setRegularMarketDayLow(double regularMarketDayLow) {
        this.regularMarketDayLow = regularMarketDayLow;
    }

    public double getRegularMarketVolume() {
        return regularMarketVolume;
    }

    public void setRegularMarketVolume(double regularMarketVolume) {
        this.regularMarketVolume = regularMarketVolume;
    }

    public double getRegularMarketPreviousClose() {
        return regularMarketPreviousClose;
    }

    public void setRegularMarketPreviousClose(double regularMarketPreviousClose) {
        this.regularMarketPreviousClose = regularMarketPreviousClose;
    }

    public double getRegularMarketOpen() {
        return regularMarketOpen;
    }

    public void setRegularMarketOpen(double regularMarketOpen) {
        this.regularMarketOpen = regularMarketOpen;
    }

    public double getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(double fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public double getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(double fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public double getPriceEarnings() {
        return priceEarnings;
    }

    public void setPriceEarnings(double priceEarnings) {
        this.priceEarnings = priceEarnings;
    }

    public double getEarningsPerShare() {
        return earningsPerShare;
    }

    public void setEarningsPerShare(double earningsPerShare) {
        this.earningsPerShare = earningsPerShare;
    }
}
