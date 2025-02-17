package net.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.start.model.Customer;

//@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
