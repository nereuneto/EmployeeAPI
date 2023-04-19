package com.nlneto.apitest.repositories;

import com.nlneto.apitest.model.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> { }