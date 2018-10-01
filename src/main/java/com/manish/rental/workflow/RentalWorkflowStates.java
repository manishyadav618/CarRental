package com.manish.rental.workflow;

import com.manish.rental.model.CarRentalContext;
import com.manish.rental.model.Customer;
import com.manish.rental.model.PaymentDetails;
import com.manish.rental.model.RentalDates;
import com.manish.rental.services.PaymentService;

public enum RentalWorkflowStates {
    /*
        Various stages of Car rental booking workflow.
     */
    Initial{
        RentalWorkflowStates doWork(CarRentalContext context){
            context.getWorkflowService().doInitialWork(context);
            return CheckRentalDates;
        }
    },

    CheckRentalDates{
        RentalWorkflowStates doWork(CarRentalContext context){
            RentalDates rentalDates = context.getUserInputHelper().getRentalDatesFromUser();
            System.out.println("Rental Dates choosen: " + rentalDates.toString());
            boolean tripExists = context.getWorkflowService().checkTrips(context, rentalDates);
            if(tripExists){
                return this;
            }else{
                return DisplayAvailableCars;
            }
        }
    },

    DisplayAvailableCars{
        RentalWorkflowStates doWork(CarRentalContext context){
            context.getWorkflowService().displayAllAvailableCars(context);
            return ChooseCar;
        }
    },

    ChooseCar{
        RentalWorkflowStates doWork(CarRentalContext context){
            String carId = context.getUserInputHelper().getCarChoiceFromUser();
            System.out.println("CarId choosen: " + carId);
            boolean carBlocked = context.getWorkflowService().chooseCar(context, carId);
            if (carBlocked) {
                return AdditionRentals;
            }else{
                return this;
            }
        }
    },

    AdditionRentals{
        RentalWorkflowStates doWork(CarRentalContext context){
            System.out.println("Do you want to book another car for the trip. Enter Y or N.");
            boolean bookAnotherRental = context.getUserInputHelper().wantToBookAnotherTrip();
            if(bookAnotherRental){
                return CheckRentalDates;
            }else{
                return BookTrips;
            }
        }
    },

    BookTrips{
        RentalWorkflowStates doWork(CarRentalContext context) {
            Customer customer = context.getUserInputHelper().getCustomerDetails(context);
            PaymentDetails paymentDetails = context.getUserInputHelper().getPaymentDetails(context);
            boolean booked = context.getWorkflowService().bookCars(context, customer, paymentDetails);
            if(booked) {
                return Confirmation;
            }else{
                return this;
            }
        }
    },

    Confirmation{
        RentalWorkflowStates doWork(CarRentalContext context){
            context.getWorkflowService().showConfirmation(context);
            return Done;
        }
    },

    Done{
        RentalWorkflowStates doWork(CarRentalContext context){
            return this;
        }
    };

    abstract RentalWorkflowStates doWork(CarRentalContext context);

}
