package com.nlneto.apitest.services;

import com.nlneto.apitest.entities.data.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO save(String nome, String sobrenome, String email, Long nis);
    Optional<EmployeeDTO> findById(Long id);
    List<EmployeeDTO> getAll();
    EmployeeDTO update(EmployeeDTO employee);
    void deleteById(Long id);
    boolean existsById(Long id);
}
