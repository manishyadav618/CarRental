package com.manish.rental.model;

import com.manish.rental.services.UserInputHelper;
import com.manish.rental.services.WorkflowService;

import java.util.HashSet;
import java.util.Set;

public class CarRentalContext {
    //Data storage fields needed in the rental
    private Customer customer;
    private Set<RentalTrip> trips = new HashSet<>();
    private PaymentDetails paymentDetails;
    private double costOfRental;
    private RentalDates tentativeRentalDates = new RentalDates();

    //Service need in the execution engine
    private WorkflowService workflowService;
    private UserInputHelper userInputHelper;

    public CarRentalContext(){

    }

    public CarRentalContext(WorkflowService workflowService, UserInputHelper userInputHelper){
        this.workflowService = workflowService;
        this.userInputHelper = userInputHelper;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<RentalTrip> getTrips() {
        return trips;
    }

    public void addTrip(RentalTrip trip) {
        this.trips.add(trip);
    }

    public void removeTrip(RentalTrip trip){
        this.trips.remove(trip);
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public double getCostOfRental() {
        calculateCostOfRental();
        return costOfRental;
    }

    public void setCostOfRental(double costOfRental) {
        this.costOfRental = costOfRental;
    }

    public void calculateCostOfRental(){
        double totalCost = 0;
        for(RentalTrip trip: trips){
            totalCost += trip.getTripCost();
        }
        setCostOfRental(totalCost);
    }

    public RentalDates getTentativeRentalDates() {
        return tentativeRentalDates;
    }

    public void setTentativeRentalDates(RentalDates tentativeRentalDates) {
        this.tentativeRentalDates = tentativeRentalDates;
    }

    public WorkflowService getWorkflowService() {
        return workflowService;
    }

    public void setWorkflowService(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    public UserInputHelper getUserInputHelper() {
        return userInputHelper;
    }

    public void setUserInputHelper(UserInputHelper userInputHelper) {
        this.userInputHelper = userInputHelper;
    }
}
