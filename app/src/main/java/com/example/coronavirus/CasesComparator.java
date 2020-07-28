package com.example.coronavirus;

import com.example.coronavirus.model.CountryInformation;

import java.util.Comparator;

public class CasesComparator implements Comparator<CountryInformation> {

    @Override
    public int compare(CountryInformation ci1, CountryInformation ci2) {


            if (ci1.getCases() > ci2.getCases()) {
                return 1;
            } else if (ci1.getCases() < ci2.getCases()) {
                return -1;
            } else {
                return 0;
            }

    }
}
