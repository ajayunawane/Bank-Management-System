package com.Controller;

import com.Entity.Customer;
import com.Service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin("*")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    // CREATE CUSTOMER
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }
    // GET ALL CUSTOMERS
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    // GET CUSTOMER BY ID
    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
    // DELETE CUSTOMER
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return "Customer deleted successfully";
    }

    // UPDATE CUSTOMER
    @PutMapping("/{id}")
    public Customer updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customer) {

        return customerService.updateCustomer(id, customer);
    }

}
