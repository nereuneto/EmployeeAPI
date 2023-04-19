package com.nlneto.apitest.services;

import com.nlneto.apitest.model.dto.EmployeeDTO;
import com.nlneto.apitest.model.entities.Employee;
import com.nlneto.apitest.model.entities.EmployeeConverter;
import com.nlneto.apitest.repositories.EmployeeRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @MockBean
    private EmployeeRepository repository;

    @Autowired
    private EmployeeConverter converter;

    @Autowired
    private EmployeeService service;

    @Test
    public void testGetAll() {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "João", "Beto", "João.Beto@test.com", 12345678901L),
                new Employee(2L, "Maria", "Das couves", "Maria.couves@test.com", 23456789012L)
        );
        when(repository.findAll()).thenReturn(employees);
        List<EmployeeDTO> expectedDtoList = employees.stream()
                .map(converter::toDTO)
                .collect(Collectors.toList());
        List<EmployeeDTO> actualDtoList = service.getAll();
        assertEquals(expectedDtoList, actualDtoList);
    }

    @Test
    public void testFindById() {
        Employee employee = new Employee(1L, "João", "Beto", "João.Beto@test.com", 12345678901L);
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        Optional<EmployeeDTO> expectedDto = Optional.of(converter.toDTO(employee));
        Optional<EmployeeDTO> actualDto = service.findById(1L);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    public void testSave() {
        EmployeeDTO dto = new EmployeeDTO(null, "João", "Beto", "João.Beto@test.com", 12345678901L);
        Employee employee = converter.toEntity(dto);
        when(repository.save(employee)).thenReturn(employee);
        EmployeeDTO expectedDto = converter.toDTO(employee);
        EmployeeDTO actualDto = service.save(dto.getNome(), dto.getSobrenome(), dto.getEmail(), dto.getNis());
        assertEquals(expectedDto, actualDto);
    }

    @Test
    public void testDeleteById() {
        doNothing().when(repository).deleteById(1L);
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testExistsById() {
        when(repository.existsById(1L)).thenReturn(true);
        assertTrue(service.existsById(1L));
    }

    @Test
    public void testUpdate() {
        Employee employee = new Employee(1L, "João", "Beto", "João.Beto@test.com", 12345678901L);
        EmployeeDTO dto = new EmployeeDTO(1L, "John", "Wick", "john.wick@test.com", 12345678901L);
        Employee updatedEmployee = new Employee(1L, "John", "Wick", "john.wick@test.com", 12345678901L);
        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(updatedEmployee)).thenReturn(updatedEmployee);
        EmployeeDTO expectedDto = converter.toDTO(updatedEmployee);
        EmployeeDTO actualDto = service.update(dto);
        assertEquals(expectedDto, actualDto);
    }
    @Test
    public void testCreateEmployeeWithInvalidEmail() {
        EmployeeDTO dto = new EmployeeDTO(null, "João", "Beto", "João.Beto", 12345678901L);
        when(repository.save(converter.toEntity(dto))).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> service.save(dto.getNome(), dto.getSobrenome(),
                dto.getEmail(), dto.getNis()), "Deveria lançar exceção quando o e-mail é inválido");
    }

    @Test
    public void testCreateEmployeeWithoutEmail() {
        EmployeeDTO dto = new EmployeeDTO(null, "João", "Beto", "", 12345678901L);
        when(repository.save(converter.toEntity(dto))).thenThrow(ConstraintViolationException.class);
        assertThrows(ConstraintViolationException.class, () -> service.save(dto.getNome(), dto.getSobrenome(),
                dto.getEmail(), dto.getNis()), "Deveria lançar exceção quando o e-mail é inválido");
    }

}
