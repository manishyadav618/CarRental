package com.manish.rental.services.impl;

import com.manish.rental.model.CarRentalContext;
import com.manish.rental.model.Customer;
import com.manish.rental.model.PaymentDetails;
import com.manish.rental.model.RentalDates;
import com.manish.rental.services.UserInputHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleUserInputHelper implements UserInputHelper {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Scanner scanner = new Scanner(System.in);

    @Override
    public RentalDates getRentalDatesFromUser() {
        RentalDates rentalDates = new RentalDates();
        boolean correct_value = false;
        do {
            System.out.println("Enter Start Date for your trip (yyyy-MM-dd): ");
            String startDateString = scanner.nextLine();
            try {
                LocalDate startDate = LocalDate.parse(startDateString, formatter);
                rentalDates.setStartDate(startDate);
                correct_value = true;
            } catch (DateTimeParseException e) {
                System.out.println("Date format is incorrect. Please reenter.");
            }
        }while(!correct_value);

        do {
            System.out.println("Enter end date for your trip (yyyy-MM-dd): ");
            correct_value = false;
            String endDateString = scanner.nextLine();
            try {
                LocalDate endDate = LocalDate.parse(endDateString, formatter);
                rentalDates.setEndDate(endDate);
                if (endDate.isBefore(rentalDates.getStartDate())){
                    System.out.printf("End date: %s is before start date: %s ", endDate.format(formatter),
                            rentalDates.getStartDate().format(formatter));
                    System.out.println();
                }else{
                    correct_value = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Date format is incorrect. Please reenter.");
            }
        }while(!correct_value);

        return rentalDates;
    }

    @Override
    public String getCarChoiceFromUser() {

        String carID = scanner.nextLine();
        return carID;
    }

    @Override
    public boolean wantToBookAnotherTrip() {
        String nextTrip = scanner.nextLine();
        System.out.println();
        return nextTrip.equals("Y");
    }

    @Override
    public Customer getCustomerDetails(CarRentalContext context) {
        if (context.getCustomer() == null) {
            System.out.println("====Customer Information=====");
            System.out.println("Enter First Name: ");
            String firstName = scanner.nextLine();
            System.out.println("Enter Last Name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter Driving Licence Number: ");
            String dlNumber = scanner.nextLine();
            System.out.println("Enter Phone Number: ");
            String phoneNumber = scanner.nextLine();
            System.out.println("Enter Email: ");
            String email = scanner.nextLine();

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setDrivingLicenceNumber(dlNumber);
            customer.setEmailAddress(email);
            customer.setPhoneNumber(phoneNumber);
            return customer;
        }else{
            return  context.getCustomer();
        }
    }

    @Override
    public PaymentDetails getPaymentDetails(CarRentalContext context) {
        if(context.getPaymentDetails() == null) {
            System.out.println();
            System.out.println("==============Payment Information=============");
            System.out.println("Enter Credit card number: ");
            String ccNumber = scanner.nextLine();
            System.out.println("Enter Credit card holder full name: ");
            String userName = scanner.nextLine();
            System.out.println("Enter Credit card expiration date (yyyy-MM-dd) : ");
            boolean correct_value = false;
            LocalDate expirationDate = null;
            do {
                String startDateString = scanner.nextLine();
                try {
                    expirationDate = LocalDate.parse(startDateString, formatter);
                    correct_value = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Date format is incorrect. Please reenter (yyyy-MM-dd) : ");
                }
            } while (!correct_value);
            System.out.println("Enter zip code for credit card : ");
            int zipCode = scanner.nextInt();
            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setCardNumber(ccNumber);
            paymentDetails.setCardUserName(userName);
            paymentDetails.setExpirationDate(expirationDate);
            paymentDetails.setZipCode(zipCode);
            return paymentDetails;
        }else{
            return context.getPaymentDetails();
        }
    }
}
