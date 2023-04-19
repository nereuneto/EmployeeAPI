package com.nlneto.apitest.controller;

import com.nlneto.apitest.model.dto.EmployeeDTO;
import com.nlneto.apitest.model.requests.EmployeeRequest;
import com.nlneto.apitest.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeControllerImpl implements EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeRequest employee) {
        return service.save(employee.getNome(), employee.getSobrenome(), employee.getEmail(), employee.getNis());
    }

    @GetMapping("/{id}")
    public EmployeeDTO findEmployee(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employeeUpdated) {
        EmployeeDTO employee = service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        employee.setNome(employeeUpdated.getNome());
        employee.setSobrenome(employeeUpdated.getSobrenome());
        employee.setEmail(employeeUpdated.getEmail());
        employee.setNis(employeeUpdated.getNis());

        return service.update(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (!service.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
