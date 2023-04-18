package com.nlneto.apitest.entities;

import com.nlneto.apitest.entities.data.EmployeeDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeConverter {

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setNome(employee.getNome());
        dto.setSobrenome(employee.getSobrenome());
        dto.setEmail(employee.getEmail());
        dto.setNis(employee.getNis());
        return dto;
    }

    public Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setNome(dto.getNome());
        employee.setSobrenome(dto.getSobrenome());
        employee.setEmail(dto.getEmail());
        employee.setNis(dto.getNis());
        return employee;
    }

    public Optional<EmployeeDTO> toOptionalDTO(Optional<Employee> optionalEmployee) {
        return optionalEmployee.map(this::toDTO);
    }

    public List<EmployeeDTO> toDTOList(List<Employee> employees) {
        return employees.stream().map(this::toDTO).collect(Collectors.toList());
    }

}