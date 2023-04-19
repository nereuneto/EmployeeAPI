package com.nlneto.apitest.model.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class EmployeeRequest {

    private String nome;
    private String sobrenome;
    private String email;
    private Long nis;

}
