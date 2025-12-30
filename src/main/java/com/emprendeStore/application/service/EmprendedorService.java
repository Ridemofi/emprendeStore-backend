package com.emprendeStore.application.service;

import com.emprendeStore.web.dto.request.RegisterEmprendedorRequestDto;
import com.emprendeStore.web.dto.request.UpdateEmprendedorRequestDto;
import com.emprendeStore.web.dto.response.EmprendedorResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface EmprendedorService {
    EmprendedorResponseDto saveEmpre(RegisterEmprendedorRequestDto dto);
    EmprendedorResponseDto updateEmpre(UpdateEmprendedorRequestDto dto, Long id);
    EmprendedorResponseDto updateimagenEmpre(Long id, MultipartFile imgempre);
    EmprendedorResponseDto deleteEmpre(Long id);
    List<EmprendedorResponseDto> listarEmprePopulares();
    EmprendedorResponseDto obtenerEmpreXId(Long id);
    List<EmprendedorResponseDto>listarAllEmpres();
}
