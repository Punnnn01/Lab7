package net.start.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import net.start.model.Customer;
import net.start.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerApiService {
	
	@Autowired CustomerService customerService ;

	/*@GetMapping("/")
	public String test() {
		return "Hellow" ;
	}*/
	
	@GetMapping(value = "/customers", produces = "application/json")
	public ResponseEntity<List<Customer>> findAll(){
		return new ResponseEntity<List<Customer>>(customerService.findAll(),HttpStatus.OK);
	}
	
	@PostMapping(value = "/customers", consumes =  "application/json" , produces = "application/json")
	public ResponseEntity<String> save(@RequestBody Customer customer){
		
		customerService.save(customer);
		
		return new ResponseEntity<String>("ok",HttpStatus.OK);
	}
}
