package com.nlneto.apitest.services;

import com.nlneto.apitest.model.entities.Employee;
import com.nlneto.apitest.model.entities.EmployeeConverter;
import com.nlneto.apitest.model.dto.EmployeeDTO;
import com.nlneto.apitest.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private EmployeeConverter converter;

    public EmployeeDTO save(String nome, String sobrenome, String email, Long nis) {
        Employee newEmployee = new Employee();
        newEmployee.setNome(nome);
        newEmployee.setSobrenome(sobrenome);
        newEmployee.setEmail(email);
        newEmployee.setNis(nis);
        return converter.toDTO(repository.save(newEmployee));
    }

    public Optional<EmployeeDTO> findById(Long id) {
        Optional<Employee> employeeFound = repository.findById(id);
        return converter.toOptionalDTO(employeeFound);
    }

    public List<EmployeeDTO> getAll() {
        List<Employee> employees = repository.findAll();
        return converter.toDTOList(employees);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EmployeeDTO update(EmployeeDTO employee) {
        Optional<Employee> optionalEmployee = repository.findById(employee.getId());
        if (optionalEmployee.isPresent()) {
            Employee newEmployee = optionalEmployee.get();
            newEmployee.setNome(employee.getNome());
            newEmployee.setSobrenome(employee.getSobrenome());
            newEmployee.setEmail(employee.getEmail());
            newEmployee.setNis(employee.getNis());
            Employee updatedEmployee = repository.save(newEmployee);
            return converter.toDTO(updatedEmployee);
        }
        throw new NoSuchElementException("Funcionário não encontrado no id: " + employee.getId());
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

}
