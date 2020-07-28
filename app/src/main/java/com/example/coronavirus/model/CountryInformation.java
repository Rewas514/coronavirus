package com.example.coronavirus.model;

public class CountryInformation {

    private String countryName;
    private int deaths;
    private int cases;
    private int recovered;
    private String date;

    public CountryInformation() {

    }

    public CountryInformation(String countryName, int deaths, int cases, int recovered) {
        this.countryName = countryName;
        this.deaths = deaths;
        this.cases = cases;
        this.recovered = recovered;
    }

    public String getCountryName() {
        return countryName;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getCases() {
        return cases;
    }

    public String getDate() {
        return date;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }




}
