package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;

import java.util.List;
public interface EmprendedorService {
    EmprendedorResponseDto saveEmpre(RegisterEmprendedorRequestDto dto);
    EmprendedorResponseDto deleteEmpre(Long id);
    List<EmprendedorResponseDto> listarEmprePopulares();
    EmprendedorResponseDto obtenerEmpreXId(Long id);
    List<EmprendedorResponseDto>listarAllEmpres();
}
