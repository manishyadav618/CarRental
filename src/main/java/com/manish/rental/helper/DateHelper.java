package com.manish.rental.helper;

import com.manish.rental.model.RentalDates;

public class DateHelper {
    public static boolean doRentalsOverLap(RentalDates rentalDate1, RentalDates rentalDates2){
        if (rentalDates2.getStartDate().isBefore(rentalDate1.getStartDate())
                && rentalDates2.getEndDate().isBefore(rentalDate1.getStartDate())){
            return false;
        }
        if (rentalDates2.getStartDate().isAfter(rentalDate1.getEndDate()) &&
                rentalDates2.getEndDate().isAfter(rentalDate1.getEndDate())){
            return false;
        }
        return true;
    }
}
