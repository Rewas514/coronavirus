package com.example.coronavirus;

import com.example.coronavirus.model.CountryInformation;

import java.util.Comparator;

public class RecoveriesComparator implements Comparator<CountryInformation> {

    @Override
    public int compare(CountryInformation ci1, CountryInformation ci2) {


        if (ci1.getRecovered() > ci2.getRecovered()) {
            return 1;
        } else if (ci1.getRecovered() < ci2.getRecovered()) {
            return -1;
        } else {
            return 0;
        }

    }
}
