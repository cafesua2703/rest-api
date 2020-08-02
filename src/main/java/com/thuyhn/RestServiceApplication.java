/**
 * Copyright (c) Elastic Path Software Inc., 2018
 */
package com.thuyhn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.ui.Model;

import com.thuyhn.customer.CustomerRepository;
import com.thuyhn.files.StorageProperties;
import com.thuyhn.files.StorageService;

/**
 * The Class RestServiceApplication.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableMongoRepositories(basePackageClasses=CustomerRepository.class)
@EnableConfigurationProperties(StorageProperties.class)
public class RestServiceApplication //{
implements CommandLineRunner {
    
    /** The repository. */
    @Autowired
    private CustomerRepository repository;
    
//    private SikuliService sikuliService;
    
    /** The repository. */
    @Autowired
    private StorageService storageService;
    
    private Model model;

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }
    
    @Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
    
    @Override
    public void run (String...strings) {
//    	model.addAttribute("user", "sikuli");
//    	sikuliService.test(model);
    	
//        repository.deleteAll();
//
//        // save a couple of customers
//        repository.save(new Customer("Alice", "Smith"));
//        repository.save(new Customer("Bob", "Smith"));
//
//        // fetch all customers
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (Customer customer : repository.findAll()) {
//          System.out.println(customer);
//        }
//        System.out.println();
//
//        // fetch an individual customer
//        System.out.println("Customer found with findByFirstName('Alice'):");
//        System.out.println("--------------------------------");
//        System.out.println(repository.findByFirstName("Alice"));
//
//        System.out.println("Customers found with findByLastName('Smith'):");
//        System.out.println("--------------------------------");
//        for (Customer customer : repository.findByLastName("Smith")) {
//          System.out.println(customer);
//        }
    }

}
