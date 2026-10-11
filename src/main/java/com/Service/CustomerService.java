
package com.Service;

import com.Entity.Customer;
import com.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Create Customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get All Customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get Customer By ID
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with ID: " + id
                        ));
    }

    // Update Customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with ID: " + id
                        ));

        customer.setFullName(updatedCustomer.getFullName());
        customer.setDateOfBirth(updatedCustomer.getDateOfBirth());
        customer.setGender(updatedCustomer.getGender());
        customer.setEmail(updatedCustomer.getEmail());
        customer.setContactNo(updatedCustomer.getContactNo());
        customer.setAddress(updatedCustomer.getAddress());
        customer.setCity(updatedCustomer.getCity());

        return customerRepository.save(customer);
    }

    // Delete Customer
    public void deleteCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with ID: " + id
                        ));

        if (customer.getAccount() != null) {
            throw new RuntimeException(
                    "Customer has an account. Delete or unlink the account first."
            );
        }

        customerRepository.delete(customer);
    }
}
