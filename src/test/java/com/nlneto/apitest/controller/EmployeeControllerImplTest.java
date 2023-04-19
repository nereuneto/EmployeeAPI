package com.nlneto.apitest.controller;

import com.nlneto.apitest.model.dto.EmployeeDTO;
import com.nlneto.apitest.model.requests.EmployeeRequest;
import com.nlneto.apitest.services.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerImplTest {

    @Mock
    private EmployeeService service;

    @InjectMocks
    private EmployeeControllerImpl controller;

    private EmployeeRequest employeeRequest;
    private EmployeeDTO employeeDTO;

    @Before
    public void setUp() {
        employeeRequest = EmployeeRequest.builder()
                .nome("João")
                .sobrenome("Silva")
                .email("joao.silva.com")
                .nis(12345678901L)
                .build();

        employeeDTO = EmployeeDTO.builder()
                .id(1L)
                .nome("João")
                .sobrenome("Silva")
                .email("joao.silva.com")
                .nis(12345678901L)
                .build();
    }

    @Test
    public void deveCriarUmFuncionario() {
        when(service.save(anyString(), anyString(), anyString(), anyLong())).thenReturn(employeeDTO);

        EmployeeDTO result = controller.createEmployee(employeeRequest);

        assertEquals(employeeDTO, result);
        verify(service, times(1)).save(anyString(), anyString(), anyString(), anyLong());
    }

    @Test
    public void deveEncontrarUmFuncionario() {
        when(service.findById(anyLong())).thenReturn(Optional.of(employeeDTO));

        EmployeeDTO result = controller.findEmployee(1L);

        assertEquals(employeeDTO, result);
        verify(service, times(1)).findById(1L);
    }

    @Test
    public void deveRetornarErroQuandoNaoEncontrarUmFuncionario() {
        when(service.findById(anyLong())).thenReturn(Optional.empty());

        Throwable throwable = catchThrowable(() -> controller.findEmployee(1L));

        assertTrue(throwable instanceof ResponseStatusException);
        ResponseStatusException exception = (ResponseStatusException) throwable;
        assertEquals("Funcionário não encontrado", exception.getReason());
        verify(service, times(1)).findById(1L);
    }

    @Test
    public void deveRetornarTodosOsFuncionarios() {
        when(service.getAll()).thenReturn(Collections.singletonList(employeeDTO));

        List<EmployeeDTO> result = controller.getAllEmployees();

        assertEquals(Collections.singletonList(employeeDTO), result);
        verify(service, times(1)).getAll();
    }

    @Test
    public void deveAtualizarUmFuncionario() {
        EmployeeRequest employeeUpdated = EmployeeRequest.builder()
                .nome("Pedro")
                .sobrenome("Souza")
                .email("pedro.souza@gmail.com")
                .nis(12345678901L)
                .build();
        EmployeeDTO employeeToUpdate = employeeDTO.toBuilder().build();
        employeeToUpdate.setNome(employeeUpdated.getNome());
        employeeToUpdate.setSobrenome(employeeUpdated.getSobrenome());
        employeeToUpdate.setEmail(employeeUpdated.getEmail());
        employeeToUpdate.setNis(employeeUpdated.getNis());

        when(service.findById(1L)).thenReturn(Optional.of(employeeDTO));
        when(service.update(employeeToUpdate)).thenReturn(employeeToUpdate);

        EmployeeDTO updatedEmployee = controller.updateEmployee(1L, employeeUpdated);

        verify(service).findById(1L);
        verify(service).update(employeeToUpdate);

        assertEquals(employeeUpdated.getNome(), updatedEmployee.getNome());
        assertEquals(employeeUpdated.getSobrenome(), updatedEmployee.getSobrenome());
        assertEquals(employeeUpdated.getEmail(), updatedEmployee.getEmail());
        assertEquals(employeeUpdated.getNis(), updatedEmployee.getNis());
    }

    @Test
    public void deveDeletarUmFuncionario() {
        Long id = 1L;

        doReturn(true).when(service).existsById(id);
        doNothing().when(service).deleteById(id);
        ResponseEntity<Void> response = controller.deleteEmployee(id);

        verify(service, times(1)).deleteById(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
