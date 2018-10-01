package com.manish.rental.workflow;

import com.manish.rental.model.CarRentalContext;
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
import com.manish.rental.services.impl.ConsoleUserInputHelper;
import com.manish.rental.services.impl.DummyPaymentService;
import com.manish.rental.services.impl.WorkflowServiceImpl;

public class CarRentalWorkflowEngine {
    private RentalWorkflowStates state;
    private CarRentalContext carRentalContext;

    public CarRentalWorkflowEngine(CarRentalContext context){
        this.state = RentalWorkflowStates.Initial;
        this.carRentalContext = context;
    }

    public void executeRentalWorkflow(){
        while(this.state != RentalWorkflowStates.Done) {
            this.state = this.state.doWork(this.carRentalContext);
        }
    }

    public RentalWorkflowStates getState() {
        return state;
    }

    public CarRentalContext getCarRentalContext() {
        return carRentalContext;
    }

    public static void main(String[] args) {
        CarRepository carRepository = new DummyCarRepository();
        CarRentalRepository carRentalRepository = new DummyCarRentalRepository(carRepository);
        CustomerRentalRepository customerRentalRepository = new DummyCustomerRentalRepository();

        PaymentService paymentService = new DummyPaymentService();
        CarRentalService carRentalService = new BasicCarRentalService(customerRentalRepository, carRepository,
                carRentalRepository, paymentService);
        UserInputHelper userInputHelper = new ConsoleUserInputHelper();
        WorkflowService workflowService = new WorkflowServiceImpl(carRentalService);

        CarRentalContext context = new CarRentalContext(workflowService, userInputHelper);

        CarRentalWorkflowEngine carRentalWorkflowEngine = new CarRentalWorkflowEngine(context);
        carRentalWorkflowEngine.executeRentalWorkflow();

    }

}
