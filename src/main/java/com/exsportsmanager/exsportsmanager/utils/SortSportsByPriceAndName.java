package com.exsportsmanager.exsportsmanager.utils;

import com.exsportsmanager.exsportsmanager.models.Sport;

import java.util.Comparator;

public class SortSportsByPriceAndName implements Comparator<Sport> {

    @Override
    public int compare(Sport sport1, Sport sport2) {
        int nameCompare = sport1.getSportName().compareTo(sport2.getSportName());
        int averagePriceCompare = sport1.getAveragePrice().compareTo(sport2.getAveragePrice());

        if (averagePriceCompare == 0) {
            return ((nameCompare == 0) ? averagePriceCompare : nameCompare);
        } else {
            return averagePriceCompare;
        }
    }
}
