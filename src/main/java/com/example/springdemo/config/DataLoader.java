package com.example.springdemo.config;

import com.example.springdemo.model.Customer;
import com.example.springdemo.model.Product;
import com.example.springdemo.repository.CustomerRepository;
import com.example.springdemo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public DataLoader(ProductRepository productRepository, CustomerRepository customerRepository) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                    new Product("Laptop", "Gaming laptop with 16GB RAM", 1200.00, true),
                    new Product("Wireless Mouse", "Ergonomic dual-mode mouse", 35.00, true),
                    new Product("Mechanical Keyboard", "RGB keyboard with hot-swappable keys", 95.00, true)
            ));
        }

        if (customerRepository.count() == 0) {
            customerRepository.saveAll(List.of(
                    new Customer("Priya Sharma", "priya@example.com"),
                    new Customer("Rahul Verma", "rahul@example.com"),
                    new Customer("Aisha Khan", "aisha@example.com")
            ));
        }
    }
}
