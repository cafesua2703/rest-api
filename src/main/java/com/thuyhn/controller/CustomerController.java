package com.thuyhn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thuyhn.domain.Customer;

import repository.CustomerRepository;

@Controller
public class CustomerController {
    
    /** The repository. */
    @Autowired
    private CustomerRepository repository;
    
    @GetMapping("/customer")
    public String customer(@RequestParam(name="name", required=false, defaultValue="") String name, Model model) {
        repository.deleteAll();
        repository.save(new Customer(name, name));
        Customer customer = repository.findByFirstName(name);
        model.addAttribute("name", customer.toString());
        return "customer";
    }

}
