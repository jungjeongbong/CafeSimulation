package server.service;

import server.domain.Customer;
import server.repository.CustomerRepository;

public class CustomerService {

    private CustomerRepository repository;

    public CustomerService() {
        repository = new CustomerRepository();
    }

    public Customer login(String phoneNumber) {
        return repository.findCustomerByPhone(phoneNumber);
    }
}