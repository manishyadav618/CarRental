package com.manish.rental.workflow;

import com.manish.rental.model.*;
import com.manish.rental.repository.CarRentalRepository;
import com.manish.rental.repository.CarRepository;
import com.manish.rental.repository.CustomerRentalRepository;
import com.manish.rental.repository.impl.DummyCarRentalRepository;
import com.manish.rental.repository.impl.DummyCarRepository;
import com.manish.rental.repository.impl.DummyCustomerRentalRepository;
import com.manish.rental.services.CarRentalService;
import com.manish.rental.services.PaymentService;
import com.manish.rental.services.UserInputHelper;
import com.manish.rental.services.WorkflowService;
import com.manish.rental.services.impl.BasicCarRentalService;
import com.manish.rental.services.impl.DummyPaymentService;
import com.manish.rental.services.impl.WorkflowServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;



public class CarRentalWorkflowEngineTest {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static Map<String, Object> testContext = new HashMap<>();

    @Before
    public void setUp(){
        CarRepository carRepository = new DummyCarRepository();
        CarRentalRepository carRentalRepository = new DummyCarRentalRepository(carRepository);
        CustomerRentalRepository customerRentalRepository = new DummyCustomerRentalRepository();

        PaymentService paymentService = new DummyPaymentService();
        CarRentalService carRentalService = new BasicCarRentalService(customerRentalRepository, carRepository,
                carRentalRepository, paymentService);
        WorkflowService workflowService = new WorkflowServiceImpl(carRentalService);


        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setDrivingLicenceNumber("MA1234567");
        customer.setPhoneNumber("1001001000");
        customer.setEmailAddress("example@example.com");

        PaymentDetails  paymentDetails = new PaymentDetails();
        paymentDetails.setCardNumber("4400-1234-1234-1234");
        paymentDetails.setCardUserName("John Doe");
        paymentDetails.setZipCode(12345);
        paymentDetails.setExpirationDate(LocalDate.parse("2021-11-30", formatter));

        testContext.put("CustomerRentalRepository", customerRentalRepository);
        testContext.put("CarRentalService", carRentalService);
        testContext.put("WorkflowService", workflowService);
        testContext.put("Customer", customer);
        testContext.put("PaymentDetails", paymentDetails);

    }


    @Test
    public void testCustomerWantsToBookSingleTrip(){
        CarRentalContext carRentalContext = new CarRentalContext();

        UserInputHelper userInputHelper = mock(UserInputHelper.class);

        RentalDates rentalDates = new RentalDates();
        rentalDates.setStartDate(LocalDate.parse("2018-10-03", formatter));
        rentalDates.setEndDate(LocalDate.parse("2018-10-08", formatter));

        when(userInputHelper.getRentalDatesFromUser()).thenReturn(rentalDates);
        when(userInputHelper.getCarChoiceFromUser()).thenReturn("CAR1002");
        when(userInputHelper.wantToBookAnotherTrip()).thenReturn(false);
        when(userInputHelper.getCustomerDetails(carRentalContext)).thenReturn((Customer)testContext.get("Customer"));
        when(userInputHelper.getPaymentDetails(carRentalContext)).thenReturn((PaymentDetails)testContext.get("PaymentDetails"));

        carRentalContext.setWorkflowService((WorkflowService)testContext.get("WorkflowService"));
        carRentalContext.setUserInputHelper(userInputHelper);

        CarRentalWorkflowEngine carRentalWorkflowEngine = new CarRentalWorkflowEngine(carRentalContext);
        carRentalWorkflowEngine.executeRentalWorkflow();

        //Assertions
        assertEquals(carRentalWorkflowEngine.getState(), RentalWorkflowStates.Done);
        Customer customer = (Customer)testContext.get("Customer");
        CustomerRentalRepository customerRentalRepository = (CustomerRentalRepository)testContext.get("CustomerRentalRepository");
        List<RentalTrip> rentals = customerRentalRepository.getAllRentalByCustomer(customer);
        assertEquals(rentals.size(), 1);
        assertEquals(rentals.get(0).getRentalDates(), rentalDates);
        assertEquals(rentals.get(0).getCar().getCarId(), "CAR1002");
        assertTrue(rentals.get(0).getTripCost()==240);

    }

    @Test
    public void testCustomerWantsToBookMultipleNonOverLappingTrips(){
        /*
          Customer wants to book non-overlapping trips [2018-10-03 to 2018-10-08]
          and [2018-10-11 to 2018-10-19]
         */
        CarRentalContext carRentalContext = new CarRentalContext();

        UserInputHelper userInputHelper = mock(UserInputHelper.class);

        RentalDates rentalDates1 = new RentalDates();
        rentalDates1.setStartDate(LocalDate.parse("2018-10-03", formatter));
        rentalDates1.setEndDate(LocalDate.parse("2018-10-08", formatter));

        RentalDates rentalDates2 = new RentalDates();
        rentalDates2.setStartDate(LocalDate.parse("2018-10-11", formatter));
        rentalDates2.setEndDate(LocalDate.parse("2018-10-19", formatter));

        when(userInputHelper.getRentalDatesFromUser()).thenReturn(rentalDates1).thenReturn(rentalDates2);
        when(userInputHelper.getCarChoiceFromUser()).thenReturn("CAR1002").thenReturn("CAR1003");
        when(userInputHelper.wantToBookAnotherTrip()).thenReturn(true).thenReturn(false);
        when(userInputHelper.getCustomerDetails(carRentalContext)).thenReturn((Customer)testContext.get("Customer"));
        when(userInputHelper.getPaymentDetails(carRentalContext)).thenReturn((PaymentDetails)testContext.get("PaymentDetails"));

        carRentalContext.setWorkflowService((WorkflowService)testContext.get("WorkflowService"));
        carRentalContext.setUserInputHelper(userInputHelper);

        CarRentalWorkflowEngine carRentalWorkflowEngine = new CarRentalWorkflowEngine(carRentalContext);
        carRentalWorkflowEngine.executeRentalWorkflow();

        //Assertions
        assertEquals(carRentalWorkflowEngine.getState(), RentalWorkflowStates.Done);
        Customer customer = (Customer)testContext.get("Customer");
        CustomerRentalRepository customerRentalRepository = (CustomerRentalRepository)testContext.get("CustomerRentalRepository");
        List<RentalTrip> rentals = customerRentalRepository.getAllRentalByCustomer(customer);
        assertEquals(2, rentals.size());
        assertEquals(rentalDates1, rentals.get(0).getRentalDates());
        assertEquals("CAR1002", rentals.get(0).getCar().getCarId());
        assertTrue(rentals.get(0).getTripCost()==240);

        assertEquals(rentalDates2, rentals.get(1).getRentalDates());
        assertEquals("CAR1003", rentals.get(1).getCar().getCarId());
        assertTrue(rentals.get(1).getTripCost()==360);
    }


    @Test
    public void testCustomerWantsToBookMultipleNonOverLappingTripsButPutOverLappingDate(){
        /*
          Customer wants to book trips [2018-10-03 to 2018-10-08] and [2018-10-11 to 2018-10-19]
          but he by accident enters trip date [2018-10-06 to 2018-10-16] which should not be accepted
          as valid rental period because he has already chosen [2018-10-03 to 2018-10-08].
         */
        CarRentalContext carRentalContext = new CarRentalContext();

        UserInputHelper userInputHelper = mock(UserInputHelper.class);

        RentalDates rentalDates1 = new RentalDates();
        rentalDates1.setStartDate(LocalDate.parse("2018-10-03", formatter));
        rentalDates1.setEndDate(LocalDate.parse("2018-10-08", formatter));

        RentalDates rentalDates2 = new RentalDates();
        rentalDates2.setStartDate(LocalDate.parse("2018-10-06", formatter));
        rentalDates2.setEndDate(LocalDate.parse("2018-10-16", formatter));

        RentalDates rentalDates3 = new RentalDates();
        rentalDates3.setStartDate(LocalDate.parse("2018-10-11", formatter));
        rentalDates3.setEndDate(LocalDate.parse("2018-10-19", formatter));

        when(userInputHelper.getRentalDatesFromUser()).thenReturn(rentalDates1).thenReturn(rentalDates2).thenReturn(rentalDates3);
        when(userInputHelper.getCarChoiceFromUser()).thenReturn("CAR1002").thenReturn("CAR1003");
        when(userInputHelper.wantToBookAnotherTrip()).thenReturn(true).thenReturn(false);
        when(userInputHelper.getCustomerDetails(carRentalContext)).thenReturn((Customer)testContext.get("Customer"));
        when(userInputHelper.getPaymentDetails(carRentalContext)).thenReturn((PaymentDetails)testContext.get("PaymentDetails"));

        carRentalContext.setWorkflowService((WorkflowService)testContext.get("WorkflowService"));
        carRentalContext.setUserInputHelper(userInputHelper);

        CarRentalWorkflowEngine carRentalWorkflowEngine = new CarRentalWorkflowEngine(carRentalContext);
        carRentalWorkflowEngine.executeRentalWorkflow();

        //Assertions
        assertEquals(carRentalWorkflowEngine.getState(), RentalWorkflowStates.Done);
        Customer customer = (Customer)testContext.get("Customer");
        CustomerRentalRepository customerRentalRepository = (CustomerRentalRepository)testContext.get("CustomerRentalRepository");
        List<RentalTrip> rentals = customerRentalRepository.getAllRentalByCustomer(customer);
        assertEquals(2, rentals.size());
        assertEquals(rentalDates1, rentals.get(0).getRentalDates());
        assertEquals("CAR1002", rentals.get(0).getCar().getCarId());
        assertTrue(rentals.get(0).getTripCost()==240);

        assertEquals(rentalDates3, rentals.get(1).getRentalDates());
        assertEquals("CAR1003", rentals.get(1).getCar().getCarId());
        assertTrue(rentals.get(1).getTripCost()==360);

    }
}