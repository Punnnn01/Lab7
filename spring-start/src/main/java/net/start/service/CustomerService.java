package net.start.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.start.model.Customer;
import net.start.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired 
	CustomerRepository customerRepository;
	
	public List<Customer> findAll(){
		return customerRepository.findAll();
	}

}
