package com.nlneto.apitest.controller;

import com.nlneto.apitest.entities.data.EmployeeDTO;
import com.nlneto.apitest.entities.requests.EmployeeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface EmployeeController {

    @PostMapping
    EmployeeDTO createEmployee(EmployeeRequest employeeRequest);

    @GetMapping("/{id}")
    EmployeeDTO findEmployee(@PathVariable Long id);

    @GetMapping
    List<EmployeeDTO> getAllEmployees();

    @PutMapping("/{id}")
    EmployeeDTO updateEmployee(@PathVariable Long id, EmployeeRequest employeeDTO);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Long id);
}
