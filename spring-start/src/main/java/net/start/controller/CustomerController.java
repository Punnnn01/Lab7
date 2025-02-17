package net.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import  java.util.List;

import net.start.model.Customer;
import net.start.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	
	//@Autowired CustomerService customerService;
	RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping(path = "/create")
	public String createCustomers(Model model,@ModelAttribute Customer customer) {
		
		return "customers/create" ;
	}
	
	@PostMapping(path = "/save")
	public String saveCustomers(Model model,@ModelAttribute Customer customer) throws JsonProcessingException {
		customer.setUsername("");
		customer.setPwd("");
		
		//customerService.save(customer);
		
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		 HttpEntity<String> request = 
			      new HttpEntity<String>(
			    objectMapper.writeValueAsString(customer), headers);
		
		 String s = 
			      restTemplate.postForObject("http://localhost:8080/api/customers",
			    		  request, String.class);
		
		return "redirect:/customers/index";
	}

	@GetMapping(path = "/index")
	public String viewCustomers(Model model) {
		//customerService.findAll()
		/*ResponseEntity<String> result = restClient.get() 
				  .uri("http://localhost:8080/api/customers") 
				  .retrieve()
				  .toEntity(String.class);*/
		HttpHeaders headers = new HttpHeaders();
		// set headers if required

		HttpEntity<?> entity = new HttpEntity<>(headers);

		String url = "http://localhost:8080/api/customers";

		ParameterizedTypeReference<List<Customer>> responseType = new ParameterizedTypeReference<List<Customer>>() {};
		ResponseEntity<List<Customer>> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

		List<Customer> clientResources = response.getBody();
		//System.out.println(clientResources);
		
		model.addAttribute("customersList", clientResources);
		return "/customers/index";
	}
}
