package com.nlneto.apitest.controller;

import com.nlneto.apitest.model.dto.EmployeeDTO;
import com.nlneto.apitest.model.requests.EmployeeRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee", description = "Endpoints para gerenciamento de funcionários")
public interface EmployeeController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Cria um novo funcionário")
    EmployeeDTO createEmployee(EmployeeRequest employeeRequest);

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca um funcionário pelo ID")
    EmployeeDTO findEmployee(@PathVariable Long id);

    @GetMapping
    @ApiOperation(value = "Busca todos os funcionários")
    List<EmployeeDTO> getAllEmployees();

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um funcionário")
    EmployeeDTO updateEmployee(@PathVariable Long id, EmployeeRequest employeeDTO);

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um funcionário pelo ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> deleteEmployee(@PathVariable Long id);
}
