package com.inProject.in.domain.Profile.controller;

import com.inProject.in.domain.Profile.Dto.request.RequestCertificateDto;
import com.inProject.in.domain.Profile.Dto.response.ResponseCertificateDto;
import com.inProject.in.domain.Profile.service.CertificateServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
@Tag(name = "certificate", description = "자격증 관련 api")
public class CertificateController {

    private final CertificateServiceImpl certificateService;

    @Autowired
    public CertificateController(CertificateServiceImpl certificateService){
        this.certificateService = certificateService;
    }

    @PostMapping()
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseCertificateDto> createCertificate(@RequestBody RequestCertificateDto requestCertificateDto, HttpServletRequest request){

        ResponseCertificateDto responseCertificateDto = certificateService.createCertificate(requestCertificateDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseCertificateDto);
    }

    @PutMapping("/{certificate_id}")
    @Parameter(name = "X-AUTH-TOKEN", description = "토큰을 전송합니다.", in = ParameterIn.HEADER)
    public ResponseEntity<ResponseCertificateDto> updateCertificate(@RequestBody RequestCertificateDto requestCertificateDto,
                                                                    @PathVariable(name = "certificate_id") Long certificate_id,
                                                                    HttpServletRequest request){
        ResponseCertificateDto responseCertificateDto = certificateService.updateCertificate(certificate_id, requestCertificateDto, request);

        return ResponseEntity.status(HttpStatus.OK).body(responseCertificateDto);
    }

    @DeleteMapping("/{certificate_id}")
    public ResponseEntity<String> deleteCertificate(@PathVariable(name = "certificate_id") Long certificate_id, HttpServletRequest request){
        certificateService.deleteCertificate(certificate_id, request);

        return ResponseEntity.status(HttpStatus.OK).body("certificate 삭제 성공");
    }
}
