package com.saraya.isarayaregistry.controller;

import com.saraya.isarayaregistry.exception.ResourceNotFoundException;
import com.saraya.isarayaregistry.model.Customer;
import com.saraya.isarayaregistry.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    //get all customers
    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    //create customer
    @PostMapping("/customers")
    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepo.save(customer);
    }

    //get customer by id
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Customer customer = customerRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not exist with id :" + id));
        return ResponseEntity.ok(customer);
    }

    //update customer
    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
                                                   @RequestBody Customer customerDetails){
        Customer customer = customerRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not exist with id :" + id));
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setEmailId(customerDetails.getEmailId());
        customer.setDescription(customerDetails.getDescription());
        customer.setProductsPurchased(customerDetails.getProductsPurchased());
        customer.setPrice(customerDetails.getPrice());

        Customer updateCustomer = customerRepo.save(customer);
        return ResponseEntity.ok(updateCustomer);
    }

    // delete Customer
    @DeleteMapping("customers/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteEmployee(@PathVariable Long id){

        Customer customer = customerRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Customer not exist with id :" + id));
        customerRepo.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
